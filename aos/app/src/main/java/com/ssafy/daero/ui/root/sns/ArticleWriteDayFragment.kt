package com.ssafy.daero.ui.root.sns

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.FragmentArticleWriteDayBinding
import com.ssafy.daero.ui.adapter.sns.ArticleWriteTripStampAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import java.text.SimpleDateFormat
import java.util.*

class ArticleWriteDayFragment : BaseFragment<FragmentArticleWriteDayBinding>(R.layout.fragment_article_write_day) {

    private val articleWriteViewModel: ArticleWriteViewModel by viewModels()
    private lateinit var articleWriteTripStampAdapter: ArticleWriteTripStampAdapter
    private var daySeq: Int = 1
    private var dateTimeList = mutableListOf<String>()
    private val tripStampList = mutableListOf<TripStamp>()

    private val tripStampItemClickListener: (View, Int) -> Unit = { _, userSeq ->
        //todo : 트립스탬프 상세보기로 이동, nav_graph에 추가하기
        findNavController().navigate(
            R.id.action_followerFragment_to_myPageFragment,
            bundleOf("UserSeq" to userSeq)
        )
    }

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews(){
        articleWriteViewModel.getTripStamps()
    }

    private fun observeData(){
        articleWriteViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    setBinding()
                    articleWriteViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    articleWriteViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding(){
        var dateFormat = SimpleDateFormat("yyyy.MM.dd(E)")
        val dateTime = Date(articleWriteViewModel.articleTripStampData[0].dateTime)
        val str:String = dateFormat.format(dateTime)
        dateTimeList.add(str)
        if(articleWriteViewModel.articleTripStampData.size > 1){
            for(i in articleWriteViewModel.articleTripStampData){
                val dateTime = Date(i.dateTime)
                if(dateTimeList[dateTimeList.lastIndex] != dateFormat.format(dateTime)){
                    dateTimeList.add(dateFormat.format(dateTime))
                }
            }
        }
        for(i in articleWriteViewModel.articleTripStampData){
            val dateTime = Date(i.dateTime)
            if(dateTimeList[daySeq] == dateFormat.format(dateTime)){
                tripStampList.add(i)
            }
        }
        articleWriteTripStampAdapter = ArticleWriteTripStampAdapter().apply {
            onItemClickListener = tripStampItemClickListener
            articleTripStampData = tripStampList
        }
        binding.recyclerArticleWriteTripStamp.apply {
            adapter = articleWriteTripStampAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        binding.textArticleWriteDate.text = dateTimeList[daySeq]
    }

    private fun setOnClickListeners(){
        binding.buttonArticleWriteNext.setOnClickListener {
            //todo : dateTimeList.size보다 daySeq가 작으면 다시 프래그먼트 호출
            //todo : 만약 같거나 크면 fragment_expense로 이동 -> 데이터 처리 어떻게? -> 코멘트만?
            if(dateTimeList.size>daySeq){
                findNavController().navigate(
                    R.id.action_articleWriteDayFragment_to_articleWriteDayFragment,
                    bundleOf("daySeq" to (daySeq+1))
                )
            }else{

            }
        }

        binding.imgArticleWriteBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}