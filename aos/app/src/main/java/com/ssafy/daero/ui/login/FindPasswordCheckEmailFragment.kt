package com.ssafy.daero.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ssafy.daero.databinding.DlgFindPasswordBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindPasswordCheckEmailFragment(val confirm : () -> Unit) : DialogFragment() {

    private val findPasswordCheckEmailViewModel : FindPasswordCheckEmailViewModel by viewModels()
    private var _binding: DlgFindPasswordBinding? = null
    private val binding get() = _binding!!
    private var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgFindPasswordBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners() {
        binding.buttonDlgFindPasswordEmail.setOnClickListener {
            if (arguments != null)
            {
                email = arguments!!.getString("email").toString()
            }
            findPasswordCheckEmailViewModel.emailCheck(email)
        }
        binding.buttonDlgFindPasswordCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        findPasswordCheckEmailViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindPasswordLoading.isVisible = it
        }
        findPasswordCheckEmailViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    confirm()
                    dismiss()
                }
                FAIL -> {
                    dismiss()
                }
            }
        }
    }

}