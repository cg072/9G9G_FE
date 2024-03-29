package com.team.nineg.retrofit

import com.team.nineg.data.db.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @Headers("Content-Type: application/json")
    @GET("/oauth/kakao")
    suspend fun login(
        @Header("Authorization") accessToken: String
    ): Response<UserDto>

    @DELETE("/users/{id}")
    suspend fun revoke(@Path("id") deviceId: String): Response<RevokeDto>

    @Multipart
    @POST("/records")
    suspend fun registerGoody(
        @Part image: MultipartBody.Part,
        @Part("recordDto") body: RequestBody
    ): Response<GoodyDto>

    @GET("/missions")
    suspend fun getMissionCardList(
    ): Response<MissionCardDto>

    @DELETE("/records/{id}")
    suspend fun removeGoody(
        @Path("id") goodyId: String
    ): Response<Unit>

    @Multipart
    @PUT("/records/{id}")
    suspend fun updateGoody(
        @Path("id") goodyId: String,
        @Part image: MultipartBody.Part?,
        @Part("recordUpdateDto") body: RequestBody
    ): Response<GoodyDto>

    @GET("/records/user/{deviceId}")
    suspend fun fetchGoodyList(
        @Path("deviceId") deviceId: String
    ): Response<List<GoodyDto>>

    @POST("/bookmarks")
    suspend fun createBookmark(
        @Body params: HashMap<String, String>
    ): Response<BookmarkDto>

    @GET("/bookmarks/user/{deviceId}")
    suspend fun searchBookmark(
        @Path("deviceId") deviceId: String
    ): Response<BookmarkDto>

    @DELETE("/bookmarks/{id}")
    suspend fun removeBookmark(
        @Path("id") bookmarkId: String
    ): Response<Unit>
}
