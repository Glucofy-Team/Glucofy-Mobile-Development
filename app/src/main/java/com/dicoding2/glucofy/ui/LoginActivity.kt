package com.dicoding2.glucofy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.entity.UserEntity
import com.dicoding2.glucofy.data.remote.response.LoginResponse
import com.dicoding2.glucofy.databinding.ActivityLoginBinding
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.helper.toast
import com.dicoding2.glucofy.ui.auth.LoginViewModel
import com.dicoding2.glucofy.ui.auth.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginViewModel: LoginViewModel = LoginViewModel.getInstance(this)

        loginViewModel.isLoading.observe(this){loading ->
            binding.btnLogin.isEnabled = !loading
        }

        loginViewModel.login.observe(this){loginResponse ->
            if(loginResponse.status == 201){
                toast(this@LoginActivity, "Berhasil Login")
            }
//            Injection.provideGlucofyRepository(this)
            setUserData(loginResponse)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.tiEmail.text.toString()
            val password = binding.tiPassword.text.toString()

            loginViewModel.postLogin(email, password)
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        checkUser()
    }

    private fun setUserData(loginResponse: LoginResponse){
        val userPreference = UserPreference(this)
        var userModel = UserEntity(
            loginResponse.token,
        )

        userPreference.setUser(userModel)

        checkUser()
    }

    private fun checkUser(){
        val userPreference = UserPreference(this)

        if (userPreference.getUser().token !== ""){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}