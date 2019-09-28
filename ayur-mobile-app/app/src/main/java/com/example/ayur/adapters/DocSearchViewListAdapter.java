package com.example.ayur.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ayur.R;
import com.example.ayur.channelDateFRagment;
import com.example.ayur.domain.DocSearchResultElement;

import java.util.List;


public class DocSearchViewListAdapter extends ArrayAdapter<DocSearchResultElement> {

    private Context context;

    public DocSearchViewListAdapter(@NonNull Context context, List<DocSearchResultElement> itemList) {
        super(context, R.layout.all_items_list_view_custom_row, itemList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.all_items_list_view_custom_row, parent, false);
        final DocSearchResultElement doctor = getItem(position);

        TextView itemNameText = customView.findViewById(R.id.item_name);
        TextView itesDetailsText = customView.findViewById(R.id.item_details);
        TextView appointmentDate = customView.findViewById(R.id.appointment_date);
        TextView appointmentTime = customView.findViewById(R.id.appointment_time);
        TextView patientCount = customView.findViewById(R.id.current_patient_count);


        itemNameText.setText(doctor.getDoctorName());
        itesDetailsText.setText(doctor.getDoctorSpecialization());

        if (doctor.getAppointmentDate() != null && !doctor.getAppointmentDate().isEmpty()) {
            appointmentDate.setText(doctor.getAppointmentDate());
            appointmentDate.setVisibility(View.VISIBLE);
        }
        if (doctor.getAppointmentStartTime() != null && !doctor.getAppointmentStartTime().isEmpty()) {
            appointmentTime.setText(doctor.getAppointmentStartTime());
            appointmentTime.setVisibility(View.VISIBLE);
        }
        if (doctor.getCurrentPatientCount() != 0) {
            patientCount.setText(doctor.getCurrentPatientCount());
            patientCount.setVisibility(View.VISIBLE);
        }

        customView.setOnClickListener(v -> {
            doctor.getDoctorNic(); // the nic of selected doctor
            System.out.println("============> clicked" + doctor);

            Intent intent = new Intent(this.context, channelDateFRagment.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("response", doctor.getDoctorResponse());
            intent.putExtra("doctorData", bundle);
            this.context.startActivity(intent);
        });
        return customView;
    }
}
