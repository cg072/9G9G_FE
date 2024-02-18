package com.example.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.dto.asEntityModel
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.data.db.local.MissionCardLocalDataSource
import com.example.nineg.data.db.remote.GoodyRemoteDataSource
import com.example.nineg.data.db.remote.MissionCardRemoteDataSource
import com.example.nineg.retrofit.ApiResult
import com.example.nineg.util.DateUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MissionCardRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localMissionCardImpl: MissionCardLocalDataSource,
    private val remoteMissionCardImpl: MissionCardRemoteDataSource,
    private val goodyRemoteDataSource: GoodyRemoteDataSource
) : MissionCardRepository {

    private val statusPref: SharedPreferences =
        context.getSharedPreferences("STATUS_PREFS", Context.MODE_PRIVATE)

    override suspend fun getMissionCardList(): List<MissionCardInfoEntity> {
        return localMissionCardImpl.getMissionCardList()
    }

    override suspend fun getMissionCardPack(): List<MissionCardInfoEntity> {
        return localMissionCardImpl.getMissionCardPack()
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        localMissionCardImpl.addMissionCard(missionCardInfo)
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        localMissionCardImpl.addMissionCardList(missionCardInfoList)
    }

    override suspend fun clearMissionCard() {
        localMissionCardImpl.clearMissionCard()
    }

    override suspend fun getTodayMissionCard(userId: String): MissionCard? {
        return try {
            val response = goodyRemoteDataSource.getGoodyList(userId)

            if (response.isSuccessful && response.body() != null) {
                response.body()?.find { it.dueDate == DateUtil.getSimpleToday() }?.let { goodyDto ->
                    MissionCard(
                        id = goodyDto.id.toInt(),
                        index = 0,
                        image = goodyDto.photoUrl,
                        level = 0,
                        title = goodyDto.title,
                        guide = null,
                        content = goodyDto.content,
                    )
                }
            } else {
                null
            }
        } catch (throwable: Throwable) {
            return null
        }
    }

    override suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity> {
        return localMissionCardImpl.getBookmarkedMissionCardList()
    }

    override suspend fun bookmarkMissionCard(id: Int) {
        localMissionCardImpl.bookmarkMissionCard(id)
    }

    override suspend fun unBookmarkMissionCard(id: Int) {
        localMissionCardImpl.unBookmarkMissionCard(id)
    }

    override fun getIsFirstLaunch(): Boolean {
        return statusPref.getBoolean(IS_FIRST_LAUNCH, true)
    }

    override fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        statusPref.edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply()
    }

    override fun getUserId(): String {
        return statusPref.getString(USER_ID, "").toString()
    }

    override fun setUserId(userId: String) {
        statusPref.edit().putString(USER_ID, userId).apply()
    }

    override suspend fun downloadMissionCardList() {
        val result = remoteMissionCardImpl.downloadMissionCardList()

        when (result) {
            is ApiResult.Success -> {
                addMissionCardList(result.value.asEntityModel())
            }

            is ApiResult.Error -> {
                Log.e(TAG, "downloadMissionCardList: ${result.exception?.message}")
            }
        }
    }


    companion object {
        private const val TAG = "MissionCardRepositoryIm"
        private const val IS_FIRST_LAUNCH = "isFirstLaunch"
        private const val USER_ID = "userId"
    }
}