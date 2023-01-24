package com.example.smartalarms.mainActivity.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.smartalarms.data.AlarmObject

class MainAlarmDiffUtill(
    private val oldList: List<AlarmObject>,
    private val newList: List<AlarmObject>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}