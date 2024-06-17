package com.dicoding2.glucofy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityCalculatorBinding
import com.dicoding2.glucofy.ui.viewmodel.CalculatorViewModel

class CalculatorActivity : AppCompatActivity() {

    private val items: Array<String> = arrayOf("Insulin Basal","Insulin Pradial")
    private lateinit var calculatorViewModel: CalculatorViewModel
    private lateinit var binding: ActivityCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        calculatorViewModel = CalculatorViewModel()

        val adapter = ArrayAdapter(this, R.layout.input_list_item, items)
        binding.tiInsulin.setAdapter(adapter)

        calculatorViewModel.insulin.observe(this, Observer { insulinEntity ->
            binding.tvMorning.text = insulinEntity.morning.toString()
            binding.tvAfternoon.text = insulinEntity.afternoon.toString()
            binding.tvEvening.text = insulinEntity.evening.toString()
        })

        binding.btnSum.setOnClickListener {
            val insulin = binding.tiInsulin.text.toString()
            val weight = binding.tiWeight.text.toString()
            Log.d("aa12","$insulin - $weight")
            calculatorViewModel.calculateInsulin(insulin,weight.toInt())
        }

        setContentView(binding.root)
    }

}