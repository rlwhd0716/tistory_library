package com.example.alertdialogex

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.showAlertDialog() {
    AlertDialog.Builder(this).apply {
        setTitle("제목")
        setMessage("내용")
        setCancelable(false)
        setPositiveButton("확인") { dialog, which ->
            // 확인 눌렀을 때 동작
            dialog.dismiss()
        }
        setNegativeButton("취소") { dialog, whick ->
            // 취소 눌렀을 때 동작
            dialog.dismiss()
        }
    }.show()
}