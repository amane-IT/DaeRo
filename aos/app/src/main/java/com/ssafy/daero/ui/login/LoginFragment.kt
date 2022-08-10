package com.ssafy.daero.ui.login

import android.graphics.Paint
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentLoginBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.TRIP_BEFORE
import com.ssafy.daero.utils.file.deleteCache
import com.ssafy.daero.utils.view.setStatusBarOrigin
import com.ssafy.daero.utils.view.setStatusBarTransparent

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun init() {
        initViews()
        setOnClickListeners()
        observeData()
        jwtLogin()
    }

    private fun showPermissionGuide() {
        PermissionDialogFragment().show(childFragmentManager, "dlg_permission")
    }

    private fun initViews() {
        binding.textLoginSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG;

        if (!App.prefs.isPermissionGuideCheck) {
            showPermissionGuide()
        }
    }

    private fun setOnClickListeners() {
        binding.buttonLoginEmailLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailLoginFragment)
        }
        binding.textLoginSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupEmailFragment)
        }
    }

    private fun jwtLogin() {
        App.prefs.jwt?.let {
            loginViewModel.jwtLogin()
        }
    }

    private fun observeData() {
        loginViewModel.responseState.observe(viewLifecycleOwner) { response ->
            when (response) {
                200 -> {
                    findNavController().navigate(R.id.action_loginFragment_to_rootFragment)
                    loginViewModel.responseState.value = DEFAULT
                }
                202 -> {
                    findNavController().navigate(R.id.action_loginFragment_to_tripPreferenceFragment)
                    loginViewModel.responseState.value = DEFAULT
                }
                403 -> {
                    // todo: 정지된 유저 다이얼로그 띄우기
                    loginViewModel.responseState.value = DEFAULT
                }

                FAIL -> {
                    Log.d("code", "observeData: XXX")
                    // jwt 토큰, user_seq 삭제
                    deleteAllInformation()
                    loginViewModel.responseState.value = DEFAULT
                }
            }
        }
        loginViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarLoginLoading.isVisible = it
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

    private fun deleteAllInformation() {
        // 캐시 디렉토리 전체 삭제
        deleteCache(requireContext())

        // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
        loginViewModel.deleteAllTripRecord()

        // Prefs 초기화
        App.prefs.initUser()
        App.prefs.initTrip()
        App.prefs.tripState = TRIP_BEFORE
    }
}