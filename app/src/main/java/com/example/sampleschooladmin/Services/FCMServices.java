package com.example.sampleschooladmin.Services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMServices extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
