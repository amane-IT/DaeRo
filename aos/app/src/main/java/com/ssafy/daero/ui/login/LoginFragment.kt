package com.ssafy.daero.ui.login

import android.graphics.Paint
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentLoginBinding
import com.ssafy.daero.utils.view.setStatusBarOrigin
import com.ssafy.daero.utils.view.setStatusBarTransparent

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override fun init() {
        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.textLoginSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
    }

    private fun setOnClickListeners() {
        binding.buttonLoginEmailLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailLoginFragment)
        }
        binding.textLoginSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupEmailFragment)
        }
    }

    private fun setStatusBarTransParent() {
        binding.constraintLoginInnerContainer.setStatusBarTransparent(requireActivity())
    }

    private fun setStatusBarOrigin() {
        requireActivity().setStatusBarOrigin()
    }

    override fun onStart() {
        super.onStart()
        setStatusBarTransParent()
    }

    override fun onStop() {
        super.onStop()
        setStatusBarOrigin()
    }
}