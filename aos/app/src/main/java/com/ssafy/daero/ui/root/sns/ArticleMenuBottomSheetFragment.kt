package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.databinding.FragmentArticleMenuBottomSheetBinding
import com.ssafy.daero.ui.root.trip.TripFollowBottomSheetFragment
import com.ssafy.daero.utils.constant.ARTICLE
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.REPORT_BOTTOM_SHEET


class ArticleMenuBottomSheetFragment(
    private var articleSeq: Int,
    var userSeq: Int,
    var fragmentSeq: Int,
    var listener: ArticleListener,
    var expose: Char = 'y',
) : BottomSheetDialogFragment() {

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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article_menu_bottom_sheet,
            container,
            false
        )
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

    private fun initView() {
        if (userSeq == App.prefs.userSeq) {
            binding.tvArticleMenuTripFollow.visibility = View.GONE
            binding.viewArticleMenuTripFollow.visibility = View.GONE
            binding.tvArticleMenuReport.visibility = View.GONE
            binding.viewArticleMenuReport.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
            if (expose == 'y') {
                binding.tvArticleMenuTripGoPublic.text = "게시글 비공개"
            } else {
                binding.tvArticleMenuTripGoPublic.text = "게시글 공개"
            }
        } else {
            binding.tvArticleMenuModify.visibility = View.GONE
            binding.viewArticleMenuModify.visibility = View.GONE
            binding.tvArticleMenuDelete.visibility = View.GONE
            binding.viewArticleMenuDelete.visibility = View.GONE
            binding.tvArticleMenuTripGoPublic.visibility = View.GONE
            binding.viewArticleMenuTripGoPublic.visibility = View.GONE
        }
    }

    private fun setOnClickListeners() {
        binding.tvArticleMenuTripFollow.setOnClickListener {
            // 따라가기

            // 기존 여행하고 있는 경우
            if (App.prefs.curPlaceSeq > 0) {
                TripFollowBottomSheetFragment(fragmentSeq, articleSeq).show(parentFragmentManager, "TRIP_FOLLOW_BOTTOM_SHEET")
            }
            // 기존 여행 없으면 바로 따라가기
            else {
                when (fragmentSeq) {
                    1 -> findNavController().navigate(
                        R.id.action_rootFragment_to_tripFollowSelectFragment,
                        bundleOf(ARTICLE_SEQ to articleSeq)
                    )
                    2 -> findNavController().navigate(
                        R.id.action_articleFragment_to_tripFollowSelectFragment,
                        bundleOf(ARTICLE_SEQ to articleSeq)
                    )
                }
            }
            dismiss()
        }
        binding.tvArticleMenuShare.setOnClickListener {
            //todo: 공유하기
        }
        binding.tvArticleMenuModify.setOnClickListener {
            //수정하기
            App.isEdit = true
            findNavController().navigate(
                R.id.action_articleFragment_to_articleEditDayFragment,
                bundleOf(ARTICLE_SEQ to articleSeq)
            )
            dismiss()
        }
        binding.tvArticleMenuDelete.setOnClickListener {
            //삭제하기
            listener.articleDelete(articleSeq)
            dismiss()
        }
        binding.tvArticleMenuReport.setOnClickListener {
            //todo: 신고하기, album_seq
            dismiss()
            ReportBottomSheetFragment(ARTICLE, articleSeq).show(
                parentFragmentManager,
                REPORT_BOTTOM_SHEET
            )
        }
        binding.tvArticleMenuBlock.setOnClickListener {
            //차단하기
            listener.blockArticle(userSeq)
            dismiss()
        }
        binding.tvArticleMenuTripGoPublic.setOnClickListener {
            //공개,비공개
            if (expose == 'y') {
                //비공개하기
                listener.articleClose(articleSeq)
            } else {
                //공개하기
                listener.articleOpen(articleSeq)
            }
            dismiss()
        }
        binding.tvArticleMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}