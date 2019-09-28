package com.example.ayur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayur.domain.AppointmentData;
import com.example.ayur.domain.AppointmentResponse;
import com.example.ayur.domain.AvailableTimeSlots;
import com.example.ayur.domain.DoctorResponse;
import com.example.ayur.domain.MobileNotifyPayment;
import com.example.ayur.util.HttpUtils;

import org.springframework.http.ResponseEntity;

import java.util.Objects;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class channelDetailFragment extends AppCompatActivity {

    private final static int PAYHERE_REQUEST = 11010;
    private Button channelBtn;
    private Button button2;
    private DoctorResponse doctorResponse;
    private AvailableTimeSlots availableTimeSlot;
    private AppointmentResponse appointmentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_details_fill);
        channelBtn = findViewById(R.id.channel_details_channel_btn);

        Bundle extras = getIntent().getExtras().getBundle("doctorData");

        if (extras != null && !extras.isEmpty()) {
            doctorResponse = (DoctorResponse) extras.getSerializable("response");
            availableTimeSlot = (AvailableTimeSlots) extras.getSerializable("timeSlot");

        }

        channelBtn.setOnClickListener(v -> {

//                RadioButton isMember = customView.findViewById(R.id.chanel_member);
//                RadioButton notMember = customView.findViewById(R.id.channel_not_member);

            View customView = v.getRootView();
            RadioButton needDocNotification = customView.findViewById(R.id.channel_doctor_notify);
            RadioButton noDocNotification = customView.findViewById(R.id.channel_no_doc_notifications);
            Spinner patientTitle = customView.findViewById(R.id.patient_title);
            TextView name = customView.findViewById(R.id.channel_patient_name);
            TextInputEditText nic = customView.findViewById(R.id.channel_patient_nic);
            TextInputEditText area = customView.findViewById(R.id.channel_patient_area);
            TextInputEditText mobile = customView.findViewById(R.id.channel_patient_mobile);
            TextInputEditText email = customView.findViewById(R.id.channel_patient_email);
            CheckBox accept = customView.findViewById(R.id.channel_accept);

            if (Objects.isNull(name) || Objects.isNull(name.getText())
                    || Objects.isNull(nic) || Objects.isNull(nic.getText())
                    || Objects.isNull(area) || Objects.isNull(area.getText())
                    || Objects.isNull(mobile) || Objects.isNull(mobile.getText())
                    || Objects.isNull(email) || Objects.isNull(email.getText())) {
                Toast.makeText(this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }


            AppointmentData appointmentData = new AppointmentData();
            appointmentData.setNeedDoctorNotification(needDocNotification.isSelected() || !noDocNotification.isSelected());
            appointmentData.setAppointmentDoctorId(availableTimeSlot.getDoctor().getDoctorId());
            appointmentData.setAvailableTimeSlot(availableTimeSlot);
            String title = Objects.nonNull(patientTitle.getSelectedItem()) ? String.valueOf(patientTitle.getSelectedItem()) : null;
            appointmentData.setTitle(title);
            appointmentData.setLastName(name.getText().toString());
            appointmentData.setNic(nic.getText().toString());
            appointmentData.setArea(area.getText().toString());
            appointmentData.setMobile(mobile.getText().toString());
            appointmentData.setEmail(email.getText().toString());
            appointmentData.setAcceptedTerms(accept.isChecked());
            appointmentData.setPreferredNotificationMethod("email");

            appointmentResponse = HttpUtils.putAppointment(appointmentData);
            Toast.makeText(this, "Appointment Placed, Ref : " + appointmentResponse.getAppointmentRefNo(), Toast.LENGTH_LONG).show();

            InitRequest req = new InitRequest();
            req.setMerchantId("1212202"); // Your Merchant ID
            req.setMerchantSecret("testsecret20190708"); // Your Merchant secret
            req.setAmount(appointmentResponse.getTotalPayment()); // Amount which the customer should pay
            req.setCurrency("LKR"); // Currency
            req.setOrderId(appointmentResponse.getAppointmentId()); // Unique ID for your payment transaction
            req.setItemsDescription("Doctor channel fee");  // Item title or Order/Invoice number
            String firstNames = appointmentResponse.getPatient().getFirstNames();
            String lastName = appointmentResponse.getPatient().getLastName();
            req.getCustomer().setFirstName(firstNames != null ? firstNames : lastName);
            req.getCustomer().setLastName(lastName);
            req.getCustomer().setEmail(appointmentResponse.getPatient().getEmail());
            req.getCustomer().setPhone(appointmentResponse.getPatient().getPrimaryContactNumber());
            req.getCustomer().getAddress().setAddress(appointmentResponse.getPatient().getAddress());
            req.getCustomer().getAddress().setCity("Colombo");
            req.getCustomer().getAddress().setCountry("Sri Lanka");


            Intent intent = new Intent(this, PHMainActivity.class);
            intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
            PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
            startActivityForResult(intent, PAYHERE_REQUEST);

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            String msg;
            try {
                MobileNotifyPayment notifyPayment = new MobileNotifyPayment();
                notifyPayment.setMerchantId("1212202");
                notifyPayment.setOrderId(appointmentResponse.getAppointmentId());
                notifyPayment.setPaymentId(String.valueOf(response.getData().getPaymentNo()));
                notifyPayment.setStatusCode(String.valueOf(response.getData().getStatus()));
                ResponseEntity responseEntity = HttpUtils.notifyPayment(notifyPayment);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (response.isSuccess()) {
                msg = "Activity result:" + response.getData().toString();
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            } else {
                msg = "Result:" + response.toString();
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
            String dialogMessage = response.isSuccess()?"Your appointment was confirmed":"There was an error while processing the payment";
            new AlertDialog.Builder(this)
                    .setTitle("Channel Status")
                    .setMessage(dialogMessage)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    })
                    .setIcon(response.isSuccess()?android.R.drawable.ic_dialog_info:android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }

}
