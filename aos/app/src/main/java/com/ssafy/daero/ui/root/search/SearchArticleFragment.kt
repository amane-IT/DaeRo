package com.ssafy.daero.ui.root.search

import android.graphics.Paint
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchArticleBinding
import com.ssafy.daero.ui.adapter.search.SearchArticleAdapter
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SearchArticleFragment :
    BaseFragment<FragmentSearchArticleBinding>(R.layout.fragment_search_article) {
    private val searchViewModel: SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var searchArticleContentAdapter: SearchArticleAdapter
    private lateinit var searchArticlePlaceAdapter: SearchArticleAdapter

    override fun init() {
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initView() {
        binding.textSearchArticlePlaceMoreData.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textSearchArticleContentMoreData.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initAdapter() {
        // 내용 검색
        searchArticleContentAdapter = SearchArticleAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }
        binding.recyclerSearchArticleContent.adapter = searchArticleContentAdapter

        // 여행지 검색
        searchArticlePlaceAdapter = SearchArticleAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }
        binding.recyclerSearchArticlePlace.adapter = searchArticlePlaceAdapter
    }

    private fun observeData() {
        searchViewModel.resultArticleSearch.observe(viewLifecycleOwner) {
            binding.scrollSearchArticle.visibility = View.VISIBLE
            if (it.place.isEmpty()) {
                binding.recyclerSearchArticlePlace.visibility = View.GONE
                binding.textSearchArticlePlace.visibility = View.GONE
                binding.textSearchArticlePlaceMoreData.visibility = View.GONE
            } else {
                binding.textSearchArticleNoContent.visibility = View.GONE
                binding.recyclerSearchArticlePlace.visibility = View.VISIBLE
                binding.textSearchArticlePlace.visibility = View.VISIBLE
                binding.textSearchArticlePlaceMoreData.visibility = View.VISIBLE
                searchArticlePlaceAdapter.apply {
                    resultList = it.place
                    notifyDataSetChanged()
                }
            }

            if (it.content.isEmpty()) {
                binding.recyclerSearchArticleContent.visibility = View.GONE
                binding.textSearchArticleContent.visibility = View.GONE
                binding.textSearchArticleContentMoreData.visibility = View.GONE
            } else {
                binding.textSearchArticleNoContent.visibility = View.GONE
                binding.recyclerSearchArticleContent.visibility = View.VISIBLE
                binding.textSearchArticleContent.visibility = View.VISIBLE
                binding.textSearchArticleContentMoreData.visibility = View.VISIBLE
                searchArticleContentAdapter.apply {
                    resultList = it.content
                    notifyDataSetChanged()
                }
            }
        }

        searchViewModel.responseState_articles.observe(viewLifecycleOwner) { state ->
            when (state) {
                FAIL -> {
                    binding.scrollSearchArticle.visibility = View.GONE
                    binding.textSearchArticleNoContent.visibility = View.VISIBLE
                    searchViewModel.responseState_articles.value = DEFAULT
                }
                SUCCESS -> {
                    binding.scrollSearchArticle.visibility = View.VISIBLE
                    binding.textSearchArticleNoContent.visibility = View.GONE
                    searchViewModel.responseState_articles.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textSearchArticleContentMoreData.setOnClickListener {
                findNavController().navigate(R.id.action_rootFragment_to_searchContentMoreFragment)
            }

            textSearchArticlePlaceMoreData.setOnClickListener {
                findNavController().navigate(R.id.action_rootFragment_to_searchPlaceNameMoreFragment)
            }
        }
    }

    private val searchArticleItemClickListener: (
        View, Int
    ) -> Unit = { _, article_seq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_articleFragment,
            bundleOf(ARTICLE_SEQ to article_seq)
        )
    }
}