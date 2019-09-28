package com.ayurveda.server.services;

import com.ayurveda.server.api.request.AppointmentRequest;
import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.domain.*;
import com.ayurveda.server.dto.AppointmentData;
import com.ayurveda.server.exceptions.NoAvailableTimeSlotException;
import com.ayurveda.server.exceptions.ResourceNotExistsException;
import com.ayurveda.server.repository.*;
import com.ayurveda.server.utility.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmm");
    private final DoctorRepository doctorRepository;
    private final AvailableTimeSlotsRepository availableTimeSlotsRepository;
    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private ConsultationRepository consultationRepository;

    @Value("${ayur.payment.gateway_url}")
    private String gatewayUrl;
    @Value("${ayur.payment.merchant_id}")
    private String merchantId;
    @Value("${ayur.payment.return_url}")
    private String returnUrl;
    @Value("${ayur.payment.cancel_url}")
    private String cancelUrl;
    @Value("${ayur.payment.notify_url}")
    private String notifyUrl;


    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
                              DoctorRepository doctorRepository, AvailableTimeSlotsRepository availableTimeSlotsRepository, ConsultationRepository consultationRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.availableTimeSlotsRepository = availableTimeSlotsRepository;
        this.consultationRepository = consultationRepository;
    }

    public AppointmentResponse searchAppointment(String refId, String nic, String patientName) {
        if (refId != null && !refId.isEmpty() && !refId.equalsIgnoreCase("-")) {
            Appointment appointment = appointmentRepository.findAppointmentByAppointmentRefNoIs(refId);
            if (appointment != null) {
                return Utils.convertDBAppointmentToResponse(appointment);
            }
        } else if (nic != null && !nic.isEmpty() && !nic.equalsIgnoreCase("-")) {
            Patient patient = patientRepository.findPatientByPatientNicIs(nic);
            List<Appointment> appointments = appointmentRepository.findAllByPatient_PatientId(patient.getPatientId());
            Optional<Appointment> appointmentOpt = appointments.stream().filter(appointment -> !appointment.getAppointmentDate().isBefore(LocalDate.now())).findFirst();
            if (appointmentOpt.isPresent()) {
                return Utils.convertDBAppointmentToResponse(appointmentOpt.get());
            }

        } else if (patientName != null && !patientName.isEmpty() && !patientName.equalsIgnoreCase("-")) {
            List<Patient> patients = patientRepository.findPatientByFirstNamesIsOrLastNameIs(patientName, patientName);
            if (!patients.isEmpty()) {
                List<Appointment> appointments = appointmentRepository.findAllByPatient_PatientId(patients.get(0).getPatientId());
                Optional<Appointment> appointmentOpt = appointments.stream().filter(appointment -> !appointment.getAppointmentDate().isBefore(LocalDate.now())).findFirst();
                if (appointmentOpt.isPresent()) {
                    return Utils.convertDBAppointmentToResponse(appointmentOpt.get());
                }
            }
        }
        return null;
    }

    public boolean markAsWaitingConsultation(String appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(AppointmentStatus.WAITING_CONSULTATION);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }


    public AppointmentResponse executeChannel(AppointmentData appointmentData) {

        try {
            LocalDate appointmentDate = appointmentData.getAvailableTimeSlot().getDate();
            String patientNic = appointmentData.getNic();
            String patientTitle = appointmentData.getTitle();
            String patientContactNumber = appointmentData.getMobile();
            String appointmentTimeSlot = appointmentData.getAvailableTimeSlot().getTimeSlot().getId();
            String paymentMode = appointmentData.getPaymentMode();
            String doctorId = appointmentData.getAppointmentDoctorId();

            // create temp patient if the patient is not in the db
            Patient patient = patientRepository.findPatientByPatientNicIs(patientNic);
            if (patient == null) {
                patient = Utils.createTemporaryPatient(patientNic, appointmentData.getFirstName(), appointmentData.getLastName(), patientContactNumber);
                patient.setTitle(patientTitle);
                patient.setAddress(appointmentData.getArea());
                patient.setEmail(appointmentData.getEmail());
                patient.setNotificationMethod(appointmentData.getPreferredNotificationMethod());
                patientRepository.insert(patient);
            } else {
                // if the use is in the DB and the notification method is differen from the one selected in the appointment screen, then update patient record
                if (!patient.getNotificationMethod().equalsIgnoreCase(appointmentData.getPreferredNotificationMethod())){
                    patient.setNotificationMethod(appointmentData.getPreferredNotificationMethod());
                    patient = patientRepository.save(patient);
                }
            }

            AppointmentRequest request = new AppointmentRequest();
            request.setAppointmentDate(appointmentDate);
            request.setAppointmentTimeSlotId(appointmentTimeSlot);
            request.setPatientContactNumber(patientContactNumber);
            request.setPatientFirstName(appointmentData.getFirstName());
            request.setPatientLastName(appointmentData.getLastName());
            request.setPatientNIC(patientNic);
            request.setPaymentType(paymentMode);
            request.setEmail(appointmentData.getEmail());
            request.setDoctorNotificationsSubscribed(appointmentData.isNeedDoctorNotification());

            AppointmentResponse response = setAppointment(doctorId, request);
            //set ipg urls
            response.setMerchantId(merchantId);
            response.setGatewayUrl(gatewayUrl);
            response.setNotifyUrl(notifyUrl);
            response.setCancelUrl(cancelUrl);
            response.setReturnUrl(returnUrl);
            return response;

        } catch (Exception e) {
            throw NoAvailableTimeSlotException.getInstance();
        }
    }


    private AppointmentResponse setAppointment(String doctorId, AppointmentRequest request) {
        Doctor doctor = Optional.ofNullable(doctorRepository.findDoctorByDoctorId(doctorId))
                .orElseThrow(ResourceNotExistsException::getInstance);

//        AvailableTimeSlots availableTimeSlot = Optional.ofNullable(availableTimeSlotsRepository
//                .findAvailableTimeSlotsByDateAndDoctor_DoctorIdAndTimeSlot_Id(
//                        request.getAppointmentDate(),
//                        doctor.getDoctorId(),
//                        request.getAppointmentTimeSlotId())).orElseThrow(NoAvailableTimeSlotException::getInstance);

        AvailableTimeSlots availableTimeSlot = availableTimeSlotsRepository.findAll()
                .stream()
                .filter(availableTimeSlots -> availableTimeSlots.getDoctor().getDoctorId().equalsIgnoreCase(doctor.getDoctorId()))
                .filter(availableTimeSlots -> availableTimeSlots.getDate().isEqual(request.getAppointmentDate()))
                .filter(availableTimeSlots -> availableTimeSlots.getTimeSlot().getId().equalsIgnoreCase(request.getAppointmentTimeSlotId()))
                .findFirst().orElseThrow(NoAvailableTimeSlotException::getInstance);

        List<Appointment> existingAppointmentList = appointmentRepository
                .findAppointmentsByAppointmentDateAndDoctor_DoctorIdAndAppointmentTimeSlot_Id(
                        request.getAppointmentDate(), doctorId, availableTimeSlot.getTimeSlot().getId());
        if (existingAppointmentList != null) {
            Optional<Appointment> appointmentOptional = existingAppointmentList.parallelStream()
                    .filter(appointment -> appointment.getPatient().getPatientNic().equalsIgnoreCase(request.getPatientNIC()))
                    .findFirst();
            if (appointmentOptional.isPresent()) {
                return Utils.convertDBAppointmentToResponse(appointmentOptional.get());
            }
        }
        int currentPatientCount = availableTimeSlot.getCurrentPatientCount();
        if (currentPatientCount < availableTimeSlot.getMaxPatientCount()) {
            availableTimeSlot.setCurrentPatientCount(++currentPatientCount);
            AvailableTimeSlots savedSlot = availableTimeSlotsRepository.save(availableTimeSlot);
            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(request.getAppointmentDate());
            appointment.setAppointmentNumber(currentPatientCount);
            appointment.setAppointmentTimeSlot(savedSlot.getTimeSlot());
            appointment.setDoctor(doctor);
            appointment.setRoomNumber(savedSlot.getRoomNumber());
            appointment.setDoctorNotification(request.isDoctorNotificationsSubscribed());
            Patient patient = patientRepository.findPatientByPatientNicIs(request.getPatientNIC());
            appointment.setPatient(patient);
            appointment.setStatus(AppointmentStatus.INITIAL);
            appointment.setRoomCharge(availableTimeSlot.getRoomCharge());
            appointment.setDoctorCharge(doctor.getConsultationCharge());
            String refId = request.getAppointmentDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    + TIME_FORMAT.format(availableTimeSlot.getTimeSlot().getStartTime())
                    + TIME_FORMAT.format(availableTimeSlot.getTimeSlot().getEndTime())
                    + appointment.getRoomNumber() + appointment.getAppointmentNumber();
            appointment.setAppointmentRefNo(refId);
            appointment = appointmentRepository.insert(appointment);
            return Utils.convertDBAppointmentToResponse(appointment);
        } else {
            throw NoAvailableTimeSlotException.getInstance();
        }


    }

    public Consultation getNextAppointmentForSession(String sessionId) {
        AvailableTimeSlots timeSlots = availableTimeSlotsRepository.findAvailableTimeSlotsByIdIs(sessionId);
        Optional<Appointment> appointmentOpt = appointmentRepository.findAppointmentsByAppointmentDateAndAppointmentTimeSlot_Id(timeSlots.getDate(), timeSlots.getTimeSlot().getId())
                .stream()
                .filter(appointment -> appointment.getAppointmentNumber() >= timeSlots.getCurrentNumber() + 1)
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.WAITING_CONSULTATION)
                .sorted(Comparator.comparing(Appointment::getAppointmentNumber))
                .findFirst();
        if (appointmentOpt.isPresent()){
            timeSlots.setCurrentNumber(timeSlots.getCurrentNumber()+1);
            Consultation consultation = new Consultation();
            consultation.setCurrentSession(availableTimeSlotsRepository.save(timeSlots));
            Appointment appointment = appointmentOpt.get();
            consultation.setAppointment(appointment);
            consultation.setAppointmentDate(appointment.getAppointmentDate());
            if (appointment.getPatient() != null) {
                String patientId = appointment.getPatient().getPatientId();
                consultation.setPatientId(patientId);

                List<Consultation> consultationList = consultationRepository.findAllByPatientIdIs(patientId).stream()
                        .sorted(Comparator.comparing(consultation1 -> consultation1.getAppointment().getAppointmentDate()))
                        .collect(Collectors.toList());

                consultation.setPreviousConsultationList(consultationList);
            }
            return consultation;

        } else {
            return null;
        }
    }

    public Consultation moveToPostConsult(Consultation consultation) {
        Appointment appointment = consultation.getAppointment();
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointment.getId());
        if (appointmentOpt.isPresent()){
            appointment = appointmentOpt.get();
            appointment.setStatus(AppointmentStatus.POST_CONSULTATION);
            consultation.setAppointment(appointmentRepository.save(appointment));
            consultation.setAppointmentDate(appointment.getAppointmentDate());
        }
        return consultationRepository.save(consultation);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> executeAppointmentSearch(String doctorName, String DateFrom, String DateTo) {
        return appointmentRepository.findAll().stream()
                .filter(appointment ->  "-1".equalsIgnoreCase(doctorName) || doctorName.equalsIgnoreCase(appointment.getDoctor().getDoctorId()))
                .filter(appointment -> "-1".equalsIgnoreCase(DateFrom) || !LocalDate.parse(DateFrom).isAfter(appointment.getAppointmentDate()))
                .filter(appointment -> "-1".equalsIgnoreCase(DateTo) || !LocalDate.parse(DateTo).isBefore(appointment.getAppointmentDate()))
                .collect(Collectors.toList());

    }
}
