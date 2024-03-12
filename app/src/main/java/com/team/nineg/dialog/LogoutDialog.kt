package com.team.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.team.nineg.R
import com.team.nineg.databinding.DialogLogoutBinding

class LogoutDialog(context: Context, private val onConfirmClick: () -> Unit) :
    Dialog(context, R.style.BasicDialog) {

    private val binding: DialogLogoutBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_logout,
        null,
        false
    )

    init {
        setContentView(binding.root)
    }

    override fun show() {

        binding.dialogLogoutCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogLogoutBtn.setOnClickListener {
            onConfirmClick()
            dismiss()
        }

        super.show()
    }
}