package com.example.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.nineg.data.db.dto.asDomainModel
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.data.db.local.MissionCardLocalDataSource
import com.example.nineg.data.db.remote.MissionCardRemoteDataSource
import com.example.nineg.retrofit.ApiResult
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
        //        TODO("Not yet implemented")
        return localMissionCardImpl.getMissionCardList()
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
//        TODO("Not yet implemented")
        localMissionCardImpl.addMissionCard(missionCardInfo)
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
//        TODO("Not yet implemented")
        localMissionCardImpl.addMissionCardList(missionCardInfoList)
    }

    override suspend fun clearMissionCard() {
        localMissionCardImpl.clearMissionCard()
    }

    override suspend fun getTodayMissionCard(): MissionCardInfoEntity? {
//        TODO("Not yet implemented")
        return null
    }

    override suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity> {
//        TODO("Not yet implemented")
        return listOf()
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override fun getIsFirstLaunch(): Boolean {
        return statusPref.getBoolean(IS_FIRST_LAUNCH, true)
    }

    override fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        statusPref.edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply()
    }

    override suspend fun downloadMissionCardList(): List<MissionCardInfoEntity> {
        val result = remoteMissionCardImpl.downloadMissionCardList()

        return when (result) {
            is ApiResult.Success -> {
                Log.d(TAG, "downloadMissionCardList: ${result.value}")
                result.value.asDomainModel()
            }

            is ApiResult.Error -> {
                Log.e(TAG, "downloadMissionCardList: ${result.exception}")
                emptyList()
            }
        }
    }

    companion object {
        private const val TAG = "MissionCardRepositoryIm"
        private const val IS_FIRST_LAUNCH = "isFirstLaunch"
    }
}