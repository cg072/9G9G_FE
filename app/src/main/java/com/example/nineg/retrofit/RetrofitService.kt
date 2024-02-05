package com.example.nineg.retrofit

import com.example.nineg.data.db.dto.UserDto
import com.example.nineg.data.db.remote.RemoteMissionCard
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("/todo")
    suspend fun getGitHubUserData(
    ): Response<RemoteMissionCard>

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
