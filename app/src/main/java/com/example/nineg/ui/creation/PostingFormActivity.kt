package com.example.nineg.ui.creation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.base.UiState
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.databinding.ActivityPostingFormBinding
import com.example.nineg.dialog.PostingFormExitDialog
import com.example.nineg.extension.hideKeyboard
import com.example.nineg.ui.calendar.CalendarFragment
import com.example.nineg.util.ImageUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PostingFormActivity : BaseActivity<ActivityPostingFormBinding>() {

    private val viewModel: PostingFormViewModel by viewModels()

    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault())
    private var imageUrl: MultipartBody.Part? = null

    private val titleTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            limitTitleText(p0)
        }

        override fun afterTextChanged(p0: Editable?) {
            binding.activityPostingFormSaveBtn.isSelected = validContent()
        }
    }

    private val contentTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val count = p0?.length ?: 0
            binding.activityPostingFormContentCount.text = count.toString()
        }

        override fun afterTextChanged(p0: Editable?) {}
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
                    imageUrl = ImageUtil.getMultipartBody(contentResolver, it)
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
        initData()
        initContentEditTextActionEvent()
        initListener()
        observe()
        onBackPressedDispatcher.addCallback(this, callback)
        val ssaid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.requestGoodyList(ssaid)
    }

    override fun onDestroy() {
        binding.activityPostingFormTitleEditText.removeTextChangedListener(titleTextWatcher)
        binding.activityPostingFormContentEditText.removeTextChangedListener(contentTextWatcher)
        super.onDestroy()
    }

    private fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_MISSION_CARD, MissionCard::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_MISSION_CARD)
        }?.let { missionCard ->
            binding.activityPostingFormImage.load(missionCard.image) {
                transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
            }
            binding.activityPostingFormTitleEditText.setText(missionCard.title)
            binding.activityPostingFormContentEditText.setText(missionCard.content)
        }

        calendar = Calendar.getInstance()
        binding.activityPostingFormDate.text = format.format(calendar.time)
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
            imageUrl = null
            binding.activityPostingFormImage.setImageDrawable(null)
            binding.activityPostingFormEmptyImageContainer.visibility = View.VISIBLE
        }

        binding.activityPostingFormEmptyImageContainer.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.activityPostingFormSaveBtn.setOnClickListener {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dueDate = inputFormat.format(calendar.time)

            if (!validDueDate(dueDate)) {
                Toast.makeText(this, R.string.goody_due_date_error_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validContent() && imageUrl != null) {
                val ssaid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

                viewModel.registerGoody(
                    ssaid,
                    binding.activityPostingFormTitleEditText.text.toString(),
                    binding.activityPostingFormContentEditText.text.toString(),
                    dueDate,
                    imageUrl!!
                )
            }
        }

        binding.activityPostingFormTitleEditText.addTextChangedListener(titleTextWatcher)
        binding.activityPostingFormContentEditText.addTextChangedListener(contentTextWatcher)

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

    private fun validDueDate(dueDate: String) = !viewModel.getDueDateSet().contains(dueDate)

    private fun observe() {
        viewModel.goodyState.observe(this) { state ->
            when (state) {
                is UiState.Uninitialized -> {

                }
                is UiState.Loading -> {

                }
                is UiState.Empty -> {

                }
                is UiState.Success -> {
                    Toast.makeText(
                        this,
                        R.string.goody_register_success_message,
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent().apply {
                        putExtra(CalendarFragment.EXTRA_SAVE_GOODY, state.data)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
                is UiState.Error -> {
                    Toast.makeText(
                        this,
                        R.string.goody_register_error_message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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

    private fun limitTitleText(sequence: CharSequence?) {
        val textLength = (sequence?.length ?: 0) - 1
        val trimTextLength = sequence?.trim()?.length ?: 0

        if (trimTextLength > MAX_TEXT_LENGTH) {
            binding.activityPostingFormTitleEditText.setText(sequence?.subSequence(0, textLength))
            binding.activityPostingFormTitleEditText.setSelection(textLength)
        }
    }

    companion object {
        private const val ROUNDED_CORNERS_VALUE = 30f
        private const val MIN_YEAR = 2024
        private const val MAX_TEXT_LENGTH = 28
        const val EXTRA_MISSION_CARD = "mission_card"
    }
}