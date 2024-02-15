package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.BookmarkDto
import retrofit2.Response

interface BookmarkRemoteDataSource {
    suspend fun createBookmark(deviceId: String, missionTitle: String): Response<BookmarkDto>
    suspend fun searchBookmark(deviceId: String): Response<BookmarkDto>
    suspend fun removeBookmark(bookmarkId: String): Response<Unit>
}