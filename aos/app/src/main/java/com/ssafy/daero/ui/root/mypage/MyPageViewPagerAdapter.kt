package com.ssafy.daero.ui.root.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPageViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return MyPageAlbumFragment()
            1 -> return MyPageMapFragment()
            else -> return MyPageAlbumFragment()
        }
    }

    companion object {
        private const val NUM_TABS = 2
    }
}