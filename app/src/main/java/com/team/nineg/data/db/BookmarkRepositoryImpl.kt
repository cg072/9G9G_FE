package com.team.nineg.data.db

import com.team.nineg.data.db.dto.BookmarkDto
import retrofit2.Response

class BookmarkRepositoryImpl : BookmarkRepository {
    override suspend fun createBookmark(
        deviceId: String,
        missionTitle: String
    ): Response<BookmarkDto> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBookmark(deviceId: String): Response<BookmarkDto> {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(bookmarkId: String): Response<Unit> {
        TODO("Not yet implemented")
    }
}