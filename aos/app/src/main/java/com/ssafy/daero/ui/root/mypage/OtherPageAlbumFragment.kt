package com.ssafy.daero.ui.root.mypage

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentOtherPageAlbumBinding
import com.ssafy.daero.ui.adapter.mypage.OtherPageAlbumAdapter
import com.ssafy.daero.utils.constant.ARTICLE_SEQ

class OtherPageAlbumFragment :
    BaseFragment<FragmentOtherPageAlbumBinding>(R.layout.fragment_other_page_album) {
    private val otherPageViewModel: OtherPageViewModel by viewModels({ requireParentFragment() })
    private lateinit var otherPageAlbumAdapter: OtherPageAlbumAdapter

    override fun init() {
        initAdapter()
        observeData()
        getAlbum()
    }

    private fun initAdapter() {
        otherPageAlbumAdapter = OtherPageAlbumAdapter().apply {
            onItemClickListener = albumItemClickListener
        }
        binding.recyclerOtherPageAlbum.adapter = otherPageAlbumAdapter
    }

    private val albumItemClickListener: (View, Int) -> Unit = { _, tripSeq ->
        findNavController().navigate(R.id.action_otherPageFragment_to_articleFragment, bundleOf(
            ARTICLE_SEQ to tripSeq))
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            (requireParentFragment() as OtherPageFragment).enableSlide()
        }
    }

    private fun observeData() {
        otherPageViewModel.album.observe(viewLifecycleOwner) {
            otherPageAlbumAdapter.submitData(lifecycle, it)
        }
    }

    private fun getAlbum() {
        otherPageViewModel.getAlbum((requireParentFragment() as OtherPageFragment).userSeq)
    }
}