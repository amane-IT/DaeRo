package com.ssafy.daero.ui.root.collection

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentCollectionBinding
import com.ssafy.daero.ui.adapter.sns.CollectionAdapter
import com.ssafy.daero.ui.root.sns.ArticleViewModel
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.USER_SEQ
import kotlinx.coroutines.launch

class CollectionFragment : BaseFragment<FragmentCollectionBinding>(R.layout.fragment_collection){
    private val collectionViewModel : CollectionViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()

    private lateinit var collectionAdapter: CollectionAdapter

    override fun init() {
        initData()
        initAdapter()
        observeData()
        setOnClickListeners()
        setOtherListeners()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            initData()
        }
    }

    private fun initData(){
        collectionViewModel.getCollections()
    }

    private fun initAdapter(){
        collectionAdapter = CollectionAdapter(
            collectionUserClickListener,
            collectionLikeClickListener,
            collectionItemClickListener
        )

        binding.recyclerCollection.adapter = collectionAdapter
    }

    private fun observeData(){
        collectionViewModel.collections.observe(viewLifecycleOwner){
            binding.textCollectionNoContent.visibility = View.GONE
            collectionAdapter.submitData(lifecycle, it)
        }
    }


    private fun setOnClickListeners(){
        binding.apply {
            imageCollectionNotification.setOnClickListener {
                findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
            }
        }
    }

    private fun setOtherListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            collectionAdapter.loadStateFlow.collect {
                if(it.append is LoadState.NotLoading && it.append.endOfPaginationReached) {
                    if(collectionAdapter.itemCount < 1) {
                        binding.textCollectionNoContent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    // 유저 프로필 클릭 이벤트
    private val collectionUserClickListener: (Int) -> Unit = { userSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_otherPageFragment,
            bundleOf(USER_SEQ to userSeq)
        )
    }

    // 좋아요 클릭 이벤트
    private val collectionLikeClickListener: (Int, Int) -> Unit = { articleSeq, position ->
        articleViewModel.likeDelete(App.prefs.userSeq, articleSeq)
        initData()


    }

    // 썸네일 클릭 이벤트
    private val collectionItemClickListener: (
        Int) -> Unit = { articleSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_articleFragment,
            bundleOf(ARTICLE_SEQ to articleSeq)
        )
    }

}