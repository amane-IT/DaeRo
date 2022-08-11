package com.ssafy.daero.ui.root

import androidx.fragment.app.Fragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentRootBinding
import com.ssafy.daero.ui.root.collection.CollectionFragment
import com.ssafy.daero.ui.root.mypage.MyPageFragment
import com.ssafy.daero.ui.root.search.SearchFragment
import com.ssafy.daero.ui.root.sns.HomeFragment
import com.ssafy.daero.ui.root.trip.*
import com.ssafy.daero.utils.constant.*

class RootFragment : BaseFragment<FragmentRootBinding>(R.layout.fragment_root) {
    override fun init() {
        initFragment()
        checkTripStart()
        checkTripStamp()
        setOnClickListeners()
    }

    private fun initFragment() {
        binding.bottomnavigationRoot.selectedItemId = selectPosition
        val fragmentType = getFragmentType(binding.bottomnavigationRoot.selectedItemId)
        changeFragment(fragmentType)
    }

    // 여행 상세 페이지에서 여행 시작 버튼 눌렀는지 여부
    private fun checkTripStart() {
        // 여행 시작 버튼 누름
        if (App.prefs.isTripStart) {
            changeTripState(TRIP_ING)
            App.prefs.isTripStart = false
        }
    }

    private fun checkTripStamp() {
        // 트립스탬프 찍음
        if (App.prefs.isTripStampComplete) {
            changeTripState(TRIP_COMPLETE)
            App.prefs.isTripStampComplete = false
        }
    }

    private fun setOnClickListeners() {
        binding.bottomnavigationRoot.setOnItemSelectedListener {
            val fragmentType = getFragmentType(it.itemId)
            changeFragment(fragmentType)
            true
        }
    }

    /**
     * 다음 여행 상태를 매개변수로 전달
     * 각 Fragment 에서 함수 사용방법은 아래와 같음
     * (requireParentFragment() as RootFragment).changeTripState(다음 여행 상태)  // ex) TRIP_ING
     *
     */
    fun changeTripState(nextTripState: Int) {
        val curTag = when (App.prefs.tripState) {
            TRIP_BEFORE -> TRIP_FRAGMENT
            TRIP_ING -> TRAVELING_FRAGMENT
            TRIP_VERIFICATION -> TRIP_VERIFICATION_FRAGMENT
            TRIP_COMPLETE -> {
                if (App.prefs.isFollow) {
                    TRIP_FOLLOW_FRAGMENT
                } else {
                    TRIP_NEXT_FRAGMENT
                }
            }
            else -> TRIP_FRAGMENT
        }

        val fragmentType = when (nextTripState) {
            TRIP_BEFORE -> FragmentType.TripFragment
            TRIP_ING -> FragmentType.TravelingFragment
            TRIP_VERIFICATION -> FragmentType.TripVerificationFragment
            TRIP_COMPLETE -> {
                if (App.prefs.isFollow) {
                    FragmentType.TripFollowFragment
                } else {
                    FragmentType.TripNextFragment
                }
            }
            else -> FragmentType.TripFragment
        }

        val transaction = childFragmentManager.beginTransaction()
        var targetFragment = childFragmentManager.findFragmentByTag(curTag)
        targetFragment?.let {
            transaction.remove(targetFragment)
        }
        transaction.commitAllowingStateLoss()

        App.prefs.tripState = nextTripState
        changeFragment(fragmentType)
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
            R.id.TripFragment -> {
                when (App.prefs.tripState) {
                    TRIP_BEFORE -> FragmentType.TripFragment
                    TRIP_ING -> FragmentType.TravelingFragment
                    TRIP_VERIFICATION -> FragmentType.TripVerificationFragment
                    TRIP_COMPLETE -> {
                        if (App.prefs.isFollow) {
                            FragmentType.TripFollowFragment
                        } else {
                            FragmentType.TripNextFragment
                        }
                    }
                    else -> FragmentType.TripFragment
                }
            }
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
            FragmentType.TravelingFragment -> {
                return TravelingFragment()
            }
            FragmentType.TripVerificationFragment -> {
                return TripVerificationFragment()
            }
            FragmentType.TripNextFragment -> {
                return TripNextFragment()
            }
            FragmentType.TripFollowFragment -> {
                return TripFollowFragment()
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
        selectPosition = binding.bottomnavigationRoot.selectedItemId
    }

    companion object {
        var selectPosition = R.id.TripFragment
    }
}