package com.team.nineg.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.ui.creation.PostingFormActivity
import com.team.nineg.ui.detail.RecordDetailActivity
import com.team.nineg.ui.main.MainActivity

object ActivityUtil {

    fun startMainActivity(context: Context) {
        context.startActivity(
            Intent(
                context,
                MainActivity::class.java
            )
        )
    }

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

    fun startPostingFormActivityFromMissionCard(
        context: Context,
        missionCard: MissionCard,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        launcher.launch(
            Intent(
                context,
                PostingFormActivity::class.java
            ).apply {
                putExtra(PostingFormActivity.EXTRA_MISSION_CARD, missionCard)
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