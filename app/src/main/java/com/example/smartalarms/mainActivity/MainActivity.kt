package com.example.smartalarms.mainActivity


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarms.R
import com.example.smartalarms.alarmUtill.AlarmService
import com.example.smartalarms.data.AlarmObject
import com.example.smartalarms.mainActivity.adapters.MainAdapter
import com.example.smartalarms.mainActivity.viewModel.MainActivityViewModel
import com.example.smartalarms.other.CHANNEL_ID
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


private const val TAG= "MainActivity"

class MainActivity : AppCompatActivity() {
    private var fabVisible = false
    private lateinit var viewModel: MainActivityViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannnel()

        viewModel  = MainActivityViewModel(this)


        val rv = findViewById<RecyclerView>(R.id.recyclerView2)
      var adapter = MainAdapter(emptyList(),switchCallBack)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        viewModel.getData().observe(this){ entity ->
            Log.d(TAG, "onCreate: latest alarms fetched")
//            if (entity.isNotEmpty()){
//            val alarms = ArrayList<AlarmObject>()
//                entity.forEach{
//                    alarms.add(it.alarm!!)
//                }
//                viewModel.alarmsList=alarms
//                adapter.setData(alarms)
//
//
//            }
        }



        val addFab = findViewById<FloatingActionButton>(R.id.extended_fab)
        val singleAlarmFab = findViewById<FloatingActionButton>(R.id.single_alarm)
        val bulkAlarms = findViewById<FloatingActionButton>(R.id.bulk_alarms)

        addFab.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                singleAlarmFab.show()
                bulkAlarms.show()

                // on below line we are setting
                // their visibility to visible.
                bulkAlarms.visibility = View.VISIBLE
                singleAlarmFab.visibility = View.VISIBLE


                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                singleAlarmFab.hide()
                bulkAlarms.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                singleAlarmFab.visibility = View.GONE
                bulkAlarms.visibility = View.GONE


                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }
        singleAlarmFab.setOnClickListener {

            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)

        }

        bulkAlarms.setOnClickListener{
            viewModel.setAlarm(0)
        }


    }

    private val switchCallBack = fun(pos:Int, isChecked:Boolean ) {
        if (isChecked) {
            viewModel.setAlarm(pos)
            Toast.makeText(this, "Alarm $pos Turned On", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.removeAlarm(pos)
            Toast.makeText(this, "Alarm $pos Turned Off", Toast.LENGTH_SHORT).show()

        }

}
    private fun createNotificationChannnel() {
        Log.d("AlarmBroadCastR", "createNotificationChannnel: called")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Smart Alarm Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor= Color.BLUE
                lockscreenVisibility= Notification.VISIBILITY_PRIVATE
            }
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }





}