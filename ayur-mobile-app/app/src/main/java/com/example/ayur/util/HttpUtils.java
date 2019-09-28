package com.example.ayur.util;

import com.example.ayur.domain.AppointmentData;
import com.example.ayur.domain.AppointmentResponse;
import com.example.ayur.domain.DoctorAndSpecializationData;
import com.example.ayur.domain.DoctorSearchRequest;
import com.example.ayur.domain.MobileNotifyPayment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static DoctorAndSpecializationData getAllDoctorAndSpecializationData() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate.getForObject("http://api.ayur.ngrok.io/doctorAndSpecializationData", DoctorAndSpecializationData.class);
    }

    public static DoctorAndSpecializationData getDoctorAndSpecializationData(String doctorId, String specialization, String date) {
        DoctorSearchRequest searchRequest = new DoctorSearchRequest();
        searchRequest.setDoctorId(doctorId);
        searchRequest.setSpecialization(specialization);
        searchRequest.setChannelDate(date);

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate.postForObject("http://api.ayur.ngrok.io/executeDoctorSearch", searchRequest, DoctorAndSpecializationData.class);
    }


    public static AppointmentResponse putAppointment(AppointmentData appointmentData) {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate.postForObject("http://api.ayur.ngrok.io/executeChannel", appointmentData, AppointmentResponse.class);
    }

    public static ResponseEntity notifyPayment(MobileNotifyPayment notify) {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate.postForObject("http://api.ayur.ngrok.io/payment/notify/mobile", notify, ResponseEntity.class);
    }

    public static void httpGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

}
