package com.team.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.team.nineg.R
import com.team.nineg.databinding.DialogRevokeBinding

class RevokeDialog(context: Context, private val onConfirmClick: () -> Unit) :
    Dialog(context, R.style.BasicDialog) {

    private val binding: DialogRevokeBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_revoke,
        null,
        false
    )

    init {
        setContentView(binding.root)
    }

    override fun show() {

        binding.dialogRevokeCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogRevokeBtn.setOnClickListener {
            onConfirmClick()
            dismiss()
        }

        super.show()
    }
}