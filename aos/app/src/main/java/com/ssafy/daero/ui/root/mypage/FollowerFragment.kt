package com.ssafy.daero.ui.root.mypage

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentFollowerBinding
import com.ssafy.daero.ui.adapter.mypage.FollowAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.constant.USER_SEQ
import com.ssafy.daero.utils.view.toast

class FollowerFragment : BaseFragment<FragmentFollowerBinding>(R.layout.fragment_follower), FollowListener {

    private val followViewModel: FollowViewModel by viewModels({ requireParentFragment() })
    private lateinit var followAdapter: FollowAdapter

    private val followItemClickListener: (View, Int) -> Unit = { _, userSeq ->
        findNavController().navigate(
            R.id.action_followerFragment_to_otherPageFragment,
            bundleOf(USER_SEQ to userSeq)
        )
    }

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews(){
        followAdapter = FollowAdapter(this@FollowerFragment).apply {
            onItemClickListener = followItemClickListener
        }
        binding.recyclerFollower.apply {
            adapter = followAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        followViewModel.follower(App.prefs.userSeq)
    }

    private fun observeData(){
        followViewModel.follow.observe(viewLifecycleOwner) {
            followAdapter.submitData(lifecycle, it)
        }

    }

    private fun setOnClickListeners(){
        binding.imgFollowerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun follow(userSeq: Int) {
        followViewModel.follow(userSeq)
        observeCheck(true)
    }

    override fun unFollow(userSeq: Int) {
        followViewModel.unFollow(userSeq)
        observeCheck(false)
    }

    private fun observeCheck(chk: Boolean){
        followViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    when(chk){
                        true -> toast("팔로우 했습니다.")
                        false -> toast("팔로우를 해제했습니다.")
                    }
                    followViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    followViewModel.responseState.value = DEFAULT
                }
            }
        }
    }
}