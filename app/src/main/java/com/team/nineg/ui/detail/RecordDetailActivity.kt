package com.team.nineg.ui.detail

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.team.nineg.R
import com.team.nineg.base.BaseActivity
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.databinding.ActivityRecordDetailBinding
import com.team.nineg.dialog.RecordDeleteDialog
import com.team.nineg.dialog.RecordOptionDialog
import com.team.nineg.extension.getParcelableExtraCompat
import com.team.nineg.ui.calendar.CalendarFragment
import com.team.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RecordDetailActivity : BaseActivity<ActivityRecordDetailBinding>() {

    private val viewModel: RecordDetailViewModel by viewModels()
    private var goody: Goody? = null

    private val startPostingFormActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                goody = result.data?.getParcelableExtraCompat(CalendarFragment.EXTRA_SAVE_GOODY)
                updateGoodyDetail(goody)
                setResult(RESULT_OK)
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.activity_record_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goody = intent?.getParcelableExtraCompat(EXTRA_GOODY)
        initListener()
        initObserve()
        updateGoodyDetail(goody)
    }

    private fun updateGoodyDetail(goody: Goody?) {
        binding.fragmentRecordDetailImageCard.post {
            binding.fragmentRecordDetailImageCard.load(goody?.photoUrl) {
                transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
            }
        }

        binding.fragmentRecordDetailImageCardText.text = goody?.title

        val format = SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault())
        val parseFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = goody?.dueDate?.let { parseFormat.parse(it) } ?: ""
        binding.fragmentRecordDetailContentDate.text = format.format(date)
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
                Toast.makeText(this, R.string.goody_delete_success_message, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, R.string.goody_delete_error_message, Toast.LENGTH_SHORT).show()
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