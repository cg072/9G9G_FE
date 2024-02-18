package com.example.nineg.ui.detail

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.databinding.ActivityRecordDetailBinding
import com.example.nineg.dialog.RecordDeleteDialog
import com.example.nineg.dialog.RecordOptionDialog
import com.example.nineg.ui.calendar.CalendarFragment
import com.example.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordDetailActivity : BaseActivity<ActivityRecordDetailBinding>() {

    private val viewModel: RecordDetailViewModel by viewModels()
    private var goody: Goody? = null

    private val startPostingFormActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                goody = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent?.getParcelableExtra(CalendarFragment.EXTRA_SAVE_GOODY, Goody::class.java)
                } else {
                    intent?.getParcelableExtra(CalendarFragment.EXTRA_SAVE_GOODY)
                }
                initGoody()
                setResult(RESULT_OK)
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.activity_record_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goody = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_GOODY, Goody::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_GOODY)
        }

        initGoody()
        initListener()
        initObserve()
    }

    private fun initGoody() {
        binding.fragmentRecordDetailImageCard.post {
            binding.fragmentRecordDetailImageCard.load(goody?.photoUrl) {
                transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
            }
        }

        binding.fragmentRecordDetailImageCardText.text = goody?.title
        binding.fragmentRecordDetailContentTitle.text = goody?.title
        binding.fragmentRecordDetailContentText.text = goody?.content

        val level = goody?.level ?: 0
        if (level >= 1) binding.fragmentRecordDetailLevelOne.visibility = View.VISIBLE
        if (level >= 2) binding.fragmentRecordDetailLevelTwo.visibility = View.VISIBLE
        if (level >= 3) binding.fragmentRecordDetailLevelThree.visibility = View.VISIBLE
    }

    private fun initListener() {
        binding.fragmentRecordDetailBackBtn.setOnClickListener { finish() }
        binding.fragmentRecordDetailOptionBtn.setOnClickListener {
            showOptionDialog(it)
        }
    }

    private fun initObserve() {
        viewModel.deleteGoody.observe(this) { isDelete ->
            if (isDelete) {
                Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun showOptionDialog(view: View) {
        val dialog = RecordOptionDialog(binding.root.context, view.left, view.bottom,
            object : RecordOptionDialog.OnClickListener {
                override fun onEdit() {
                    goody?.let {
                        ActivityUtil.startUpdateFormActivity(
                            binding.root.context,
                            it,
                            startPostingFormActivityForResult
                        )
                    }
                }

                override fun onDelete() {
                    showDeleteDialog()
                }
            })

        dialog.show()
    }

    private fun showDeleteDialog() {
        val deleteDialog = RecordDeleteDialog(binding.root.context) {
            goody?.id?.let { goodyId -> viewModel.deleteGoody(goodyId) }
        }
        deleteDialog.show()
    }

    companion object {
        private const val ROUNDED_CORNERS_VALUE = 30f
        const val EXTRA_GOODY = "goody"
    }
}