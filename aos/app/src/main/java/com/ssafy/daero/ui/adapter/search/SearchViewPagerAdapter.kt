package com.ssafy.daero.ui.adapter.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.daero.ui.root.search.SearchArticleFragment
import com.ssafy.daero.ui.root.search.SearchUsernameFragment

class SearchViewPagerAdapter(fragment: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return SearchUsernameFragment()
            1 -> return SearchArticleFragment()
            else -> return SearchUsernameFragment()
        }
    }

    companion object{
        private const val NUM_TABS = 2
    }
}