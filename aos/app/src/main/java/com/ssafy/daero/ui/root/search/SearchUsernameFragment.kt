package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchUsernameBinding
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.pagingUser

class SearchUsernameFragment : BaseFragment<FragmentSearchUsernameBinding>(R.layout.fragment_search_username){
    private val searchViewModel : SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var searchUserNameAdapter: SearchUserNameAdapter

    override fun init() {
        initAdapter()
        observeData()
    }

    private fun initAdapter(){
        searchUserNameAdapter = SearchUserNameAdapter().apply {
            onItemClickListener = searchUserItemClickListener
        }

        binding.recyclerSearchUserName.adapter = searchUserNameAdapter
    }

    private fun observeData(){
        searchViewModel.resultUserSearch.observe(viewLifecycleOwner){
            Log.d("TAG", "observeData: 여기")
//            TODO: 유저 검색 API 완성되면 살리기
//            searchUserNameAdapter.submitData(lifecycle, it)
            searchUserNameAdapter.submitData(lifecycle, pagingUser)
        }

        searchViewModel.responseState_userName.observe(viewLifecycleOwner){ state ->
            when(state){
                FAIL -> binding.textSearchUserNoData.visibility = View.VISIBLE
            }
        }
    }

    private val searchUserItemClickListener: (
        View, Int) -> Unit = { _, userSeq ->
    }
}