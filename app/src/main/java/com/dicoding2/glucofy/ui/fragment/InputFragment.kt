package com.dicoding2.glucofy.ui.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.glucofy.adapter.FoodAdapter
import com.dicoding2.glucofy.databinding.FragmentInputBinding
import com.dicoding2.glucofy.ui.viewmodel.InputViewModel
import com.dicoding2.glucofy.ui.viewmodel.ViewModelFactory

class InputFragment : Fragment() {

    private lateinit var adapter: FoodAdapter
    private lateinit var viewModel: InputViewModel
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = InputFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = obtainViewModel(requireActivity())

        initRecyclerView()

        setupSearchView()

        return root
    }

    private fun obtainViewModel(activiy: FragmentActivity): InputViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activiy, factory)[InputViewModel::class.java]
    }

    private fun initRecyclerView() {
        adapter = FoodAdapter()
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSearchView() {
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchView.text.toString()
            viewModel.findFoods(query)
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
