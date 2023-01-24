package com.example.smartalarms.mainActivity.viewModel

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartalarms.alarmUtill.AlarmHandler
import com.example.smartalarms.data.AlarmObject
import com.example.smartalarms.database.AlarmDatabase
import com.example.smartalarms.database.daos.AlarmDAO
import com.example.smartalarms.database.entites.AlarmEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Flow
import kotlin.collections.ArrayList

class MainActivityViewModel(private val context: Context) : ViewModel() {



    private val calendar: Calendar = Calendar.getInstance()
    private val repo: AlarmDAO= AlarmDatabase.getInstance(context).alarmDao()
    private val _alarmLiveData = MutableLiveData<List<AlarmObject>>()
    private val alarmLiveData: LiveData<List<AlarmObject>> get() =  _alarmLiveData
    var alarmsList= ArrayList<AlarmObject>()


    fun setAlarm(pos: Int){
      // val alarm= alarmsList[pos]
        val alarmIntent= AlarmHandler.setAlarm(calendar.timeInMillis+1000,context)
      val alarmSet= AlarmObject(Random().nextLong(),false,"10.11","Fri,Sun",alarmIntent)
       saveAlarmInDB(alarmSet)
    }

    private fun saveAlarmInDB(alarm: AlarmObject) {

        viewModelScope.launch(Dispatchers.IO) {
           val entity= AlarmEntity(alarm)
            repo.insert(entity)
        }

    }

    fun getData(): LiveData<List<AlarmEntity>> {
       return repo.getAllAlarms()

    }
    fun removeAlarm(pos: Int){
        val alarmIntent= alarmsList[pos].pendingIntent ?: return
        AlarmHandler.removeAlarm(alarmIntent,context)
    }


}