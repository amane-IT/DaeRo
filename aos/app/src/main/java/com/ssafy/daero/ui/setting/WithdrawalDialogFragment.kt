package com.ssafy.daero.ui.setting

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.databinding.DlgLogoutBinding
import com.ssafy.daero.databinding.DlgWithdrawalBinding

class WithdrawalDialogFragment(val confirm : () -> Unit) : DialogFragment() {

    private var _binding: DlgWithdrawalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgWithdrawalBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonDlgWithdrawalWithdrawal.setOnClickListener { confirm(); dismiss() }
        binding.buttonDlgWithdrawalCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}