package com.dicoding2.glucofy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.databinding.ActivityProfileEditBinding
import com.dicoding2.glucofy.ui.viewmodel.ProfileEditViewModel

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var profileEditViewModel: ProfileEditViewModel
    private val items: Array<String> = arrayOf("Laki-laki", "Perempuan")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        profileEditViewModel = ProfileEditViewModel.getInstance(this)

        val adapter = ArrayAdapter(this, R.layout.input_list_item, items)
        binding.tiGender.setAdapter(adapter)

        getUser()

        profileEditViewModel.isLoading.observe(this){
            binding.btnSubmit.isEnabled = !it
        }

        profileEditViewModel.profileUpdate.observe(this){response ->
            Log.d("test12",response.toString())
        }

        binding.btnSubmit.setOnClickListener {
            val firstname = binding.tiFirstName.text.toString()
            val lastname = binding.tiLastName.text.toString()
            val phonenumber = binding.tiPhoneNumber.text.toString()
            val password = binding.tiPassword.text.toString()
            val gender = binding.tiGender.text.toString()
            val weight = binding.tiWeight.text.toString()
            val height = binding.tiHeight.text.toString()
            val age = binding.tiAge.text.toString()

            profileEditViewModel.postEditProfile(firstname, lastname, phonenumber,password, gender, weight, height, age)
        }

        setContentView(binding.root)
    }

    private fun getUser(){
        val userPreference = UserPreference(this)
        val data = userPreference.getUser()

        binding.tvProfile.text = data.firstName+" "+data.lastName
        binding.tiFirstName.setText(data.firstName.toString())
        binding.tiLastName.setText(data.lastName)
        binding.tiPhoneNumber.setText(data.phoneNumber)
        binding.tvEmail.text = data.email
        binding.tiAge.setText(data.age)
        binding.tiGender.setText(data.gender)
        binding.tiHeight.setText(data.height)
        binding.tiWeight.setText(data.weight)

    }

}