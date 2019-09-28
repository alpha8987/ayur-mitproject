package com.example.ayur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ayur.adapters.DocAppointmentViesListAdapter;
import com.example.ayur.adapters.DocSearchViewListAdapter;
import com.example.ayur.domain.DocSearchResultElement;
import com.example.ayur.domain.DoctorAndSpecializationData;
import com.example.ayur.domain.DoctorResponse;
import com.example.ayur.util.HttpUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class channelDateFRagment extends AppCompatActivity {
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_date);

        button = findViewById(R.id.hm_signin);
        SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("hh:mm a");

        Bundle extras = getIntent().getExtras().getBundle("doctorData");

        if (extras != null && !extras.isEmpty()){
            DoctorResponse doctorResponse = (DoctorResponse) extras.getSerializable("response");

            List<DocSearchResultElement> collect = doctorResponse.getAvailableTimeSlotsList().stream().map(timeSlots -> {
                DocSearchResultElement item = new DocSearchResultElement();
                item.setDoctorResponse(doctorResponse);
                item.setAppointmentDate(timeSlots.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                item.setAppointmentStartTime(SIMPLE_DATE_FORMAT.format(timeSlots.getTimeSlot().getStartTime()));
                item.setCurrentPatientCount(timeSlots.getCurrentPatientCount());
                item.setTotalPatientCount(timeSlots.getMaxPatientCount());
                item.setAvailableTimeSlot(timeSlots);
                return item;
            }).collect(Collectors.toList());

            ListAdapter listAdapter = new DocAppointmentViesListAdapter(channelDateFRagment.this, collect);
            ListView myListView = findViewById(R.id.doctor_appointment_list);
            myListView.setAdapter(listAdapter);

        }


    }
}


