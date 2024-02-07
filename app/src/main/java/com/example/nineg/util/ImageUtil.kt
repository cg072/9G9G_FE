package com.example.nineg.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

object ImageUtil {
    private const val IMAGE_NAME = "image"

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