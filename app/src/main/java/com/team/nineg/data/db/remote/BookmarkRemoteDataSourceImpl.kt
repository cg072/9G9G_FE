package com.team.nineg.data.db.remote

import com.team.nineg.data.db.dto.BookmarkDto
import com.team.nineg.retrofit.RetrofitService
import retrofit2.Response
import javax.inject.Inject

class BookmarkRemoteDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    BookmarkRemoteDataSource {
    override suspend fun createBookmark(
        deviceId: String,
        missionTitle: String
    ): Response<BookmarkDto> {
        val param = HashMap<String, String>()
        param["deviceId"] = deviceId
        param["missionTitle"] = missionTitle
        return service.createBookmark(param)
    }

    override suspend fun searchBookmark(deviceId: String): Response<BookmarkDto> {
        return service.searchBookmark(deviceId)
    }

    override suspend fun removeBookmark(bookmarkId: String): Response<Unit> {
        return service.removeBookmark(bookmarkId)
    }
}