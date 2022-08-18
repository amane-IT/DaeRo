package com.ssafy.daero.ui.root.search

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchUsernameBinding
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.USER_SEQ
import com.ssafy.daero.utils.view.toast
import kotlinx.coroutines.launch

class SearchUsernameFragment :
    BaseFragment<FragmentSearchUsernameBinding>(R.layout.fragment_search_username) {
    private val searchViewModel: SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var searchUserNameAdapter: SearchUserNameAdapter

    override fun init() {
        initAdapter()
        observeData()
        setOtherListeners()
    }

    private fun setOtherListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchUserNameAdapter.loadStateFlow.collect {
                if (it.append is LoadState.NotLoading && it.append.endOfPaginationReached) {
                    if (searchUserNameAdapter.itemCount < 1) {
                        binding.textSearchUserNameNoContent.visibility = View.VISIBLE
                        binding.recyclerSearchUserName.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerSearchUserName.visibility = View.VISIBLE
        searchUserNameAdapter = SearchUserNameAdapter().apply {
            onItemClickListener = searchUserItemClickListener
        }

        binding.recyclerSearchUserName.adapter = searchUserNameAdapter
    }

    private fun observeData() {
        searchViewModel.resultUserSearch.observe(viewLifecycleOwner) {
            binding.recyclerSearchUserName.visibility = View.VISIBLE
            binding.textSearchUserNameNoContent.visibility = View.GONE
            searchUserNameAdapter.submitData(lifecycle, it)
        }

        searchViewModel.responseState_userName.observe(viewLifecycleOwner) { state ->
            when (state) {
                FAIL -> {
                    toast("유저 목록을 불러오는데 실패했습니다.")
                    searchViewModel.responseState_userName.value = DEFAULT
                    binding.recyclerSearchUserName.visibility = View.GONE
                    binding.textSearchUserNameNoContent.visibility = View.VISIBLE
                }
            }
        }
    }

    private val searchUserItemClickListener: (
        View, Int
    ) -> Unit = { _, userSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_otherPageFragment,
            bundleOf(USER_SEQ to userSeq)
        )
    }
}