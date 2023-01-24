package com.example.smartalarms.alarmUtill

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log


private const val TAG= "AlarmBroadCastR"
class AlarmBroadCastReceiver : BroadcastReceiver()  {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(TAG, "onReceive: playing sound")
        startAlarmService(p0!!,p1!!)
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra("Title", "Yo Yo")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }





}