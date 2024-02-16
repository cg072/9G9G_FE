package com.example.nineg.util

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.ui.creation.PostingFormActivity
import com.example.nineg.ui.detail.RecordDetailActivity

object ActivityUtil {

    fun startRecordDetailActivity(
        context: Context,
        goody: Goody,
        launcher: ActivityResultLauncher<Intent>
    ) {
        launcher.launch(
            Intent(
                context,
                RecordDetailActivity::class.java
            ).apply {
                putExtra(RecordDetailActivity.EXTRA_GOODY, goody)
            }
        )
    }

    fun startPostingFormActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>
    ) {
        launcher.launch(
            Intent(
                context,
                PostingFormActivity::class.java
            )
        )
    }
}