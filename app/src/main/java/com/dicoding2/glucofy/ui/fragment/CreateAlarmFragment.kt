package com.dicoding2.glucofy.ui.fragment

import android.app.Activity
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.FragmentCreateAlarmBinding
import com.dicoding2.glucofy.helper.TimePickerUtil
import com.dicoding2.glucofy.model.Alarm
import com.dicoding2.glucofy.ui.viewmodel.CreateAlarmViewModel
import com.dicoding2.glucofy.ui.viewmodel.ViewModelFactory
import java.util.Random

class CreateAlarmFragment : Fragment() {

    private var _binding: FragmentCreateAlarmBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CreateAlarmViewModel

    private lateinit var ringtone: Ringtone
    private var isVibrate = false
    var alarm: Alarm? = null
    private lateinit var tone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAlarmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tone = RingtoneManager.getActualDefaultRingtoneUri(this.context, RingtoneManager.TYPE_ALARM).toString()
        ringtone = RingtoneManager.getRingtone(context, Uri.parse(tone))

        binding.tvSetToneName.text = ringtone.getTitle(context)
        if (alarm != null) {
            updateAlarmInfo(alarm)
        }
        binding.cbRecurring.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.recurringOptions.visibility = View.VISIBLE
            } else {
                binding.recurringOptions.visibility = View.GONE
            }
        }

        binding.btnAddAlarm.setOnClickListener { v ->
            if (alarm != null) {
                updateAlarm()
            } else {
                scheduleAlarm()
            }
            findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment)
        }

        binding.cardSound.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound")
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(tone) as Uri)
            startActivityForResult(intent, 5)
        }

        binding.switchVibrate.setOnCheckedChangeListener { _, b ->
            isVibrate = b
        }

//        binding.timePicker.setOnTimeChangedListener { timePicker, i, i1 ->
//            binding.fragmentCreatealarmScheduleAlarmHeading.setText(
//                DayUtil.getDay(
//                    TimePickerUtil.getTimePickerHour(timePicker),
//                    TimePickerUtil.getTimePickerMinute(timePicker)
//                )
//            )
//        }
        return root
    }

    private fun obtainViewModel(activity: FragmentActivity): CreateAlarmViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[CreateAlarmViewModel::class.java]
    }

    private fun scheduleAlarm() {
        var alarmTitle = getString(R.string.alarm_title)
        val alarmId = Random().nextInt(Int.MAX_VALUE)
        if (binding.edTitle.getText().toString().isNotEmpty()) {
            alarmTitle = binding.edTitle.getText().toString()
        }
        val alarm: Alarm = Alarm(
            alarmId,
            TimePickerUtil.getTimePickerHour(binding.timePicker),
            TimePickerUtil.getTimePickerMinute(binding.timePicker),
            alarmTitle,
            true,
            binding.cbRecurring.isChecked,
            binding.checkMon.isChecked,
            binding.checkTue.isChecked,
            binding.checkWed.isChecked,
            binding.checkThu.isChecked,
            binding.checkFri.isChecked,
            binding.checkSat.isChecked,
            binding.checkSun.isChecked,
            tone,
            isVibrate
        )

        viewModel.insert(alarm)

        context?.let { alarm.schedule(it) }
    }

    private fun updateAlarm() {
        var alarmTitle = getString(R.string.alarm_title)
        //        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        if (binding.edTitle.getText().toString().isNotEmpty()) {
            alarmTitle = binding.edTitle.getText().toString()
        }
        val updatedAlarm: Alarm = Alarm(
            (alarm?.alarmId ?: 0),
            TimePickerUtil.getTimePickerHour(binding.timePicker),
            TimePickerUtil.getTimePickerMinute(binding.timePicker),
            alarmTitle,
            true,
            binding.cbRecurring.isChecked,
            binding.checkMon.isChecked,
            binding.checkTue.isChecked,
            binding.checkWed.isChecked,
            binding.checkThu.isChecked,
            binding.checkFri.isChecked,
            binding.checkSat.isChecked,
            binding.checkSun.isChecked,
            tone,
            isVibrate
        )
        viewModel.update(updatedAlarm)
        context?.let { updatedAlarm.schedule(it) }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            val uri = intent!!.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            ringtone = RingtoneManager.getRingtone(context, uri)
            val title = ringtone.getTitle(context)
            if (uri != null) {
                tone = uri.toString()
                if (title != null && title.isNotEmpty()) binding.tvSetToneName.text = title
            } else {
                binding.tvSetToneName.text = ""
            }
        }
    }

    private fun updateAlarmInfo(alarm: Alarm?) {
        if (alarm != null) {
            binding.edTitle.setText(alarm.title)
            binding.timePicker.hour = alarm.hour
            binding.timePicker.minute = alarm.minute

            if (alarm.isRecurring) {
                binding.cbRecurring.setChecked(true)
                binding.recurringOptions.visibility = View.VISIBLE
                when {
                    alarm.isMonday -> binding.checkMon.isChecked = true
                    alarm.isTuesday -> binding.checkTue.isChecked = true
                    alarm.isWednesday -> binding.checkWed.isChecked = true
                    alarm.isThursday -> binding.checkThu.isChecked = true
                    alarm.isFriday -> binding.checkFri.isChecked = true
                    alarm.isSaturday -> binding.checkSat.isChecked = true
                    alarm.isSunday -> binding.checkSun.isChecked = true
                }
                tone = alarm.tone
                ringtone = RingtoneManager.getRingtone(context, Uri.parse(tone))
                binding.tvSetToneName.text = ringtone.getTitle(
                    context
                )
                if (alarm.isVibrate) binding.switchVibrate.setChecked(
                    true
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}