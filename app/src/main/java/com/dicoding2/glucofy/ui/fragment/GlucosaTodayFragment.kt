package com.dicoding2.glucofy.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.FragmentGlucosaTodayBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class GlucosaTodayFragment : Fragment() {
    private var _binding: FragmentGlucosaTodayBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlucosaTodayBinding.inflate(inflater, container, false)
        val lineChart = binding.lineChart
        setupLineChart(lineChart)
        return binding.root
    }
    private fun setupLineChart(lineChart: LineChart) {
        val entries = listOf(
            Entry(8f, 60f),
            Entry(13f, 135f),
            Entry(15f, 95f),
            Entry(19f, 175f)
        )

        val dataSet = LineDataSet(entries, "Blood Glucose")
        dataSet.color = Color.parseColor("#FF5C5C") // Line color
        dataSet.setCircleColor(Color.parseColor("#FF5C5C")) // Circle color
        dataSet.circleRadius = 5f
        dataSet.circleHoleRadius = 2.5f
        dataSet.lineWidth = 2f
        dataSet.setDrawHighlightIndicators(false)
        dataSet.setDrawValues(false)

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        // Customize chart appearance
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.axisLeft.axisMaximum = 200f
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false

        // Set padding for X and Y axes
        lineChart.setViewPortOffsets(2f, 2f, 2f, 2f)

        // Custom MarkerView
        val mv = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        lineChart.marker = mv

        lineChart.invalidate() // refresh
    }

    private class XAxisValueFormatter : com.github.mikephil.charting.formatter.ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return when (value.toInt()) {
                8 -> "08.00"
                13 -> "13.00"
                15 -> "15.00"
                19 -> "19.00"
                else -> value.toString()
            }
        }
    }

    class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
        private val tvContent: TextView = findViewById(R.id.tvContent)

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            tvContent.text = "${e?.y} mg/dl"
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            // Offset to position the marker text below the point
            return MPPointF(-(width / 2).toFloat(), -height.toFloat() - 10)
        }
    }

}