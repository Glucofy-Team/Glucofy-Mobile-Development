package com.dicoding2.glucofy.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityAddGlucosaBinding
import com.dicoding2.glucofy.helper.toast
import com.dicoding2.glucofy.ui.viewmodel.AddGlucosaViewModel
import com.dicoding2.glucofy.ui.viewmodel.LoginViewModel
import java.util.Calendar

class AddGlucosaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGlucosaBinding
    private val items: Array<String> = arrayOf("puasa","acak","2 jam setelah makan")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGlucosaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val adapter = ArrayAdapter(this, R.layout.input_list_item, items)
        binding.tiCondition.setAdapter(adapter)

        val addGlucosaViewModel: AddGlucosaViewModel = AddGlucosaViewModel.getInstance(this)

        addGlucosaViewModel.glucosa.observe(this){loginResponse ->
            if(loginResponse.status == 201){
                toast(this@AddGlucosaActivity, "Berhasil Menambah Data")
            }
        }

        binding.tiDate.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.tiDate.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.tiTime.setOnClickListener {
            val c = Calendar.getInstance()

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->
                    binding.tiTime.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )

            timePickerDialog.show()
        }

        binding.btnSubmit.setOnClickListener {
            val glucosa = binding.tiGlucosaLevel.text.toString()
            val condition = binding.tiCondition.text.toString()
            val notes = binding.tiNotes.text.toString()
            val date = "${binding.tiDate.text.toString()} ${binding.tiTime.text.toString()}:00"


            addGlucosaViewModel.postGlucosa(glucosa,condition,notes, date)
        }

    }
}