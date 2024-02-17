package com.example.nineg.util

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtil {
    private const val FULL_DATE_FORMAT = "yyyy년 MM월 dd일 E요일"
    @SuppressLint("SimpleDateFormat")
    fun getTodayWithDay(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일", Locale.KOREAN)
            currentDateTime.format(formatter)
        } else {
            SimpleDateFormat(FULL_DATE_FORMAT).format(Date())
        }
    }

    fun getSimpleToday(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREAN)
            currentDateTime.format(formatter)
        } else {
            SimpleDateFormat("yyyy-MM-dd").format(Date())
        }
    }
}