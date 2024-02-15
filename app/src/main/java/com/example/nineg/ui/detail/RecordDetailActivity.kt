package com.example.nineg.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.databinding.ActivityRecordDetailBinding
import com.example.nineg.dialog.RecordDeleteDialog
import com.example.nineg.dialog.RecordOptionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordDetailActivity : BaseActivity<ActivityRecordDetailBinding>() {

    private val viewModel: RecordDetailViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.activity_record_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fragmentRecordDetailOptionBtn.setOnClickListener {
            val dialog = RecordOptionDialog(binding.root.context, it.left, it.bottom,
                object : RecordOptionDialog.OnClickListener {
                    override fun onEdit() {
                        // TODO : 수정하기 로직 추가
                    }

                    override fun onDelete() {
                        val deleteDialog = RecordDeleteDialog(binding.root.context) {
                            viewModel.deleteGoody("72")
                        }
                        deleteDialog.show()
                    }
                })

            dialog.show()
        }
        initObserve()
        viewModel.requestRecordApi()
    }

    private fun initObserve() {
        viewModel.record.observe(this) {
            binding.fragmentRecordDetailImageCard.post {
                binding.fragmentRecordDetailImageCard.load(it.image) {
                    transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
                }
            }

            binding.fragmentRecordDetailImageCardText.text = it.missionTitle
            binding.fragmentRecordDetailContentTitle.text = it.title
            binding.fragmentRecordDetailContentText.text = it.content

            if (it.level >= 1) binding.fragmentRecordDetailLevelOne.visibility = View.VISIBLE
            if (it.level >= 2) binding.fragmentRecordDetailLevelTwo.visibility = View.VISIBLE
            if (it.level >= 3) binding.fragmentRecordDetailLevelThree.visibility = View.VISIBLE
        }

        viewModel.deleteGoody.observe(this) { isDelete ->
            if (isDelete) {
                Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val ROUNDED_CORNERS_VALUE = 30f
    }
}