package com.ayurveda.server.services;

import com.ayurveda.server.api.request.AvailableTimeRequest;
import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.api.response.DoctorResponse;
import com.ayurveda.server.domain.*;
import com.ayurveda.server.dto.DoctorSessionInfo;
import com.ayurveda.server.exceptions.NoAvailableAppointmentException;
import com.ayurveda.server.exceptions.ResourceNotExistsException;
import com.ayurveda.server.repository.*;
import com.ayurveda.server.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final DoctorRepository doctorRepository;
    private final UserAuthenticationDataRepository authenticationDataRepository;
    private final AvailableTimeSlotsRepository availableTimeSlotsRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final MessagingService messagingService;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository,
                         UserAuthenticationDataRepository authenticationDataRepository,
                         AvailableTimeSlotsRepository availableTimeSlotsRepository, PatientRepository patientRepository,
                         AppointmentRepository appointmentRepository, TimeSlotRepository timeSlotRepository,
                         MessagingService messagingService) {
        this.doctorRepository = doctorRepository;
        this.authenticationDataRepository = authenticationDataRepository;
        this.availableTimeSlotsRepository = availableTimeSlotsRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.messagingService = messagingService;
    }

    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctorList = Optional.ofNullable(doctorRepository.findAll())
                .orElseThrow(ResourceNotExistsException::getInstance);
        return doctorList.parallelStream()
                .map(Utils::convertDBDoctorToApiResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorByNic(String doctorNic) {
        Doctor doctor = Optional.ofNullable(doctorRepository.findDoctorByDoctorNic(doctorNic))
                .orElseThrow(ResourceNotExistsException::getInstance);
        DoctorResponse response = Utils.convertDBDoctorToApiResponse(doctor);
        response.setAvailableTimeSlotsList(availableTimeSlotsRepository
                .findAvailableTimeSlotsByDoctor_DoctorId(doctor.getDoctorId()).stream()
                .filter(availableTimeSlots -> availableTimeSlots.getDate()
                        .isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(AvailableTimeSlots::getDate))
                .collect(Collectors.toList()));
        return response;

    }

    public AvailableTimeSlots setAvailableTime(String doctorId, AvailableTimeRequest request) {
        Doctor doctor = Optional.ofNullable(doctorRepository.findDoctorByDoctorId(doctorId))
                .orElseThrow(ResourceNotExistsException::getInstance);
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(ResourceNotExistsException::getInstance);
        AvailableTimeSlots availableTimeSlots = new AvailableTimeSlots();
        availableTimeSlots.setDate(request.getDate());
        availableTimeSlots.setCurrentPatientCount(0);
        availableTimeSlots.setMaxPatientCount(request.getMaxPatientCount());
        availableTimeSlots.setRoomNumber(request.getRoomNumber());
        availableTimeSlots.setDoctor(doctor);
        availableTimeSlots.setTimeSlot(timeSlot);
        return availableTimeSlotsRepository.insert(availableTimeSlots);

    }


    public List<DoctorResponse> executeDoctorSearch(String doctorId, String specialization, String channelDate) throws Exception {

        if (doctorId.equalsIgnoreCase("-1")
                && specialization.equalsIgnoreCase("-1") && channelDate.isEmpty()) {
            List<AvailableTimeSlots> slots = availableTimeSlotsRepository
                    .findAvailableTimeSlotsByDateGreaterThanEqual(new Date());
            return slots.stream()
                    .filter(availableTimeSlots -> !availableTimeSlots.isSessionEnded())
                    .map(AvailableTimeSlots::getDoctor)
                    .map(Utils::convertDBDoctorToApiResponse)
                    .collect(Collectors.toList());
        } else {
            // any of constraints are present
            List<Doctor> doctors;
            if (!channelDate.isEmpty()) {
                List<AvailableTimeSlots> slots = availableTimeSlotsRepository
                        .findAvailableTimeSlotsByDateGreaterThanEqual(SIMPLE_DATE_FORMAT.parse(channelDate));
                doctors = slots.stream()
                        .filter(availableTimeSlots -> !availableTimeSlots.isSessionEnded())
                        .map(AvailableTimeSlots::getDoctor).collect(Collectors.toList());
            } else {
                doctors = doctorRepository.findAll();
            }
            return doctors.parallelStream()
                    .filter(doctor -> doctorId.equalsIgnoreCase("-1") || (doctor.getDoctorId().equalsIgnoreCase(doctorId)))
                    .filter(doctor -> specialization.equals("-1") || doctor.getSpecialization().contains(specialization))
                    .peek(doctor -> System.out.println("adding doctor : " + doctor))
                    .map(Utils::convertDBDoctorToApiResponse)
                    .peek(doctorResponse -> doctorResponse.setAvailableTimeSlotsList(availableTimeSlotsRepository
                            .findAvailableTimeSlotsByDoctor_DoctorId(doctorResponse.getId()).stream()
                            .filter(availableTimeSlots -> !availableTimeSlots.isSessionEnded())
                            .filter(availableTimeSlots -> availableTimeSlots.getDate().isAfter(LocalDate.now()))
                            .collect(Collectors.toList())))
                    .filter(doctorResponse -> doctorResponse.getAvailableTimeSlotsList() != null
                            && doctorResponse.getAvailableTimeSlotsList().size() > 0)
                    .collect(Collectors.toList());
        }
    }

    public Set<String> extractSpecializationFromDoctorList(List<DoctorResponse> allDoctors) {
        return allDoctors.parallelStream()
                .filter(doctorResponse -> doctorResponse.getSpecializationList() != null)
                .flatMap(doctorResponse -> doctorResponse.getSpecializationList().stream())
                .collect(Collectors.toSet());
    }


    public AppointmentResponse findSingleAppointment(String appointmentId) {
        return Utils.convertDBAppointmentToResponse(appointmentRepository.findById(appointmentId)
                .orElseThrow(NoAvailableAppointmentException::new));
    }

    public List<DoctorSessionInfo> getFutureSessions(String doctorId) {
        List<AvailableTimeSlots> sessions = availableTimeSlotsRepository.findAvailableTimeSlotsByDoctor_DoctorId(doctorId);
        return sessions.stream().filter(session -> session.isSessionStarted() || !session.getDate().isBefore(LocalDate.now()))
                .filter(session -> !session.isSessionEnded())
                .filter(timeSlot -> {
                            if (timeSlot.getDate().isEqual(LocalDate.now())) {
                                return LocalTime.now().isBefore(LocalTime.parse(Utils.TIME_FORMAT.format(timeSlot.getTimeSlot().getStartTime())))
                                        || !LocalTime.now().isAfter(LocalTime.parse(Utils.TIME_FORMAT.format(timeSlot.getTimeSlot().getStartTime())).plusHours(1));
                            } else {
                                return true;
                            }
                        }
                )
                .map(timeSlot -> {
                    DoctorSessionInfo sessionInfo = new DoctorSessionInfo();
                    sessionInfo.setSessionDate(timeSlot.getDate());
                    sessionInfo.setTimeSlot(timeSlot.getTimeSlot());
                    sessionInfo.setTotalPatients(timeSlot.getCurrentPatientCount());
                    List<Appointment> appointments = appointmentRepository
                            .findAppointmentsByAppointmentDateAndDoctor_DoctorIdAndAppointmentTimeSlot_Id(
                                    timeSlot.getDate(), doctorId, timeSlot.getTimeSlot().getId());
                    long confirmedAppointments = appointments.stream()
                            .filter(appointment -> appointment.getStatus() == AppointmentStatus.CONFIRMED
                                    || appointment.getStatus() == AppointmentStatus.WAITING_CONSULTATION).count();

                    long waitingPatients = appointments.stream()
                            .filter(appointment -> appointment.getStatus() == AppointmentStatus.WAITING_CONSULTATION).count();

                    sessionInfo.setPaidPatients(confirmedAppointments);
                    sessionInfo.setWaitingPatients(waitingPatients);
                    sessionInfo.setSessionStarted(timeSlot.isSessionStarted());

                    sessionInfo.setTimeSlotId(timeSlot.getId());
//                    if (timeSlot.getDate().isEqual(LocalDate.now().plusDays(1))) {// todo : remove the plus day after testing
                    if (timeSlot.getDate().isEqual(LocalDate.now())) {
                        boolean canBeStarted = LocalTime.now().isBefore(LocalTime.parse(Utils.TIME_FORMAT.format(timeSlot.getTimeSlot().getStartTime())).minusHours(1))
                                || LocalTime.now().isAfter(LocalTime.parse(Utils.TIME_FORMAT.format(timeSlot.getTimeSlot().getStartTime())));
                        sessionInfo.setSessionCanBeStarted(canBeStarted);
                    } else {
                        sessionInfo.setSessionCanBeStarted(false);
                    }
                    return sessionInfo;
                })
                .sorted(Comparator.comparing(DoctorSessionInfo::getSessionDate))
                .collect(Collectors.toList());
    }

    public AvailableTimeSlots startSession(String sessionId) {
        AvailableTimeSlots timeSlot = availableTimeSlotsRepository.findAvailableTimeSlotsByIdIs(sessionId);
        if (!timeSlot.isSessionStarted()) {
            timeSlot.setSessionStarted(true);
            timeSlot.setCurrentNumber(0);
            sendDoctorNotification(timeSlot);
            return availableTimeSlotsRepository.save(timeSlot);

        }
        return timeSlot;
    }

    private void sendDoctorNotification(AvailableTimeSlots timeSlot) {
        appointmentRepository.findAppointmentsByAppointmentDateAndAppointmentTimeSlot_Id(timeSlot.getDate(), timeSlot.getTimeSlot().getId())
                .stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.WAITING_CONSULTATION || appointment.getStatus() == AppointmentStatus.CONFIRMED)
                .filter(Appointment::isDoctorNotification)
                .forEach(appointment -> {
                    try {
                        Patient patient = appointment.getPatient();
                        String message = " Dr." + appointment.getDoctor().getLastName() + " has arrived at the hospital and the session will start now"
                                + " Your appointment reference no is [" + appointment.getAppointmentRefNo() + "]";
                        if (patient != null && "sms".equalsIgnoreCase(patient.getNotificationMethod())) {
                            messagingService.sendSms(message, patient.getPrimaryContactNumber());
                        } else if (patient != null && "email".equalsIgnoreCase(patient.getNotificationMethod())) {
                            messagingService.sendEmail(message, patient.getEmail());
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void endSession(String sessionId) {
        AvailableTimeSlots timeSlot = availableTimeSlotsRepository.findAvailableTimeSlotsByIdIs(sessionId);
        if (timeSlot.isSessionStarted()) {
            timeSlot.setSessionEnded(true);
            availableTimeSlotsRepository.save(timeSlot);
        }
    }

    public List<AvailableTimeSlots> getAllAvailableSlots(String doctorId) {
        return availableTimeSlotsRepository.findAvailableTimeSlotsByDoctor_DoctorId(doctorId);
    }

    public AvailableTimeSlots updateTimeSlot(String doctorId, String slotId, AvailableTimeRequest request) {
        Doctor doctor = Optional.ofNullable(doctorRepository.findDoctorByDoctorId(doctorId))
                .orElseThrow(ResourceNotExistsException::getInstance);
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(ResourceNotExistsException::getInstance);
        AvailableTimeSlots availableTimeSlots = availableTimeSlotsRepository.findAvailableTimeSlotsByIdIs(slotId);
        if (availableTimeSlots.isSessionEnded() || availableTimeSlots.getDate().isBefore(LocalDate.now())) {
            return availableTimeSlots;
        }
        availableTimeSlots.setDate(request.getDate());
        availableTimeSlots.setMaxPatientCount(request.getMaxPatientCount());
        availableTimeSlots.setRoomNumber(request.getRoomNumber());
        availableTimeSlots.setDoctor(doctor);
        availableTimeSlots.setTimeSlot(timeSlot);
        return availableTimeSlotsRepository.save(availableTimeSlots);
    }

    public void deleteAvailableTimeSlot(String slotId) {
        availableTimeSlotsRepository.deleteById(slotId);
    }
}
