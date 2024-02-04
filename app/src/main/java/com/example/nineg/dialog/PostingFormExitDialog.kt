package com.example.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.nineg.R
import com.example.nineg.databinding.DialogPostingFormExitBinding

class PostingFormExitDialog(context: Context, private val onConfirmClick: () -> Unit) :
    Dialog(context, R.style.BasicDialog) {

    private val binding: DialogPostingFormExitBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_posting_form_exit,
        null,
        false
    )

    init {
        setContentView(binding.root)
    }

    override fun show() {

        binding.dialogPostingFormExitCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogPostingFormExitBtn.setOnClickListener {
            onConfirmClick()
            dismiss()
        }

        super.show()
    }
}