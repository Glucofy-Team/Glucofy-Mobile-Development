package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.glucofy.adapter.FoodAdapter
import com.dicoding2.glucofy.databinding.FragmentExploreFoodBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import kotlinx.coroutines.launch

class ExploreFoodFragment : Fragment() {

    private lateinit var adapter: FoodAdapter
    private lateinit var viewModel: ExploreFoodViewModel
    private var _binding: FragmentExploreFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = obtainViewModel(requireActivity())

        setupSearchView()
        initRecyclerView()
        observeData()

        return root
    }

    private fun obtainViewModel(activity: FragmentActivity): ExploreFoodViewModel {
        val factory = ViewModelFactory.getInstance(requireContext())
        return ViewModelProvider(activity, factory)[ExploreFoodViewModel::class.java]
    }

    private fun initRecyclerView() {
        adapter = FoodAdapter()
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.addTextChangedListener {editable ->
                val query = editable.toString()
                viewModel.findFoods(query)
            }
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                binding.searchBar.setText(binding.searchView.text)
                searchView.hide()
                val query = searchView.text.toString()
                viewModel.findFoods(query)
                true
            }
        }
    }

    private fun observeData() {
        viewModel.food.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                pagingData?. let {
                    adapter.submitData(it)
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
