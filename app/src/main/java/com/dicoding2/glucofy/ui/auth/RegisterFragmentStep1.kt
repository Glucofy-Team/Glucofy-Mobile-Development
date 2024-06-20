package com.dicoding2.glucofy.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.RegisterPreference
import com.dicoding2.glucofy.data.local.entity.RegisterEntity
import com.dicoding2.glucofy.databinding.FragmentRegisterStep1Binding


class RegisterFragmentStep1 : Fragment() {
    private var _binding: FragmentRegisterStep1Binding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterStep1Binding.inflate(inflater, container, false)

        viewPager = requireActivity().findViewById(R.id.view_pager)

        binding.btnNext.setOnClickListener {
            setUserRegisterData(
                binding.tiFirstName.text.toString(),
                binding.tiLastName.text.toString(),
                binding.tiPhoneNumber.text.toString(),
                binding.tiEmail.text.toString(),
                binding.tiPassword.text.toString(),
            )
            viewPager.setCurrentItem(1, true)
        }

        binding.tvLogin.setOnClickListener {
            requireActivity().finish()
        }

        binding.tiEmail.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiPassword.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiLastName.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiFirstName.addTextChangedListener {
            updateSubmitButtonState()
        }
        binding.tiPhoneNumber.addTextChangedListener {
            updateSubmitButtonState()
        }

        updateSubmitButtonState()

        return binding.root
    }

    private fun setUserRegisterData(firstName: String, lastName: String, phone: String, email: String, password: String){
        val registerPreference = RegisterPreference(requireContext())
        var registerModel = RegisterEntity(
            firstName,
            lastName,
            phone,
            email,
            password,
        )
        registerPreference.setUserRegister(registerModel)
    }
    private fun updateSubmitButtonState() {
        val isAllFieldsFilled = binding.tiEmail.text?.isNotEmpty() == true &&
                binding.tiPassword.text?.isNotEmpty() == true &&
                binding.tiLastName.text?.isNotEmpty() == true &&
                binding.tiFirstName.text?.isNotEmpty() == true &&
                binding.tiPhoneNumber.text?.isNotEmpty() == true

        binding.btnNext.isEnabled = isAllFieldsFilled
    }
}