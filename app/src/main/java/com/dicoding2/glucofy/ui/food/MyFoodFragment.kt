package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.databinding.FragmentMyFoodBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class MyFoodFragment : Fragment() {

    private var _binding: FragmentMyFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : MyFoodViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFoodBinding.inflate(inflater, container, false )
        val root: View = binding.root

        viewModel = obtainViewModel(requireActivity())

        return root


    }

    private fun obtainViewModel(activity: FragmentActivity): MyFoodViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[MyFoodViewModel::class.java]
    }
}