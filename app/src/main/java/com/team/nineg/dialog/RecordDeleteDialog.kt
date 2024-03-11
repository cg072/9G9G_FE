package com.team.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.team.nineg.R
import com.team.nineg.databinding.DialogRecordDeleteBinding

class RecordDeleteDialog(context: Context, private val onDeleteClick: () -> Unit) :
    Dialog(context, R.style.BasicDialog) {

    private val binding: DialogRecordDeleteBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_record_delete,
        null,
        false
    )

    init {
        setContentView(binding.root)
    }

    override fun show() {

        binding.dialogRecordCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogRecordDeleteBtn.setOnClickListener {
            onDeleteClick()
            dismiss()
        }

        super.show()
    }
}