package com.example.smartalarms.database.typeConverters

import androidx.room.TypeConverter
import com.example.smartalarms.data.AlarmObject
import com.google.gson.Gson

class AlarmTypeConverter {


    @TypeConverter
    fun toAlarmObject(data: String): AlarmObject {
        return  Gson().fromJson(data, AlarmObject::class.java)
    }

    @TypeConverter
    fun fromAlarmObject(alarmObject: AlarmObject): String{
        return Gson().toJson(alarmObject)
    }


}