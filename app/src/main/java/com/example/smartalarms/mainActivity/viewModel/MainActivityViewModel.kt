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


private const val TAG= "MainActivityVM"
class MainActivityViewModel(private val context: Context) : ViewModel() {



    private val calendar: Calendar = Calendar.getInstance()
    private val repo: AlarmDAO= AlarmDatabase.getInstance(context).alarmDao()
    private val _alarmLiveData = MutableLiveData<List<AlarmObject>>()
    private val alarmLiveData: LiveData<List<AlarmObject>> get() =  _alarmLiveData
    var alarmsList= ArrayList<AlarmEntity>()


    fun addNewAlarm(timeInMillis: Long){
        val alarmId = kotlin.random.Random.nextInt()
        val isAlarmSet = AlarmHandler.setAlarm(timeInMillis,context,alarmId)
        if (isAlarmSet){
            val alarmSet= AlarmObject(Random().nextLong(),true,"10.11","Fri,Sun",alarmId)
            saveAlarmInDB(alarmSet)

        }else{
            Log.d(TAG, "addNewAlarm: cannot add new alarm ")
        }
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
    fun deleteAlarm(pos: Int){

        val alarmId= alarmsList[pos].alarm!!.alarmId
        var isRemoved= AlarmHandler.removeAlarm(alarmId,context)
        if (isRemoved){
            viewModelScope.launch(Dispatchers.IO) {
                repo.delete(alarmsList[pos])
            }
        }
    }
    fun turnOnAlarm(pos:Int){
        val alarmId =  alarmsList[pos].alarm!!.alarmId
        val timeInMillis=  alarmsList[pos].alarm!!.longTime
        val isAlarmSet = AlarmHandler.setAlarm(timeInMillis,context,alarmId)
        if (isAlarmSet){
            alarmsList[pos].alarm!!.isOn=true
            updateAlarmStatusInDB(alarmsList[pos])

        }else{
            Log.d(TAG, "addNewAlarm: cannot set  alarm ")
        }
    }
    fun turnOfAlarm(pos:Int){
        val alarmId =  alarmsList[pos].alarm!!.alarmId
        val isAlarmOff = AlarmHandler.removeAlarm(alarmId,context)
        if (isAlarmOff){
            alarmsList[pos].alarm!!.isOn=false
            updateAlarmStatusInDB(alarmsList[pos])

        }else{
            Log.d(TAG, "addNewAlarm: cannot set  alarm ")
        }
    }

    private fun updateAlarmStatusInDB(alarmObject: AlarmEntity) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.update(alarmObject)
        }
    }


    fun deleteAllAlarms(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllAlarms()

        }
    }


}