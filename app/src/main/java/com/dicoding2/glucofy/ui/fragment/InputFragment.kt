package com.dicoding2.glucofy.ui.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.FoodAdapter
import com.dicoding2.glucofy.databinding.FragmentInputBinding
import com.dicoding2.glucofy.ui.viewmodel.InputViewModel

class InputFragment : Fragment() {

    private lateinit var adapter : FoodAdapter
    private var _binding : FragmentInputBinding? = null
    private val binding get() =  _binding!!

    companion object {
        fun newInstance() = InputFragment()
    }

    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{_,_,_ ->
                    binding.searchBar.setText(binding.searchView.text)
                    searchView.hide()
                    val query = searchView.text.toString()
                }
        }

        return root
    }

    private fun showFood(){
        adapter = FoodAdapter()
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(context)
    }
}