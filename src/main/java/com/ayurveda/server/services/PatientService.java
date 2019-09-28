package com.ayurveda.server.services;

import com.ayurveda.server.api.response.PatientResponse;
import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.Patient;
import com.ayurveda.server.exceptions.ResourceNotExistsException;
import com.ayurveda.server.repository.AppointmentRepository;
import com.ayurveda.server.repository.PatientRepository;
import com.ayurveda.server.repository.UserAuthenticationDataRepository;
import com.ayurveda.server.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserAuthenticationDataRepository authenticationDataRepository;
    private final AppointmentRepository appointmentRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository, UserAuthenticationDataRepository authenticationDataRepository,
                          AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.authenticationDataRepository = authenticationDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<PatientResponse> getAllPatients() {
        List<Patient> patientList = Optional.ofNullable(patientRepository.findAll())
                .orElseThrow(ResourceNotExistsException::new);
        return patientList.parallelStream()
                .map(Utils::convertDBPatientToApiResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse getPatientByNic(String patientNic) {
        Patient patient = Optional.ofNullable(patientRepository.findPatientByPatientNicIs(patientNic))
                .orElseThrow(ResourceNotExistsException::new);
        return Utils.convertDBPatientToApiResponse(patient);

    }

    public List<Appointment> getPatientAppointmentsByNic(String patientNic) {
        Patient patient = Optional.ofNullable(patientRepository.findPatientByPatientNicIs(patientNic))
                .orElseThrow(ResourceNotExistsException::new);
        List<Appointment> appointments = appointmentRepository.findAllByPatient_PatientId(patient.getPatientId());
        return appointments;

    }

    public PatientResponse deletePatientByNic(String nic) {
        Patient patient = Optional.ofNullable(patientRepository.findPatientByPatientNicIs(nic))
                .orElseThrow(ResourceNotExistsException::new);
        patientRepository.deletePatientByPatientNicIs(nic);
        authenticationDataRepository.deleteById(patient.getAuthenticateData().getId());
        return Utils.convertDBPatientToApiResponse(patient);

    }

//    /**
//     * Add the user given in request to the system. First try to create the user authentication data.
//     * If its success then add the user to the database and return the same data with user id.
//     * If there was any error while creating the user account return error.
//     *
//     * @param request the patient to be added to the system
//     * @return the patient details added with user id
//     */
//    public PatientResponse addNewPatient(PatientRequest request) {
//        Patient patient = Utils.convertRequestPatientToDB(request);
//        UserAuthenticateData authenticateData = new UserAuthenticateData();
//        authenticateData.setUserName(patient.getPatientNic());
//        authenticateData.setPassword(request.getPassword());
//        authenticateData.setAccountStatus(AccountStatus.REGISTERED);
//        authenticateData.setUserType(UserType.PATIENT);
//
//        UserAuthenticateData insertedAuthData = authenticationDataRepository.insert(authenticateData);
//        patient.setAuthenticateData(insertedAuthData);
//        return Utils.convertDBPatientToApiResponse(patientRepository.insert(patient));
//
//    }
//
//    public PatientResponse updatePatient(PatientRequest request) {
//        Patient old = Optional.ofNullable(patientRepository
//                .findById(request.getId()))
//                .orElseThrow(ResourceNotExistsException::new)
//                .get();
//
//        Patient patient = Utils.convertRequestPatientToDB(request);
//        UserAuthenticateData authenticateData = Optional.ofNullable(authenticationDataRepository
//                .findById(old.getAuthenticateData().getId()))
//                .orElseThrow(ResourceNotExistsException::new)
//                .get();
//        authenticateData.setUserName(request.getNic());
//        authenticateData.setPassword(request.getPassword());
//        authenticateData.setAccountStatus(request.getAccountStatus());
//        authenticateData.setUserType(request.getUserType());
//
//        UserAuthenticateData insertedAuthData = authenticationDataRepository.save(authenticateData);
//        patient.setAuthenticateData(insertedAuthData);
//        return Utils.convertDBPatientToApiResponse(patientRepository.save(patient));
//
//    }
}
