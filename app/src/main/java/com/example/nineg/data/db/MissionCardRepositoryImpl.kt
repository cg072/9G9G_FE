package com.example.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.data.db.dto.asEntityModel
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.data.db.local.MissionCardLocalDataSource
import com.example.nineg.data.db.remote.MissionCardRemoteDataSource
import com.example.nineg.retrofit.ApiResult
import com.example.nineg.util.DateUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MissionCardRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localMissionCardImpl: MissionCardLocalDataSource,
    private val remoteMissionCardImpl: MissionCardRemoteDataSource
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

    override suspend fun getTodayMissionCard(userId: String): GoodyDto? {
        val result = remoteMissionCardImpl.getTodayMissionCard(userId)

        when (result) {
            is ApiResult.Success -> {
                if(result.value.filter { it.dueDate == DateUtil.getSimpleToday() }.isEmpty()) {
                    return null
                }
                return result.value.filter { it.dueDate == DateUtil.getSimpleToday() }[0]
            }

            is ApiResult.Error -> {
                return null
            }
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

    override fun getUserId(): String? {
        return statusPref.getString(USER_ID, "")
    }

    override fun setUserId(userId: String) {
        statusPref.edit().putString(USER_ID, userId).apply()
    }

    override suspend fun downloadMissionCardList() {
        val result = remoteMissionCardImpl.downloadMissionCardList()

        when (result) {
            is ApiResult.Success -> {
                Log.d(TAG, "downloadMissionCardList: ${result.value}")
                addMissionCardList(result.value.asEntityModel())
            }

            is ApiResult.Error -> {
                Log.e(TAG, "downloadMissionCardList: ${result.exception}")
            }
        }
    }


    companion object {
        private const val TAG = "MissionCardRepositoryIm"
        private const val IS_FIRST_LAUNCH = "isFirstLaunch"
        private const val USER_ID = "userId"
    }
}