package com.example.github_data.retrofit

import com.example.nineg.data.db.remote.RemoteMissionCard
import retrofit2.Response
import retrofit2.http.GET

interface ServerApi {
    @GET("/todo")
    suspend fun getGitHubUserData(
    ): Response<RemoteMissionCard>
}
