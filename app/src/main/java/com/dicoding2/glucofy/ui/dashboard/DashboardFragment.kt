package com.dicoding2.glucofy.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.databinding.FragmentDashboardBinding
import com.dicoding2.glucofy.ui.calculator.CalculatorActivity
import com.dicoding2.glucofy.ui.food.InputNewFoodActivity
import com.dicoding2.glucofy.ui.profile.ProfileActivity
import com.dicoding2.glucofy.ui.recomendation.RecomendationActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.ivCalculator.setOnClickListener{
            val intent = Intent(requireContext(), CalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.ivRecomendationFood.setOnClickListener {
            val intent = Intent(requireContext(), RecomendationActivity::class.java)
            startActivityForResult(intent,100)
        }

        binding.ivProfileImage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnFoodInput.setOnClickListener {
            val intent = Intent(requireContext(), InputNewFoodActivity::class.java)
            intent.putExtra("name", "")
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}