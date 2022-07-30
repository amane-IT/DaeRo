package com.ssafy.daero.ui.root.mypage

import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentMyPageAlbumBinding
import com.ssafy.daero.ui.adapter.mypage.MyPageAlbumAdapter

class MyPageAlbumFragment :
    BaseFragment<FragmentMyPageAlbumBinding>(R.layout.fragment_my_page_album) {
    private val myPageViewModel: MyPageViewModel by viewModels({ requireParentFragment() })
    private lateinit var myPageAlbumAdapter: MyPageAlbumAdapter

    override fun init() {
        initAdapter()
        observeData()
        getMyAlbum()
    }

    private fun getMyAlbum() {
        myPageViewModel.getMyAlbum()
    }

    private fun initAdapter() {
        myPageAlbumAdapter = MyPageAlbumAdapter().apply {
            onItemClickListener = albumItemClickListener
        }
        binding.recyclerMyPageAlbum.adapter = myPageAlbumAdapter
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            (requireParentFragment() as MyPageFragment).enableSlide()
        }
    }

    private fun observeData() {
        myPageViewModel.myAlbum.observe(viewLifecycleOwner) {
            myPageAlbumAdapter.submitData(lifecycle, it)
        }
    }

    private val albumItemClickListener: (View, Int) -> Unit = { _, tripSeq ->
        // todo: tripSeq 이용해서 앨범 상세 페이지로 이동
    }
}