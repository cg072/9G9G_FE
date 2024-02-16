package com.example.nineg.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.databinding.ActivityRecordDetailBinding
import com.example.nineg.dialog.RecordDeleteDialog
import com.example.nineg.dialog.RecordOptionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordDetailActivity : BaseActivity<ActivityRecordDetailBinding>() {

    private val viewModel: RecordDetailViewModel by viewModels()
    private var goody: Goody? = null

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
            val dialog = RecordOptionDialog(binding.root.context, it.left, it.bottom,
                object : RecordOptionDialog.OnClickListener {
                    override fun onEdit() {
                        // TODO : 수정하기 로직 추가
                        setResult(RESULT_OK)
                    }

                    override fun onDelete() {
                        showDeleteDialog()
                    }
                })

            dialog.show()
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