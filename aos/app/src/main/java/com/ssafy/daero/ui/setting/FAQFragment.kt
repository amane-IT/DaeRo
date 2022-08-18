package com.ssafy.daero.ui.setting

import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentFaqBinding
import com.ssafy.daero.ui.adapter.setting.FAQAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.view.toast

class FAQFragment : BaseFragment<FragmentFaqBinding>(R.layout.fragment_faq) {
    private val faqViewModel : FAQViewModel by viewModels()
    private lateinit var faqAdapter: FAQAdapter

    override fun init() {
        initAdapter()
        setOnClickListeners()
        observeData()
        getFaqs()
    }

    private fun initAdapter() {
        faqAdapter = FAQAdapter()
        binding.recyclerFaq.adapter = faqAdapter
    }

    private fun setOnClickListeners() {
        binding.imgNoticeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData() {
        faqViewModel.faqState.observe(viewLifecycleOwner) {
            when(it) {
                FAIL -> {
                    toast("FAQ 목록을 조회하는데 실패했습니다.")
                    faqViewModel.faqState.value = DEFAULT
                }
            }
        }
        faqViewModel.faqs.observe(viewLifecycleOwner) {
            faqAdapter.apply {
                faqs = it
                notifyDataSetChanged()
            }
        }
    }

    private fun getFaqs() {
        faqViewModel.getFaqs()
    }
}