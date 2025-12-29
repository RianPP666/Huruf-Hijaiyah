package com.example.hurufhijaiyah

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    fun showConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "Ya",
        negativeButtonText: String = "Batal",
        onPositiveClick: (DialogInterface, Int) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText, onPositiveClick)
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
