package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.databinding.FragmentArticleMenuBottomSheetBinding
import com.ssafy.daero.utils.constant.ARTICLE
import com.ssafy.daero.utils.constant.COMMENT
import com.ssafy.daero.utils.constant.REPORT_BOTTOM_SHEET


class ArticleMenuBottomSheetFragment(private val articleSeq: Int, val userSeq: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentArticleMenuBottomSheetBinding

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ArticleMenuBottomSheetFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_menu_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        init()
    }

    fun init() {
        initView()
        setOnClickListeners()
    }

    private fun initView(){
        if(userSeq== App.prefs.userSeq){
            binding.tvArticleMenuTripFollow.visibility = View.GONE
            binding.viewArticleMenuTripFollow.visibility = View.GONE
            binding.tvArticleMenuHide.visibility = View.GONE
            binding.viewArticleMenuHide.visibility = View.GONE
            binding.tvArticleMenuReport.visibility = View.GONE
            binding.viewArticleMenuReport.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
        }else{
            binding.tvArticleMenuModify.visibility = View.GONE
            binding.viewArticleMenuModify.visibility = View.GONE
            binding.tvArticleMenuDelete.visibility = View.GONE
            binding.viewArticleMenuDelete.visibility = View.GONE
        }
    }

    private fun setOnClickListeners() {
        binding.tvArticleMenuTripFollow.setOnClickListener {
            //todo: 따라가기
            findNavController().navigate(
                R.id.action_articleFragment_to_tripFollowFragment
            )
        }
        binding.tvArticleMenuShare.setOnClickListener {
            //todo: 공유하기
        }
        binding.tvArticleMenuModify.setOnClickListener {
            //todo: 수정하기
            findNavController().navigate(
                R.id.action_articleFragment_to_articleWriteDayFragment
            )
        }
        binding.tvArticleMenuDelete.setOnClickListener {
            //todo: 삭제하기
        }
        binding.tvArticleMenuReport.setOnClickListener {
            //todo: 신고하기, album_seq
            dismiss()
            ReportBottomSheetFragment(ARTICLE, articleSeq).show(parentFragmentManager, REPORT_BOTTOM_SHEET)
        }
        binding.tvArticleMenuBlock.setOnClickListener {
            //todo: 차단하기
        }
        binding.tvArticleMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}