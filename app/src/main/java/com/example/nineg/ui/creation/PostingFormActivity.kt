package com.example.nineg.ui.creation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.databinding.ActivityPostingFormBinding
import com.example.nineg.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostingFormActivity : BaseActivity<ActivityPostingFormBinding>() {

    private val viewModel: PostingFormViewModel by viewModels()

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val count = p0?.length ?: 0
            binding.activityPostingFormContentCount.text = count.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
            val count = p0?.length ?: 0
            binding.activityPostingFormSaveBtn.isSelected = count > 0
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_posting_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityPostingFormBackBtn.setOnClickListener {
            finish()
        }

        binding.activityPostingFormDateBtn.setOnClickListener {
            // TODO : 달력 팝업 표시
        }

        binding.activityPostingFormImageCancelBtn.setOnClickListener {
            // TODO : Image 등록된거 취소 처리 작업
        }

        binding.activityPostingFormImageContainer.setOnClickListener {
            // TODO : 갤러리 열어서 등록 작업
        }

        binding.activityPostingFormSaveBtn.setOnClickListener {
            // TODO : 저장 가능한지 판단 후 저장 처리
        }

        binding.activityPostingFormContentEditText.addTextChangedListener(textWatcher)

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
    }

    override fun onDestroy() {
        binding.activityPostingFormContentEditText.removeTextChangedListener(textWatcher)
        super.onDestroy()
    }
}