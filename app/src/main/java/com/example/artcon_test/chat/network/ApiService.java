package com.example.artcon_test.chat.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=BGLylVPTMdAIRiW0FLQgYgjF_mKDb0FHrryz15s2ANamDQd1lrm-3Lup7-Cp1_xhpyALsGJODALk5AY5ZEHMA4c" // Replace with your server key
    })

    @POST("fcm/send")
    Call<String> sendMessage(
            @HeaderMap HashMap<String, String> headers,
            @Body String messageBody
            );
}
