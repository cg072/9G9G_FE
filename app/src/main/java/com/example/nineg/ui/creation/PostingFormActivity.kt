package com.example.nineg.ui.creation

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.databinding.ActivityPostingFormBinding
import com.example.nineg.dialog.PostingFormExitDialog
import com.example.nineg.extension.hideKeyboard
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PostingFormActivity : BaseActivity<ActivityPostingFormBinding>() {

    private val viewModel: PostingFormViewModel by viewModels()

    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault())

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val count = p0?.length ?: 0
            binding.activityPostingFormContentCount.text = count.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
            binding.activityPostingFormSaveBtn.isSelected = validContent()
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                binding.activityPostingFormImage.post {
                    binding.activityPostingFormImage.load(it) {
                        transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
                    }

                    binding.activityPostingFormEmptyImageContainer.visibility = View.GONE
                    binding.activityPostingFormSaveBtn.isSelected = validContent()
                }
            }
        }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showExitDialog()
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_posting_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendar = Calendar.getInstance()
        binding.activityPostingFormDate.text = format.format(calendar.time)
        initContentEditTextActionEvent()
        initListener()
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroy() {
        binding.activityPostingFormTitleEditText.removeTextChangedListener(textWatcher)
        super.onDestroy()
    }

    private fun initContentEditTextActionEvent() {
        binding.activityPostingFormContentEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.activityPostingFormContentEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    private fun initListener() {
        binding.activityPostingFormBackBtn.setOnClickListener {
            showExitDialog()
        }

        binding.activityPostingFormDateBtn.setOnClickListener {
            showDatePicker()
        }

        binding.activityPostingFormImageCancelBtn.setOnClickListener {
            binding.activityPostingFormImage.setImageDrawable(null)
            binding.activityPostingFormEmptyImageContainer.visibility = View.VISIBLE
        }

        binding.activityPostingFormImageContainer.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.activityPostingFormSaveBtn.setOnClickListener {
            if (validContent()) {
                // TODO : calendar 이용하여 저장 처리 로직 추가
                Log.d("PostingFormActivity", "Goody Card save")
            }
        }

        binding.activityPostingFormTitleEditText.addTextChangedListener(textWatcher)

        binding.activityPostingFormTitleEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                v.hideKeyboard()
            }
        }

        binding.activityPostingFormContentEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                v.hideKeyboard()
            }
        }

        binding.activityPostingFormContentEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.activityPostingFormSaveBtn.performClick()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun showExitDialog() {
        val dialog = PostingFormExitDialog(this) {
            finish()
        }

        dialog.show()
    }

    private fun showDatePicker() {
        val periodSettingCalendar = Calendar.getInstance()
        periodSettingCalendar[Calendar.YEAR] = MIN_YEAR
        periodSettingCalendar[Calendar.MONTH] = Calendar.JANUARY

        val janThisYear = periodSettingCalendar.timeInMillis

        val constraintsBuilder = CalendarConstraints.Builder().setStart(janThisYear)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(calendar.timeInMillis)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            calendar.timeInMillis = it
            binding.activityPostingFormDate.text = format.format(calendar.time)
        }

        datePicker.show(supportFragmentManager, "date_picker")
    }

    private fun validContent() =
        !binding.activityPostingFormEmptyImageContainer.isVisible && binding.activityPostingFormTitleEditText.length() > 0

    companion object {
        private const val ROUNDED_CORNERS_VALUE = 30f
        private const val MIN_YEAR = 2024

    }
}