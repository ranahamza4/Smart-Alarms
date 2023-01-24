package com.example.smartalarms.alarmUtill

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log


private const val TAG = "AlarmHandler"

class AlarmHandler() {


    companion object {
        @SuppressLint("UnspecifiedImmutableFlag")
        fun setAlarm(timeInMillis: Long, context: Context): PendingIntent? {

            return try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmBroadCastReceiver::class.java)
                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getBroadcast(context, 0, intent, 0)
                }
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
                pendingIntent
            } catch (e: Exception) {
                Log.d(TAG, "setAlarm: ${e.message}")
                null
            }


        }

        fun removeAlarm(alarmIntent: PendingIntent, context: Context): Boolean {

            return try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(alarmIntent)
                true

            } catch (e: Exception) {
                Log.d(TAG, "removeAlarm: ${e.message}")
                false
            }
        }
    }

}