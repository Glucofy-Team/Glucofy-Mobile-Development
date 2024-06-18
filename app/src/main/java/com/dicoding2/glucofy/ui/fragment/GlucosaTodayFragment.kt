package com.dicoding2.glucofy.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.databinding.FragmentGlucosaTodayBinding
import com.dicoding2.glucofy.ui.viewmodel.GlucosaTodayViewModel
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class GlucosaTodayFragment : Fragment() {
    private var _binding: FragmentGlucosaTodayBinding? = null

    private lateinit var lineChart: LineChart
    private lateinit var glucosaTodayViewModel: GlucosaTodayViewModel
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        glucosaTodayViewModel = obtainViewModel(requireActivity())

        _binding = FragmentGlucosaTodayBinding.inflate(inflater, container, false)
        lineChart = binding.lineChart

        glucosaTodayViewModel.getAllDataGlucose().observe(viewLifecycleOwner, Observer { data ->
            updateChartData(data)
        })

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChartData(data: List<GlucoseAverageTodayEntity>) {
        var average = 0
        val labels = ArrayList<String>()

        val entries = data.mapIndexed { index, it ->
            average += it.glucose ?: 0
            val dateTime = ZonedDateTime.parse(it.date)
            val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            labels.add(time)
            Entry(index.toFloat(), it.glucose?.toFloat() ?: 0f)
        }

        val dataSet = LineDataSet(entries, "Daily Glucose Level").apply {
            color = Color.parseColor("#FF5C5C")
            valueTextColor = Color.GRAY
            lineWidth = 2f
            setCircleColor(Color.parseColor("#FF5C5C"))
            circleRadius = 5f
            setDrawHighlightIndicators(false)
            setDrawValues(true)
            setDrawFilled(true)
            formLineWidth = 1f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
                fillDrawable = drawable
            } else {
                fillColor = Color.DKGRAY
            }
        }

        if(data.isNotEmpty()){
            binding.tvAverageToday.text = (average/data.size).toString()
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.axisLeft.axisMinimum = 0f
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.legend.isEnabled = false
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)


        val description = Description().apply {
            text = "Time of Day"
            textColor = Color.BLACK
        }
        lineChart.description = description

        val mv = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        lineChart.marker = mv

        lineChart.invalidate()
    }

    class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
        private val tvContent: TextView = findViewById(R.id.tvContent)

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            tvContent.text = "${e?.y} mg/dl"
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF(-(width / 2).toFloat(), -height.toFloat() - 10)
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): GlucosaTodayViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[GlucosaTodayViewModel::class.java]
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        glucosaTodayViewModel.getAllDataGlucose().observe(viewLifecycleOwner, Observer { data ->
            updateChartData(data)
        })
    }
}
