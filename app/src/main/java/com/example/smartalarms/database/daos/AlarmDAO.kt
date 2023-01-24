package com.example.smartalarms.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smartalarms.database.entites.AlarmEntity

@Dao
interface AlarmDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: AlarmEntity)

    @Update
    fun update(alarm: AlarmEntity)

    @Delete
    fun delete(alarm: AlarmEntity)

    @Query("delete from alarms")
    fun deleteAllAlarms()

    @Query("select * from alarms order by id desc")
    fun getAllAlarms(): LiveData<List<AlarmEntity>>
}