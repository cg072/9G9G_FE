package com.example.nineg.retrofit

import com.example.nineg.data.db.dto.UserDto
import com.example.nineg.data.db.dto.MissionCardDto
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("/todo")
    suspend fun getGitHubUserData(
    ): Response<MissionCardDto>

//    @Headers("Content-Type: application/json")
    @POST("/users")
    suspend fun createUser(
        @Body params: HashMap<String, String>
    ): Response<UserDto>

    @GET("/users/{deviceId}")
    suspend fun searchUser(
        @Path("deviceId") deviceId: String
    ): Response<UserDto>
}
