package com.app.employeeapp.commonutil

import android.annotation.SuppressLint
import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun getDateTime(time: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return sdf.parse(time).toString()
}

fun circularProgressBar(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.start()
    return circularProgressDrawable
}