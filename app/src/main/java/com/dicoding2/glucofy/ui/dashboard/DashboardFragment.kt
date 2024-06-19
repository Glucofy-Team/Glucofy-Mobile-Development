package com.dicoding2.glucofy.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.databinding.FragmentDashboardBinding
import com.dicoding2.glucofy.ui.auth.LoginActivity
import com.dicoding2.glucofy.ui.calculator.CalculatorActivity
import com.dicoding2.glucofy.ui.profile.ProfileActivity
import com.dicoding2.glucofy.ui.food.InputNewFoodActivity
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
            startActivity(intent)
        }

        binding.ivProfileImage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivityForResult(intent, 200)
        }

        binding.fabAddFood.setOnClickListener {
            val intent = Intent(requireContext(), InputNewFoodActivity::class.java)
            intent.putExtra("name", "")
            startActivity(intent)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra("result")
            if (result == "berhasil") {
                startActivity(Intent(requireContext(),LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}