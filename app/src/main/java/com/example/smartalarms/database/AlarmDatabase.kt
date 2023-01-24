package com.example.smartalarms.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smartalarms.database.daos.AlarmDAO
import com.example.smartalarms.database.entites.AlarmEntity
import com.example.smartalarms.database.typeConverters.AlarmTypeConverter

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(AlarmTypeConverter::class)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDAO

    companion object {
        private var instance: AlarmDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AlarmDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, AlarmDatabase::class.java,
                    "alarm_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }

    }



}
