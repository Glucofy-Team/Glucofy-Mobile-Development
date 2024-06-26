package com.dicoding2.glucofy.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.entity.UserEntity
import com.dicoding2.glucofy.data.remote.response.Data
import com.dicoding2.glucofy.databinding.FragmentDashboardBinding
import com.dicoding2.glucofy.ui.calculator.CalculatorActivity
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.dicoding2.glucofy.ui.food.InputTodayFoodActivity
import com.dicoding2.glucofy.ui.glucose.add.AddGlucosaActivity
import com.dicoding2.glucofy.ui.profile.ProfileActivity
import com.dicoding2.glucofy.ui.recomendation.RecomendationActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : DashboardViewModel
    private var userWeight: Int = 0
    private var todayCalor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = obtainViewModel(requireActivity())

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getProfile()
        bindButton()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProfile(){
        viewModel.getUserProfile().observe(viewLifecycleOwner){ result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        //nothing
                    }
                    is Result.Success -> {
                        val data = result.data.data
                        setPreferenceUser(data)
                        setupGraphData()
                        setupLastGlucose()
                        binding.tvHeadingTitle.text = "Hello, ${data.firstName}"
                    }
                    is Result.Error -> {
                        //nothing
                    }
                }
            }
        }
    }

    private fun bindButton(){
        binding.ivCalculator.setOnClickListener{
            val intent = Intent(requireContext(), CalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.ivRecomendationFood.setOnClickListener {
            val intent = Intent(requireContext(), RecomendationActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfileImage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnFoodInput.setOnClickListener {
            val intent = Intent(requireContext(), InputTodayFoodActivity::class.java)
            intent.putExtra("name", "")
            startActivity(intent)
        }

        binding.btnGlucoseLog.setOnClickListener {
            val intent = Intent(requireContext(), AddGlucosaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPreferenceUser(data: Data){
        val userPreference = UserPreference(requireContext())
        val token = userPreference.getUser().token

        userWeight = data.weight

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
    }

    private fun setupGraphData(){
        val maxCalor = userWeight * 25

        viewModel.getTodayFood()
        viewModel.todayFood.observe(viewLifecycleOwner) {response ->
            Log.d("Dashboard", "Assingning todayCalor")
            todayCalor = response.totalCalories
            Log.d("Dashboard", "todayCalor: $todayCalor")
            binding.tvDailyEaten.text = "${todayCalor} / ${maxCalor} kkal"

            val progress = (todayCalor!!.toFloat() / maxCalor.toFloat()) * 100
            binding.progressDaily.progress = progress
        }
    }

    private fun setupLastGlucose(){
        viewModel.lastGlucoseData.observe(viewLifecycleOwner) {lastGlucoseData ->
            if (lastGlucoseData != null){
                binding.tvLastGlucose.text = lastGlucoseData.glucose.toString()
            }
        }
        viewModel.getLastGlucose()
    }

    private fun obtainViewModel(activity: FragmentActivity) : DashboardViewModel{
        val factory = ViewModelFactory.getInstance(requireContext() )
        return ViewModelProvider(this, factory)[DashboardViewModel::class.java]
    }
}