package com.ssafy.daero.ui.adapter.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.daero.ui.root.mypage.MyPageAlbumFragment
import com.ssafy.daero.ui.root.mypage.MyPageMapFragment
import com.ssafy.daero.ui.root.mypage.OtherPageAlbumFragment
import com.ssafy.daero.ui.root.mypage.OtherPageMapFragment

class OtherPageViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OtherPageAlbumFragment()
            1 -> OtherPageMapFragment()
            else -> OtherPageAlbumFragment()
        }
    }

    companion object {
        private const val NUM_TABS = 2
    }
}