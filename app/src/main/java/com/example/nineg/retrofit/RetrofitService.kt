package com.example.nineg.retrofit

import com.example.nineg.data.db.dto.MissionCardDto
import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.data.db.dto.UserDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("/todo")
    suspend fun getGitHubUserData(
    ): Response<MissionCardDto>

    @POST("/users")
    suspend fun createUser(
        @Body params: HashMap<String, String>
    ): Response<UserDto>

    @GET("/users/{deviceId}")
    suspend fun searchUser(
        @Path("deviceId") deviceId: String
    ): Response<UserDto>

    @Multipart
    @POST("/records")
    fun registerGoody(
        @PartMap params: HashMap<String, String>,
        @Part image: MultipartBody.Part
    ): Response<GoodyDto>
}
