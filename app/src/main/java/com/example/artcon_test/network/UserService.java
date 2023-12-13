package com.example.artcon_test.network;

import com.example.artcon_test.model.UpdateUserRequest;
import com.example.artcon_test.model.UpdateUserViewModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

//    @PUT("user/{id}")
//    Call<Void> updateUser(@Path("id") String id, @Body UpdateUserRequest data);

    @Multipart
    @PUT("user/{userId}")
    Call<Void> updateUser(
            @Path("userId") int userId,
            @Part MultipartBody.Part picture,
            @Part MultipartBody.Part banner,
            @Part("updateUserRequest") UpdateUserRequest updateUserRequest
    );
}
