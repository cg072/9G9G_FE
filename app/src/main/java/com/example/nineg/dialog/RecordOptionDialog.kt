package com.example.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.nineg.R
import com.example.nineg.databinding.DialogRecordOptionBinding

class RecordOptionDialog(
    context: Context,
    rightPosition: Int,
    bottomPosition: Int,
    private val onClickListener: OnClickListener
) :
    Dialog(context, R.style.OptionDialog) {

    private val binding: DialogRecordOptionBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_record_option,
        null,
        false
    )

    init {
        setContentView(binding.root)
        setupLocation(rightPosition, bottomPosition)
    }

    interface OnClickListener {
        fun onEdit()
        fun onDelete()
    }

    override fun show() {
        binding.dialogRecordOptionEditContainer.setOnClickListener {
            onClickListener.onEdit()
            dismiss()
        }

        binding.dialogRecordOptionDeleteContainer.setOnClickListener {
            onClickListener.onDelete()
            dismiss()
        }

        super.show()
    }

    private fun setupLocation(rightPosition: Int, position: Int) {
        window?.setGravity(Gravity.TOP)

        val attr = window?.attributes?.also {
            it.x = rightPosition
            it.y = position
        }

        window?.attributes = attr
    }
}