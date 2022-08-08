package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchUsernameBinding
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.constant.USER_SEQ
import com.ssafy.daero.utils.pagingUser
import com.ssafy.daero.utils.view.toast

class SearchUsernameFragment : BaseFragment<FragmentSearchUsernameBinding>(R.layout.fragment_search_username){
    private val searchViewModel : SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var searchUserNameAdapter: SearchUserNameAdapter

    override fun init() {
        initAdapter()
        observeData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            if(App.keyword != null)
                binding.recyclerSearchUserName.visibility = View.VISIBLE
            else
                binding.recyclerSearchUserName.visibility = View.INVISIBLE
        }
    }

    private fun initAdapter(){
        binding.recyclerSearchUserName.visibility = View.VISIBLE
        searchUserNameAdapter = SearchUserNameAdapter().apply {
            onItemClickListener = searchUserItemClickListener
        }

        binding.recyclerSearchUserName.adapter = searchUserNameAdapter
    }

    private fun observeData(){
        searchViewModel.resultUserSearch.observe(viewLifecycleOwner){
            binding.recyclerSearchUserName.visibility = View.VISIBLE
            searchUserNameAdapter.submitData(lifecycle, it)
        }

        searchViewModel.responseState_userName.observe(viewLifecycleOwner){ state ->
            when(state){
                FAIL -> {
                    toast("유저 목록을 불러오는데 실패했습니다.")
                    searchViewModel.responseState_userName.value = DEFAULT
                    binding.textSearchUserNoData.visibility = View.VISIBLE
                }
            }
        }
    }

    private val searchUserItemClickListener: (
        View, Int) -> Unit = { _, userSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_otherPageFragment,
            bundleOf(USER_SEQ to userSeq)
        )
    }
}