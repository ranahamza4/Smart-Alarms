package com.example.smartalarms.data

import android.app.PendingIntent

data class AlarmObject(
    var longTime: Long,
    var isOn: Boolean,
    var timeString: String,
    var day: String,
    val pendingIntent: PendingIntent?=null
)