package com.example.ayur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.example.ayur.domain.DoctorAndSpecializationData;
import com.example.ayur.domain.DoctorResponse;
import com.example.ayur.util.DoctorResponseAdapter;
import com.example.ayur.util.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {
    private DoctorResponse selectedDoctor;
    private String selectedDate = "";
    private Activity currentActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DoctorAndSpecializationData doctorAndSpecializationData = HttpUtils.getAllDoctorAndSpecializationData();


        final Spinner docNames = view.findViewById(R.id.hm_docname_spinner);

        DoctorResponseAdapter adapter = new DoctorResponseAdapter(this.getActivity(), android.R.layout.simple_spinner_item, doctorAndSpecializationData.getAllDoctors());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        docNames.setAdapter(adapter);

        docNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctor = (DoctorResponse) parent.getSelectedItem();
                System.out.println("selected : " + selectedDoctor.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDoctor = null;
            }


        });

        final Spinner specialization = view.findViewById(R.id.hm_spe_spinner);
        List<String> collect2 = new ArrayList<>();
        collect2.add("None");
        collect2.addAll(doctorAndSpecializationData.getSpecializationSet());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, collect2);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        specialization.setAdapter(adapter2);

        Button searchButton = view.findViewById(R.id.hm_searchbtn);
        searchButton.setOnClickListener(this::executeSearch);

        final CalendarView calendarView = view.findViewById(R.id.hm_calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.format("%d-%02d-%d", year, month + 1, dayOfMonth);
            }
        });


    }

    public void executeSearch(View view) {
        String doctorId = "-1";
        String specialization = "-1";
        if (selectedDoctor != null) {
            doctorId = selectedDoctor.getId();
        }
        final Spinner specSpinner = view.findViewById(R.id.hm_spe_spinner);
        if (specSpinner != null && specSpinner.getSelectedItem() != null && "None".equalsIgnoreCase(String.valueOf(specSpinner.getSelectedItem()))) {
            specialization = String.valueOf(specSpinner.getSelectedItem());
        }

        Intent intent = new Intent(view.getContext(), DoctorChannelFragment.class);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("specialization", specialization);
        intent.putExtra("selectedDate", selectedDate);
        getActivity().startActivity(intent);

    }


}
