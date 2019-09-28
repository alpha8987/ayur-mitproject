package com.ayurveda.server.services;

import com.ayurveda.server.domain.*;
import com.ayurveda.server.dto.Member;
import com.ayurveda.server.repository.DoctorRepository;
import com.ayurveda.server.repository.PatientRepository;
import com.ayurveda.server.repository.SystemUserRepository;
import com.ayurveda.server.repository.UserAuthenticationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class MemberService {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private UserAuthenticationDataRepository authenticationDataRepository;
    private SystemUserRepository systemUserRepository;
    private MessagingService messagingService;


    @Autowired
    public MemberService(PatientRepository patientRepository, DoctorRepository doctorRepository,
                         UserAuthenticationDataRepository authenticationDataRepository,
                         SystemUserRepository systemUserRepository, MessagingService messagingService) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.authenticationDataRepository = authenticationDataRepository;
        this.systemUserRepository = systemUserRepository;
        this.messagingService = messagingService;
    }

    public Member registerMember(Member memberData) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        switch (memberData.getMemberType()) {
            case PATIENT:
                registerPatientData(memberData, encoder);
                break;
            case PHARMACIST:
            case CASHIER:
                registerUser(memberData, encoder);
                break;
            case DOCTOR:
                registerDoctorData(memberData, encoder);
                break;

        }
        if (memberData.getNotificationMethod().equalsIgnoreCase("sms")) {
            messagingService.sendSms("Your account was created successfully", memberData.getMobileNo());
        } else if (memberData.getNotificationMethod().equalsIgnoreCase("email")) {
            messagingService.sendEmail("Your account was created successfully", memberData.getEmail());
        }
        return memberData;
    }

    private void registerPatientData(Member memberData, BCryptPasswordEncoder encoder) {
        UserAuthenticateData userAuthenticateData = UserAuthenticateData.builder()
                .userName(memberData.getNic())
                .accountStatus(AccountStatus.REGISTERED)
                .password(encoder.encode(memberData.getPassword()))
                .userRoleList(Set.of(UserType.PATIENT))
                .userType(memberData.getMemberType())
                .build();
        UserAuthenticateData savedAuthData = authenticationDataRepository.save(userAuthenticateData);
        Patient patient = Patient.builder()
                .patientNic(memberData.getNic())
                .firstNames(memberData.getFirstName())
                .lastName(memberData.getLastName())
                .address(memberData.getAddress())
                .country(memberData.getCountry())
                .title(memberData.getTitle())
                .dob(memberData.getDob())
                .email(memberData.getEmail())
                .primaryContactNumber(memberData.getMobileNo())
                .authenticateData(savedAuthData)
                .notificationMethod(memberData.getNotificationMethod())
                .joinedDate(new Date())
                .build();
        patientRepository.save(patient);
    }

    private void registerUser(Member memberData, BCryptPasswordEncoder encoder) {
        UserAuthenticateData userAuthenticateData = UserAuthenticateData.builder()
                .userName(memberData.getNic())
                .accountStatus(AccountStatus.REGISTERED)
                .password(encoder.encode(memberData.getPassword()))
                .userRoleList(Set.of(memberData.getMemberType()))
                .userType(memberData.getMemberType())
                .build();
        UserAuthenticateData savedAuthData = authenticationDataRepository.save(userAuthenticateData);
        SystemUser user = SystemUser.builder()
                .userNic(memberData.getNic())
                .userType(memberData.getMemberType())
                .firstNames(memberData.getFirstName())
                .lastName(memberData.getLastName())
                .address(memberData.getAddress())
                .mobileNo(memberData.getMobileNo())
                .title(memberData.getTitle())
                .dob(memberData.getDob())
                .email(memberData.getEmail())
                .authenticateData(savedAuthData)
                .emergencyContactPersonName(memberData.getEmergencyContactName())
                .emergencyContactPersonNumber(memberData.getEmergencyContactNumber())
                .build();
        systemUserRepository.save(user);
    }

    private void registerDoctorData(Member memberData, BCryptPasswordEncoder encoder) {
        UserAuthenticateData userAuthenticateData = UserAuthenticateData.builder()
                .userName(memberData.getNic())
                .accountStatus(AccountStatus.REGISTERED)
                .password(encoder.encode(memberData.getPassword()))
                .userRoleList(Set.of(UserType.DOCTOR))
                .userType(memberData.getMemberType())
                .build();
        UserAuthenticateData savedAuthData = authenticationDataRepository.save(userAuthenticateData);
        Doctor doctor = Doctor.builder()
                .doctorNic(memberData.getNic())
                .firstNames(memberData.getFirstName())
                .lastName(memberData.getLastName())
                .address(memberData.getAddress())
                .dob(memberData.getDob())
                .email(memberData.getEmail())
                .authenticateData(savedAuthData)
                .medicalLicense(memberData.getMedicalLicense())
                .specialization(memberData.getSpecializationList())
                .consultationCharge(memberData.getConsultationCharge())
                .primaryContactNumber(memberData.getMobileNo())
                .emergencyContactPersonName(memberData.getEmergencyContactName())
                .emergencyContactPersonNumber(memberData.getEmergencyContactNumber())
                .build();
        doctorRepository.save(doctor);
    }

    public boolean changePassword(PasswordResetToken passwordResetToken) {
        UserAuthenticateData userData = authenticationDataRepository.findUserAuthenticateDataByUserNameIs(passwordResetToken.getUserName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(passwordResetToken.getOldPassword(), userData.getPassword())) {
            userData.setPassword(encoder.encode(passwordResetToken.getNewPassword()));
            authenticationDataRepository.save(userData);
            return true;
        }
        return false;
    }

    public Member retrieveMemberData(UserAuthenticateData authenticateData) {
        switch (authenticateData.getUserType()) {
            case PATIENT:
                Patient patient = patientRepository.findPatientByAuthenticateData_Id(authenticateData.getId());
                return getMemberFromPatient(patient);
            case DOCTOR:
                Doctor doctor = doctorRepository.findDoctorByAuthenticateData_Id(authenticateData.getId());
                return getMemberFromDoctor(doctor);
            case CASHIER:
            case PHARMACIST:
                SystemUser systemUser = systemUserRepository.findSystemUserByAuthenticateData_Id(authenticateData.getId());
                return getMemberFromSystemUser(systemUser);
        }
        return null;
    }

    private Member getMemberFromSystemUser(SystemUser systemUser) {
        Member member = new Member();
        member.setUserId(systemUser.getId());
        member.setMemberType(systemUser.getUserType());
        member.setNic(systemUser.getUserNic());
        member.setFirstName(systemUser.getFirstNames());
        member.setLastName(systemUser.getLastName());
        member.setTitle(systemUser.getTitle());
        member.setEmail(systemUser.getEmail());
        member.setDob(systemUser.getDob());
        member.setAddress(systemUser.getAddress());
        member.setMobileNo(systemUser.getMobileNo());
        member.setEmergencyContactName(systemUser.getEmergencyContactPersonName());
        member.setEmergencyContactNumber(systemUser.getEmergencyContactPersonNumber());
        member.setNotificationMethod("email");
        return member;
    }

    private Member getMemberFromDoctor(Doctor doctor) {
        Member member = new Member();
        member.setUserId(doctor.getDoctorId());
        member.setMemberType(UserType.DOCTOR);
        member.setNic(doctor.getDoctorNic());
        member.setFirstName(doctor.getFirstNames());
        member.setLastName(doctor.getLastName());
        member.setTitle("Dr.");
        member.setEmail(doctor.getEmail());
        member.setDob(doctor.getDob());
        member.setAddress(doctor.getAddress());
        member.setMobileNo(doctor.getPrimaryContactNumber());
        member.setEmergencyContactName(doctor.getEmergencyContactPersonName());
        member.setEmergencyContactNumber(doctor.getEmergencyContactPersonNumber());
        member.setNotificationMethod("email");
        return member;
    }

    private Member getMemberFromPatient(Patient patient) {
        Member member = new Member();
        member.setUserId(patient.getPatientId());
        member.setMemberType(UserType.PATIENT);
        member.setNic(patient.getPatientNic());
        member.setFirstName(patient.getFirstNames());
        member.setLastName(patient.getLastName());
        member.setTitle(patient.getTitle());
        member.setEmail(patient.getEmail());
        member.setDob(patient.getDob());
        member.setAddress(patient.getAddress());
        member.setCountry(patient.getCountry());
        member.setMobileNo(patient.getPrimaryContactNumber());
        member.setEmergencyContactName(patient.getEmergencyContactPersonName());
        member.setEmergencyContactNumber(patient.getEmergencyContactPersonNumber());
        member.setNotificationMethod(patient.getNotificationMethod());
        return member;
    }
}
