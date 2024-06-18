package com.dicoding2.glucofy.ui.glucose.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityAddGlucosaBinding
import com.dicoding2.glucofy.helper.toast
import java.util.Calendar

class AddGlucosaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGlucosaBinding
    private lateinit var addGlucosaViewModel: AddGlucosaViewModel
    private val items: Array<String> = arrayOf("puasa","acak","2 jam setelah makan")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGlucosaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val adapter = ArrayAdapter(this, R.layout.input_list_item, items)
        binding.tiCondition.setAdapter(adapter)

        addGlucosaViewModel = AddGlucosaViewModel.getInstance(this)

        addGlucosaViewModel.glucosa.observe(this){loginResponse ->
            if(loginResponse.status == 201){
                toast(this@AddGlucosaActivity, "Berhasil Menambah Data")
                addGlucosaViewModel.clearGlucosaData()
                finish()
            }else if(loginResponse.status == 400){
                addGlucosaViewModel.clearGlucosaData()
                toast(this@AddGlucosaActivity, "Gagal Menambah Data")
            }
        }

        addGlucosaViewModel.isLoading.observe(this){ loading ->
            binding.btnSubmit.isEnabled = !loading
        }

        binding.tiDate.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                    binding.tiDate.setText(formattedDate)
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
                    val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    binding.tiTime.setText(formattedTime)
                },
                hour,
                minute,
                false
            )

            timePickerDialog.show()
        }

        binding.tiGlucosaLevel.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiCondition.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiNotes.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiDate.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiTime.addTextChangedListener {
            updateSubmitButtonState()
        }

        binding.btnSubmit.setOnClickListener {
            val glucosa = binding.tiGlucosaLevel.text.toString()
            val condition = binding.tiCondition.text.toString()
            val notes = binding.tiNotes.text.toString()
            val date = binding.tiDate.text.toString()
            val time = binding.tiTime.text.toString()

            addGlucosaViewModel.postGlucosa(glucosa,condition,notes, "$date $time")
        }
        updateSubmitButtonState()
    }

    private fun updateSubmitButtonState() {
        val isAllFieldsFilled = binding.tiGlucosaLevel.text?.isNotEmpty() == true &&
                binding.tiCondition.text.isNotEmpty() &&
                binding.tiNotes.text?.isNotEmpty() == true  &&
                binding.tiDate.text?.isNotEmpty() == true &&
                binding.tiTime.text?.isNotEmpty() == true

        binding.btnSubmit.isEnabled = isAllFieldsFilled
    }
}