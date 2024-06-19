package com.dicoding2.glucofy.ui.glucose.weekly

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity
import com.dicoding2.glucofy.databinding.FragmentGlucosaWeeklyBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class GlucosaWeeklyFragment : Fragment() {
    private var _binding: FragmentGlucosaWeeklyBinding? = null
    private val binding get() = _binding!!
    private lateinit var glucosaWeeklyViewModel: GlucosaWeeklyViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlucosaWeeklyBinding.inflate(inflater, container, false)
        glucosaWeeklyViewModel = obtainViewModel(requireActivity())

        glucosaWeeklyViewModel.getAllDataGlucose().observe(viewLifecycleOwner) { data ->
            updateChartData(data)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChartData(data: List<GlucoseAverageWeeklyEntity>) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        val today = LocalDate.now()

        data.forEachIndexed { index, glucoseData ->
            val dateRange = glucoseData.date?.split(" - ")

            entries.add(BarEntry(index.toFloat(), glucoseData.glucose?.toFloat() ?: 0f))

            if (dateRange?.size == 2) {
                val dateStart = dateRange[0].split(" ")
                val dateEnd = dateRange[1].split(" ")

                labels.add("${dateStart[0]}-${dateEnd[0]} ${dateEnd[1]}")

                val startDate = LocalDate.parse(dateRange[0], DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID")))
                val endDate = LocalDate.parse(dateRange[1], DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID")))

                if (today in startDate..endDate) {
                    binding.tvAverageGlucose.text = glucoseData.glucose.toString()
                }
            }
        }

        while (entries.size < 4) {
            val index = entries.size
            entries.add(BarEntry(index.toFloat(), 0f))
            labels.add("")
        }

        val barDataSet = BarDataSet(entries, "Glucose Levels")
        barDataSet.color = Color.parseColor("#DC3644")

        val barData = BarData(barDataSet)
        binding.barChart.data = barData

        binding.barChart.description.isEnabled = false
        binding.barChart.axisLeft.axisMinimum = 0f

        binding.barChart.axisRight.isEnabled = false

        val xAxis = binding.barChart.xAxis
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        binding.barChart.invalidate()
    }

    private fun obtainViewModel(activity: FragmentActivity): GlucosaWeeklyViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[GlucosaWeeklyViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        glucosaWeeklyViewModel.getAllDataGlucose().observe(viewLifecycleOwner) { data ->
            updateChartData(data)
        }
    }
}