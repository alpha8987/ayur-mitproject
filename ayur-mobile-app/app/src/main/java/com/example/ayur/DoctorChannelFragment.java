package com.example.ayur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ayur.adapters.DocSearchViewListAdapter;
import com.example.ayur.domain.DocSearchResultElement;
import com.example.ayur.domain.DoctorAndSpecializationData;
import com.example.ayur.util.HttpUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorChannelFragment extends AppCompatActivity {
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_channel);

        button = findViewById(R.id.hm_signin);

        button2 = findViewById(R.id.hm_searchbtn);

        String doctorId = getIntent().getStringExtra("doctorId");
        String specialization = getIntent().getStringExtra("specialization");
        String selectedDate = getIntent().getStringExtra("selectedDate");

        DoctorAndSpecializationData doctorAndSpecializationData = HttpUtils.getDoctorAndSpecializationData(doctorId, specialization, selectedDate);
        List<DocSearchResultElement> collect = doctorAndSpecializationData.getAllDoctors().stream().map(doctorResponse -> {
            DocSearchResultElement item = new DocSearchResultElement();
            item.setDoctorNic(doctorResponse.getNic());
            item.setDoctorName(doctorResponse.getFirstNames() + " " + doctorResponse.getLastName());
            item.setDoctorSpecialization(String.join(",", doctorResponse.getSpecializationList()));
            item.setDoctorResponse(doctorResponse);
            return item;
        }).collect(Collectors.toList());

        ListAdapter listAdapter = new DocSearchViewListAdapter(DoctorChannelFragment.this, collect);
        ListView myListView = findViewById(R.id.doctorList);
        myListView.setAdapter(listAdapter);

    }
}
