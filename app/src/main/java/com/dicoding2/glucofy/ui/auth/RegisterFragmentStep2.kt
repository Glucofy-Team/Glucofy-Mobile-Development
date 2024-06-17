package com.dicoding2.glucofy.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.RegisterPreference
import com.dicoding2.glucofy.databinding.FragmentRegisterStep2Binding
import com.dicoding2.glucofy.ui.RegisterSuccessActivity


class RegisterFragmentStep2 : Fragment() {
    private var _binding: FragmentRegisterStep2Binding? = null
    private val binding get() = _binding!!
    private val items: Array<String> = arrayOf(getString(R.string.man), getString(R.string.woman))
    private var gender: String = ""
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterStep2Binding.inflate(inflater, container, false)

        registerViewModel = RegisterViewModel.getInstance(requireContext())

        registerViewModel.register.observe(viewLifecycleOwner, Observer { registerResponse ->
            if (registerResponse.status == 201) {
                startActivity(Intent(requireContext(), RegisterSuccessActivity::class.java))
                requireActivity().finish()
            }
        })

        registerViewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            binding.btnRegister.isEnabled = !loading
        })

        val adapter = ArrayAdapter(requireContext(), R.layout.input_list_item, items)
        binding.tiGender.setAdapter(adapter)

        binding.tiGender.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            gender = selectedItem
        }

        binding.btnRegister.setOnClickListener{
            sendRegister(
                gender,
                binding.tiWeight.text.toString(),
                binding.tiHeight.text.toString(),
                binding.tiAge.text.toString(),
            )
        }

        return binding.root
    }

    fun sendRegister(gender: String, weight: String, height: String, age: String){
        val registerPreference = RegisterPreference(requireContext())
        val data = registerPreference.getUserRegister()
        var genderSend = "";
        if(gender === getString(R.string.man)){
            genderSend = "L"
        }else if(gender === getString(R.string.woman)){
            genderSend = "P"
        }
        registerViewModel.postRegister(
            data.first_name ?: "",
            data.last_name ?: "",
            data.phone_number ?: "",
            data.email ?: "",
            data.password ?: "",
            genderSend,
            weight,
            height,
            age)
    }
}