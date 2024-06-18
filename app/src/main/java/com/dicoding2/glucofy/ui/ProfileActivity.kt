package com.dicoding2.glucofy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.entity.UserEntity
import com.dicoding2.glucofy.data.remote.response.Data
import com.dicoding2.glucofy.databinding.ActivityProfileBinding
import com.dicoding2.glucofy.ui.viewmodel.ProfileViewModel
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        profileViewModel = obtainViewModel(this)

        profileViewModel.getUserProfile().observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        //nothing
                    }
                    is Result.Success -> {
                        val data = result.data.data
                        setPreferenceUser(data)
                        binding.username.text = "${data.firstName} ${data.lastName}"
                        binding.email.text = data.email
                    }
                    is Result.Error -> {
                        //nothing
                    }
                }
            }
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            val userPreference = UserPreference(this)

            userPreference.deleteUser()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(binding.root)
    }

    private fun setPreferenceUser(data: Data){
        val userPreference = UserPreference(this)
        val token = userPreference.getUser().token


        var userModel = UserEntity(
            token,
            data.phoneNumber,
            data.gender,
            data.weight.toString(),
            data.age.toString(),
            data.firstName,
            data.lastName,
            data.height.toString(),
            data.email
        )

        userPreference.setUser(userModel)
    }
    private fun obtainViewModel(activity: AppCompatActivity): ProfileViewModel {
        val factory = ViewModelFactory.getInstance(this)
        return ViewModelProvider(activity, factory)[ProfileViewModel::class.java]
    }
}