package com.dicoding2.glucofy.ui.glucose.monthly

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.databinding.FragmentGlucosaMonthlyBinding
import com.dicoding2.glucofy.ui.glucose.monthly.GlucosaMonthlyViewModel
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GlucosaMonthlyFragment : Fragment() {

    private var _binding: FragmentGlucosaMonthlyBinding? = null
    private val binding get() = _binding!!
    private lateinit var glucosaMonthlyViewModel: GlucosaMonthlyViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        glucosaMonthlyViewModel = obtainViewModel(requireActivity())

        _binding = FragmentGlucosaMonthlyBinding.inflate(inflater, container, false)

        glucosaMonthlyViewModel.getAllDataGlucose().observe(viewLifecycleOwner, Observer { data ->
            updateChartData(data)
        })

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChartData(data: List<GlucoseAverageMonthlyEntity>) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val formattedDate = today.format(formatter)

        data.forEachIndexed { index, glucoseData ->
            entries.add(BarEntry(index.toFloat(), glucoseData.glucose?.toFloat() ?: 0f))
            labels.add(glucoseData.date ?: "")

            if(formattedDate.toString() == glucoseData.date.toString()){
                binding.tvAverageGlucose.text = glucoseData.glucose.toString()
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

    private fun obtainViewModel(activity: FragmentActivity): GlucosaMonthlyViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[GlucosaMonthlyViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        glucosaMonthlyViewModel.getAllDataGlucose().observe(viewLifecycleOwner, Observer { data ->
            updateChartData(data)
        })
    }
}