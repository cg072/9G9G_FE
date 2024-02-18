package com.example.nineg.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source
import java.io.File

object ImageUtil {
    private const val IMAGE_NAME = "image"

    fun getMultipartBody(bitmap: Bitmap): MultipartBody.Part {
        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? = "image/jpeg".toMediaTypeOrNull()

            override fun writeTo(sink: BufferedSink) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, sink.outputStream())
            }
        }

        val fileName = "$IMAGE_NAME.jpeg"

        return MultipartBody.Part.createFormData(IMAGE_NAME, fileName, requestBody)
    }

    fun getMultipartBody(
        contentResolver: ContentResolver,
        imageUri: Uri
    ): MultipartBody.Part? {
        return contentResolver.query(imageUri, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName =
                    it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(imageUri)?.toMediaTypeOrNull()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        contentResolver.openInputStream(imageUri)?.let { inputStream ->
                            sink.writeAll(inputStream.source())
                            inputStream.close()
                        }
                    }
                }
                it.close()

                MultipartBody.Part.createFormData(IMAGE_NAME, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }
}