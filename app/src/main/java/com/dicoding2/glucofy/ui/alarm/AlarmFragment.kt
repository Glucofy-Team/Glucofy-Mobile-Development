package com.dicoding2.glucofy.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.AlarmRecyclerViewAdapter
import com.dicoding2.glucofy.databinding.FragmentAlarmBinding
import com.dicoding2.glucofy.helper.OnToggleAlarmListener
import com.dicoding2.glucofy.model.Alarm
import com.dicoding2.glucofy.ui.viewmodel.ViewModelFactory

class AlarmFragment : Fragment(), OnToggleAlarmListener {
    private lateinit var adapter: AlarmRecyclerViewAdapter
    private lateinit var viewModel: AlarmViewModel
    private lateinit var binding: FragmentAlarmBinding
    private var alarmsRecyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AlarmRecyclerViewAdapter(this)

        viewModel = obtainViewModel(requireActivity())

        viewModel.getAlarmsLiveData().observe(this) { alarms ->
            if (alarms != null) {
                adapter.setAlarms(alarms)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val view: View = binding.getRoot()

        alarmsRecyclerView = binding.rvAlarmList
        alarmsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        alarmsRecyclerView!!.adapter = adapter

        val addAlarm = binding.btnAddAlarm
        addAlarm.setOnClickListener { v -> findNavController(v).navigate(R.id.action_alarmsListFragment_to_createAlarmFragment) }

        return view
    }


    override fun onToggle(alarm: Alarm?) {
        if (alarm != null) {
            if (alarm.isStarted) {
                context?.let { alarm.cancelAlarm(it) }
                viewModel.update(alarm)
            } else {
                context?.let { alarm.schedule(it) }
                viewModel.update(alarm)
            }
        }
    }

    override fun onDelete(alarm: Alarm?) {
        if (alarm != null) {
            if (alarm.isStarted) context?.let { alarm.cancelAlarm(it) }
        }
        if (alarm != null) {
            viewModel.delete(alarm.alarmId)
        }
    }

    override fun onItemClick(alarm: Alarm?, view: View?) {
        if (alarm != null) {
            if (alarm.isStarted) context?.let { alarm.cancelAlarm(it) }
        }
        val args = Bundle()
        args.putSerializable(getString(R.string.arg_alarm_obj), alarm)
        findNavController(requireView()).navigate(
            R.id.action_alarmsListFragment_to_createAlarmFragment,
            args
        )
    }

    private fun obtainViewModel(activity: FragmentActivity): AlarmViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[AlarmViewModel::class.java]
    }
}