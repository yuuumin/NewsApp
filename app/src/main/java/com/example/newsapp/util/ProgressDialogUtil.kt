package com.example.newsapp.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.newsapp.R

object ProgressDialogUtil {
    private var mpProgressDialog: AlertDialog? = null

    fun show(context: Context) {
        if (mpProgressDialog == null || !mpProgressDialog!!.isShowing) {
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.progress_dialog, null)

            mpProgressDialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            mpProgressDialog?.show()
        }
    }

    // プログレスダイアログを非表示にする
    fun hide() {
        mpProgressDialog?.dismiss()
        mpProgressDialog = null
    }
}