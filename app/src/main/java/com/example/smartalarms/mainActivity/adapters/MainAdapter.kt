package com.example.smartalarms.mainActivity.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarms.R
import com.example.smartalarms.data.AlarmObject


class MainAdapter(
    private var mList: List<AlarmObject>,
    private val switchCallBack: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_rv_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.alarmTime.text = itemsViewModel.timeString
        holder.alarmDay.text = itemsViewModel.day

        if (itemsViewModel.isOn){
            holder.switchBtn.isChecked= true
        }
        holder.switchBtn.setOnCheckedChangeListener { _, isChecked ->

            switchCallBack(position, isChecked)

        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val alarmTime: TextView = itemView.findViewById(R.id.alarm_time)
        val switchBtn: SwitchMaterial = itemView.findViewById(R.id.alarm_on_off_btn)
        val alarmDay: TextView = itemView.findViewById(R.id.alarm_date)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<AlarmObject>) {
//        val alarmsDiff = MainAlarmDiffUtill(mList, newData)
//        val diffUtilResult = DiffUtil.calculateDiff(alarmsDiff)
        mList = newData
        this.notifyDataSetChanged()
     //   diffUtilResult.dispatchUpdatesTo(this)
    }
}