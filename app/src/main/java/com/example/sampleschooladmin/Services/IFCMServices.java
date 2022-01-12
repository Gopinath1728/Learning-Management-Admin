package com.example.sampleschooladmin.Services;



import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMServices {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAIAlRSQg:APA91bHfzgAj5re_7WA5g47IUeEbdwWqoWZELmWrwInk7TE-NlYwEC0qB_BDsPB2H1S6jBe1VXzxIVdZ08JG6VCNGIdIIO7BToE38Zt0yQJfvYwFxVhOuX3VW9zqsTK34PqJfkKryZ-0"
    })

    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);
}
