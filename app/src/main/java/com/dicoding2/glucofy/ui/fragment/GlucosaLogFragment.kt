package com.dicoding2.glucofy.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.SectionPagerGlucosaAdapter
import com.dicoding2.glucofy.databinding.FragmentGlucosaLogBinding
import com.dicoding2.glucofy.ui.AddGlucosaActivity
import com.dicoding2.glucofy.ui.viewmodel.GlucosaLogViewModel
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class GlucosaLogFragment : Fragment() {
    private var _binding: FragmentGlucosaLogBinding? = null

    private lateinit var glucosaLogViewModel: GlucosaLogViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        glucosaLogViewModel = obtainViewModel(requireActivity())

        _binding = FragmentGlucosaLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAddGlucosa.setOnClickListener {
            startActivity(Intent(requireContext(), AddGlucosaActivity::class.java))
        }

        glucosaLogViewModel.getRequestGlucose().observe(viewLifecycleOwner){}
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionPagerGlucosaAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    private fun obtainViewModel(activity: FragmentActivity): GlucosaLogViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[GlucosaLogViewModel::class.java]
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        glucosaLogViewModel.getRequestGlucose().observe(viewLifecycleOwner){}
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
           R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
        const val EXTRA_USER = "extra_user"
    }
}