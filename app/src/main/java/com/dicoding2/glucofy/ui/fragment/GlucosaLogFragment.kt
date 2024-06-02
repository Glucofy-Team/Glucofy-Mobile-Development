package com.dicoding2.glucofy.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.FragmentDashboardBinding
import com.dicoding2.glucofy.databinding.FragmentGlucosaLogBinding
import com.dicoding2.glucofy.ui.AddGlucosaActivity
import com.dicoding2.glucofy.ui.viewmodel.DashboardViewModel
import com.dicoding2.glucofy.ui.viewmodel.GlucosaLogViewModel

class GlucosaLogFragment : Fragment() {
    private var _binding: FragmentGlucosaLogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(GlucosaLogViewModel::class.java)

        _binding = FragmentGlucosaLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGlucosaLog
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.btnAddGlucosa.setOnClickListener {
            startActivity(Intent(requireContext(), AddGlucosaActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}