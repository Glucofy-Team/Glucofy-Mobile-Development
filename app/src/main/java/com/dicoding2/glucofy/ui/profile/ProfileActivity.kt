package com.dicoding2.glucofy.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.entity.UserEntity
import com.dicoding2.glucofy.data.remote.response.Data
import com.dicoding2.glucofy.databinding.ActivityProfileBinding
import com.dicoding2.glucofy.ui.auth.LoginActivity
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        profileViewModel = obtainViewModel(this)

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }


        binding.btnLogout.setOnClickListener {
            val userPreference = UserPreference(this)

            userPreference.deleteUser()
            clearGlucoseTables()

            val logoutIntent = Intent("LOGOUT_ACTION")
            LocalBroadcastManager.getInstance(this).sendBroadcast(logoutIntent)

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }

        getProfile()

        setContentView(binding.root)
    }

    private fun clearGlucoseTables() {
        lifecycleScope.launch {
            profileViewModel.clearTableGlucose()
        }
    }

    private fun getProfile() {
        profileViewModel.getUserProfile().observe(this) { result ->
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
    }

    private fun setPreferenceUser(data: Data) {
        val userPreference = UserPreference(this)
        val token = userPreference.getUser().token

        if (data.gender == "L") {
            binding.ivProfile.setImageResource(R.drawable.ic_user_man)
        } else if (data.gender == "P") {
            binding.ivProfile.setImageResource(R.drawable.ic_user_woman)
        }

        val userModel = UserEntity(
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
        setupContainer(userModel)
    }

    private fun obtainViewModel(activity: AppCompatActivity): ProfileViewModel {
        val factory = ViewModelFactory.getInstance(this)
        return ViewModelProvider(activity, factory)[ProfileViewModel::class.java]
    }

    private fun setupContainer(userModel: UserEntity) {
        val container = binding.container
        container.removeAllViews()

        val dataItems = listOf(
            "Phone Number" to userModel.phoneNumber,
            "Age" to userModel.age,
            "Weight" to "${userModel.weight} kg",
            "Height" to "${userModel.height} cm",
        )

        dataItems.forEach { (header, value) ->
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin =
                        resources.getDimensionPixelSize(R.dimen.row_margin_bottom) // Add bottom margin
                }
            }

            val headerTextView = TextView(this).apply {
                text = header
                textSize = 14f
                setTextColor(ContextCompat.getColor(this@ProfileActivity, R.color.black))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val valueTextView = TextView(this).apply {
                text = value
                id = View.generateViewId()
                textSize = 18f
                setTextColor(ContextCompat.getColor(this@ProfileActivity, R.color.black))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            rowLayout.addView(headerTextView)
            rowLayout.addView(valueTextView)

            container.addView(rowLayout)

            val divider = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1/2
                ).apply {
                    setMargins(0, 16, 0, 16)
                }
                setBackgroundColor(ContextCompat.getColor(this@ProfileActivity, R.color.neutrals_600))
            }

            container.addView(divider)
        }
    }

    override fun onResume() {
        super.onResume()
        getProfile()
    }
}