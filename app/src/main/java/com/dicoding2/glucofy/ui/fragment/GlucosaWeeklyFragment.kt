package com.dicoding2.glucofy.ui.fragment

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.FragmentGlucosaWeeklyBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class GlucosaWeeklyFragment : Fragment() {
    private var _binding: FragmentGlucosaWeeklyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlucosaWeeklyBinding.inflate(inflater, container, false)
        val candleStickChart = binding.candleStickChart

        val entries = ArrayList<CandleEntry>()
        entries.add(CandleEntry(0f, 200f, 50f, 85f, 88f))
        entries.add(CandleEntry(1f, 200f, 50f, 92f, 94f))
        entries.add(CandleEntry(2f, 200f, 50f, 100f, 103f))
        entries.add(CandleEntry(3f, 200f, 50f, 110f, 113f))
        entries.add(CandleEntry(4f, 200f, 50f, 98f, 101f))

        val candleDataSet = CandleDataSet(entries, "Blood Sugar Levels")
        candleDataSet.color = Color.rgb(220, 54, 68)
        candleDataSet.shadowColor = Color.DKGRAY
        candleDataSet.shadowWidth = 0.7f
        candleDataSet.decreasingColor = R.color.red_500
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL
        candleDataSet.increasingColor = Color.rgb(220, 54, 68)
        candleDataSet.increasingPaintStyle = Paint.Style.FILL
        candleDataSet.neutralColor = R.color.red_500
        candleDataSet.valueTextColor = R.color.red_500
        candleDataSet.valueTextSize = 10f

        val candleData = CandleData(candleDataSet)
        candleStickChart.data = candleData

        val xAxis = candleStickChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("27-2 Mei", "3-9 Juni", "10-16 Juni", "17-23 Juni", "24-30 Juni"))
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        candleStickChart.axisLeft.axisMinimum = 0f
        candleStickChart.axisRight.isEnabled = false
        candleStickChart.description.isEnabled = false
        candleStickChart.invalidate()
        return binding.root
    }

}