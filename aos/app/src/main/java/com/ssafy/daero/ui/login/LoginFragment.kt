package com.ssafy.daero.ui.login

import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login){
    override fun init() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonLoginEmailLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailLoginFragment)
        }
    }
}