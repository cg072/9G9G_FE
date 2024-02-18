package com.example.nineg.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.data.db.domain.MissionCard
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
        launcher: ActivityResultLauncher<Intent>,
        missionCard: MissionCard? = null
    ) {
        launcher.launch(

            Intent(
                context,
                PostingFormActivity::class.java
            ).apply {
                if(missionCard != null) {
                    putExtra(PostingFormActivity.EXTRA_MISSION_CARD, missionCard)
                }
            }
        )
    }

    fun startUpdateFormActivity(
        context: Context,
        goody: Goody,
        launcher: ActivityResultLauncher<Intent>
    ) {
        launcher.launch(
            Intent(
                context,
                PostingFormActivity::class.java
            ).apply {
                putExtra(PostingFormActivity.EXTRA_UPDATE_GOODY, goody)
            }
        )
    }

    fun startExternalBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}