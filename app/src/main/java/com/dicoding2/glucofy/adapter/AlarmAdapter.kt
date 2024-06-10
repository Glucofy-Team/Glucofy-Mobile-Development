package com.dicoding2.glucofy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.databinding.ItemAlarmBinding
import com.dicoding2.glucofy.helper.OnToggleAlarmListener
import com.dicoding2.glucofy.model.Alarm

class AlarmRecyclerViewAdapter(listener: OnToggleAlarmListener) :
    RecyclerView.Adapter<AlarmViewHolder>() {
    private var alarms: List<Alarm>
    private val listener: OnToggleAlarmListener
    private var itemAlarmBinding: ItemAlarmBinding? = null

    init {
        this.alarms = ArrayList()
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        itemAlarmBinding =
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(itemAlarmBinding!!)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.bind(alarm, listener)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }
}
