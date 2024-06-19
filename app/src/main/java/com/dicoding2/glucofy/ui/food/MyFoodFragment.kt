package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.MyFoodAdapter
import com.dicoding2.glucofy.databinding.FragmentMyFoodBinding
import com.dicoding2.glucofy.ui.costumview.DividerItemDecoration
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class MyFoodFragment : Fragment() {

    private var _binding: FragmentMyFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : MyFoodViewModel
    private lateinit var adapter : MyFoodAdapter
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

        initRecyleView()

        return root
    }

    private fun obtainViewModel(activity: FragmentActivity): MyFoodViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[MyFoodViewModel::class.java]
    }

    private fun initRecyleView(){
        adapter = MyFoodAdapter()
        binding.rvMyFood.adapter = adapter
        binding.rvMyFood.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), R.drawable.divider_drawable)
        binding.rvMyFood.addItemDecoration(dividerItemDecoration)

    }
}