package com.ssafy.daero.ui.login

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ssafy.daero.R
import com.ssafy.daero.databinding.DlgFindPasswordBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class FindPasswordCheckEmailDialogFragment(val confirm : (reset_seq: Int) -> Unit) : DialogFragment() {

    private val findPasswordConfirmViewModel : FindPasswordConfirmViewModel by viewModels({ requireParentFragment() })
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
        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        Glide.with(this).load(R.drawable.find_pw_mail)
            .fitCenter()
            .override(Target.SIZE_ORIGINAL)
            .into(binding.imageDlgFindPasswordEmail)
    }

    private fun setOnClickListeners() {
        binding.buttonDlgFindPasswordEmail.setOnClickListener {
            if (arguments != null)
            {
                email = arguments!!.getString("email").toString()
                findPasswordConfirmViewModel.emailCheck(email)
            }
        }
        binding.buttonDlgFindPasswordCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        findPasswordConfirmViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindPasswordLoading.isVisible = it
        }
        findPasswordConfirmViewModel.checkEmailResponseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    confirm(findPasswordConfirmViewModel.password_reset_seq)
                    dismiss()
                }
                FAIL -> {
                    dismiss()
                }
            }
        }
    }

}