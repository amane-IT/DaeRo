package com.ssafy.daero.ui.root

import androidx.fragment.app.Fragment
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentRootBinding
import com.ssafy.daero.ui.root.collection.CollectionFragment
import com.ssafy.daero.ui.root.mypage.MyPageFragment
import com.ssafy.daero.ui.root.search.SearchFragment
import com.ssafy.daero.ui.root.sns.HomeFragment
import com.ssafy.daero.ui.root.trip.TripFragment
import com.ssafy.daero.utils.constant.FragmentType

class RootFragment : BaseFragment<FragmentRootBinding>(R.layout.fragment_root) {
    override fun init() {
        setOnClickListeners()

        changeFragment(curFragmentType)
    }

    private fun setOnClickListeners() {
        binding.bottomnavigationRoot.setOnItemSelectedListener {
            val fragmentType = getFragmentType(it.itemId)
            changeFragment(fragmentType)
            true
        }
    }

    private fun changeFragment(fragmentType: FragmentType) {
        val transaction = childFragmentManager.beginTransaction()

        var targetFragment = childFragmentManager.findFragmentByTag(fragmentType.tag)

        if (targetFragment == null) {
            targetFragment = getFragment(fragmentType)
            transaction.add(R.id.fragmentcontainer_root, targetFragment, fragmentType.tag)
        }

        transaction.show(targetFragment)

        FragmentType.values()
            .filterNot { it == fragmentType }
            .forEach { type ->
                childFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }

        transaction.commitAllowingStateLoss()
    }

    private fun getFragmentType(menuItemId: Int): FragmentType {
        return when (menuItemId) {
            R.id.HomeFragment -> FragmentType.HomeFragment
            R.id.SearchFragment -> FragmentType.SearchFragment
            R.id.TripFragment -> FragmentType.TripFragment
            R.id.CollectionFragment -> FragmentType.CollectionFragment
            R.id.MyPageFragment -> FragmentType.MyPageFragment
            else -> throw IllegalArgumentException("not found menu item id")
        }
    }

    private fun getFragment(fragmentType: FragmentType): Fragment {
        when (fragmentType) {
            FragmentType.HomeFragment -> {
                return HomeFragment()
            }
            FragmentType.SearchFragment -> {
                return SearchFragment()
            }
            FragmentType.TripFragment -> {
                return TripFragment()
            }
            FragmentType.CollectionFragment -> {
                return CollectionFragment()
            }
            FragmentType.MyPageFragment -> {
                return MyPageFragment()
            }
            else -> {
                return HomeFragment()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveCurrentFragment()
    }

    private fun saveCurrentFragment() {
        childFragmentManager.fragments.forEach { fragment ->
            if(fragment.isVisible) {
                FragmentType.values().forEach { fragmentType ->
                    if(fragmentType.tag == fragment.tag!!) {
                        curFragmentType = fragmentType
                    }
                }
            }
        }
    }

    companion object {
        var curFragmentType = FragmentType.HomeFragment
    }
}