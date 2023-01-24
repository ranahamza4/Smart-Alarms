package com.example.smartalarms.database.entites


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartalarms.data.AlarmObject


@Entity(tableName = "alarms")
data class AlarmEntity(
    val alarm: AlarmObject?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null)