package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.util.Log
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

    private lateinit var viewModel : FoodViewModel
    private lateinit var adapter : MyFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFoodBinding.inflate(inflater, container, false )
        val root: View = binding.root

        viewModel = obtainViewModel(requireActivity())

        initRecycleView()
        observeData()

        Log.d("MyFoodFragment", "onCreateView called")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyFoodFragment", "onViewCreated called")
    }

    override fun onResume() {
        super.onResume()
        observeData()
        Log.d("MyFoodFragment", "onResume called")
    }

    private fun obtainViewModel(activity: FragmentActivity): FoodViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[FoodViewModel::class.java]
    }

    private fun initRecycleView(){
        adapter = MyFoodAdapter()
        binding.rvMyFood.adapter = adapter
        binding.rvMyFood.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), R.drawable.divider_drawable)
        binding.rvMyFood.addItemDecoration(dividerItemDecoration)

    }

    private fun observeData(){
        viewModel.findMyFoods()
        viewModel.myFood.observe(viewLifecycleOwner){foodReponse ->
            if (foodReponse != null){
                adapter.submitList(foodReponse.myFoodListItem)
                Log.d("MyFoodFragment", "observeData called")
            } else {
                adapter.submitList(null)
                Log.d("MyFoodFragment", "foodResponse is empty")
            }
        }
    }
}