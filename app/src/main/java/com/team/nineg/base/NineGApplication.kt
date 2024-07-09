package com.team.nineg.base

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.team.nineg.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NineGApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}