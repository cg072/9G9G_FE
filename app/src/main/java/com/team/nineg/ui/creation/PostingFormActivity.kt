package com.team.nineg.ui.creation

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.team.nineg.R
import com.team.nineg.base.BaseActivity
import com.team.nineg.base.UiState
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.databinding.ActivityPostingFormBinding
import com.team.nineg.dialog.PostingFormExitDialog
import com.team.nineg.extension.getParcelableExtraCompat
import com.team.nineg.extension.hideKeyboard
import com.team.nineg.ui.calendar.CalendarFragment
import com.team.nineg.util.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PostingFormActivity : BaseActivity<ActivityPostingFormBinding>() {

    private val viewModel: PostingFormViewModel by viewModels()
    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault())
    private var imageMultipart: MultipartBody.Part? = null
    private var updateGoodyInfo: Goody? = null

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
                    imageMultipart = ImageUtil.getMultipartBody(contentResolver, it)
                }
            }
        }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isNotEmptyContent()) showExitDialog() else finish()
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
        viewModel.requestGoodyList()
    }

    override fun onDestroy() {
        binding.activityPostingFormTitleEditText.removeTextChangedListener(titleTextWatcher)
        binding.activityPostingFormContentEditText.removeTextChangedListener(contentTextWatcher)
        super.onDestroy()
    }

    private fun initData() {
        calendar = Calendar.getInstance()

        intent?.getParcelableExtraCompat<MissionCard>(EXTRA_MISSION_CARD)?.let { missionCard ->
            binding.activityPostingFormTitleEditText.setText(missionCard.title)
            binding.activityPostingFormContentEditText.hint = missionCard.guide
            binding.activityPostingFormSaveBtn.isSelected = validContent()
        }

        intent?.getParcelableExtraCompat<Goody>(EXTRA_UPDATE_GOODY)?.let { goody ->
            binding.activityPostingFormImage.load(goody.photoUrl) {
                transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
            }
            binding.activityPostingFormTitleEditText.setText(goody.title)
            binding.activityPostingFormContentEditText.setText(goody.content)

            binding.activityPostingFormEmptyImageContainer.visibility = View.GONE
            binding.activityPostingFormSaveBtn.isSelected = true

            setImageMultipartBody(goody.photoUrl)

            updateGoodyInfo = goody

            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.parse(goody.dueDate)?.let { calendar.time = it }
        }

        binding.activityPostingFormDate.text = format.format(calendar.time)
    }

    private fun initContentEditTextActionEvent() {
        binding.activityPostingFormContentEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.activityPostingFormContentEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    private fun initListener() {
        binding.activityPostingFormBackBtn.setOnClickListener {
            if (isNotEmptyContent()) showExitDialog() else finish()
        }

        binding.activityPostingFormDateBtn.setOnClickListener {
            showDatePicker()
        }

        binding.activityPostingFormImageCancelBtn.setOnClickListener {
            imageMultipart = null
            binding.activityPostingFormImage.setImageDrawable(null)
            binding.activityPostingFormEmptyImageContainer.visibility = View.VISIBLE
            binding.activityPostingFormSaveBtn.isSelected = validContent()
        }

        binding.activityPostingFormEmptyImageContainer.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.activityPostingFormSaveBtn.setOnClickListener {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dueDate = inputFormat.format(calendar.time)

            if (updateGoodyInfo == null) {
                registerGoody(dueDate)
            } else {
                updateGoody(dueDate)
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

    private fun registerGoody(dueDate: String) {
        if (!validDueDate(dueDate)) {
            Toast.makeText(this, R.string.goody_due_date_error_message, Toast.LENGTH_SHORT).show()
            return
        }

        if (validContent() && imageMultipart != null) {
            viewModel.registerGoody(
                binding.activityPostingFormTitleEditText.text.toString(),
                binding.activityPostingFormContentEditText.text.toString(),
                dueDate,
                imageMultipart!!
            )
        }
    }

    private fun updateGoody(dueDate: String) {
        if (!validDueDate(dueDate) && dueDate != updateGoodyInfo?.dueDate) {
            Toast.makeText(this, R.string.goody_due_date_error_message, Toast.LENGTH_SHORT).show()
            return
        }

        if (validContent() && imageMultipart != null) {
            updateGoodyInfo?.id?.let { goodyId ->
                viewModel.updateGoody(
                    goodyId,
                    binding.activityPostingFormTitleEditText.text.toString(),
                    binding.activityPostingFormContentEditText.text.toString(),
                    dueDate,
                    imageMultipart
                )
            }
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
                .setTitleText(R.string.date_picker_title)
                .setSelection(calendar.timeInMillis)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            calendar.timeInMillis = it
            binding.activityPostingFormDate.text = format.format(calendar.time)
        }

        datePicker.show(supportFragmentManager, DATE_PICKER_TAG)
    }

    private fun validContent() =
        !binding.activityPostingFormEmptyImageContainer.isVisible && binding.activityPostingFormTitleEditText.length() > 0

    private fun isNotEmptyContent() =
        !binding.activityPostingFormEmptyImageContainer.isVisible || binding.activityPostingFormTitleEditText.length() > 0 || binding.activityPostingFormContentEditText.length() > 0
    
    private fun limitTitleText(sequence: CharSequence?) {
        val textLength = (sequence?.length ?: 0) - 1
        val trimTextLength = sequence?.trim()?.length ?: 0

        if (trimTextLength > MAX_TEXT_LENGTH) {
            binding.activityPostingFormTitleEditText.setText(sequence?.subSequence(0, textLength))
            binding.activityPostingFormTitleEditText.setSelection(textLength)
        }
    }

    private fun setImageMultipartBody(photoUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val loader = ImageLoader(this@PostingFormActivity)
            val request = ImageRequest.Builder(this@PostingFormActivity)
                .data(photoUrl)
                .allowHardware(false)
                .build()

            val bitmap = when (val responseResult = loader.execute(request)) {
                is SuccessResult -> {
                    (responseResult.drawable as BitmapDrawable).bitmap
                }
                is ErrorResult -> {
                    (responseResult.drawable as BitmapDrawable).bitmap
                }
            }

            imageMultipart = ImageUtil.getMultipartBody(bitmap)
        }
    }

    companion object {
        private const val TAG = "PostingFormActivity"
        private const val ROUNDED_CORNERS_VALUE = 30f
        private const val MIN_YEAR = 2024
        private const val MAX_TEXT_LENGTH = 28
        private const val DATE_PICKER_TAG = "date_picker"
        const val EXTRA_MISSION_CARD = "mission_card"
        const val EXTRA_UPDATE_GOODY = "update_goody"
    }
}