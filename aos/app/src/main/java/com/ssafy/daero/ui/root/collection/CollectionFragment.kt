package com.ssafy.daero.ui.root.collection

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentCollectionBinding
import com.ssafy.daero.ui.adapter.sns.CollectionAdapter
import com.ssafy.daero.utils.collections
import com.ssafy.daero.utils.searchedArticleContentMore

class CollectionFragment : BaseFragment<FragmentCollectionBinding>(R.layout.fragment_collection){
    private val TAG = "CollectionFragment_DaeRo"
    private val collectionViewModel : CollectionViewModel by viewModels()
    private lateinit var collectionAdapter: CollectionAdapter

    override fun init() {
        initData()
        initAdapter()
        observeData()
    }

    private fun initData(){
        collectionViewModel.getCollections()
    }

    private fun initAdapter(){
        collectionAdapter = CollectionAdapter().apply {
            onItemClickListener = collectionItemClickListener
        }

        binding.recyclerCollection.adapter = collectionAdapter
    }

    private fun observeData(){
        collectionViewModel.collections.observe(viewLifecycleOwner){
            Log.d(TAG, "observeData: $it")
            // TODO: 컬렉션 API 완성하면 살리기
//            collectionAdapter.submitData(lifecycle, it)
            collectionAdapter.submitData(lifecycle, collections)
        }
    }

    // TODO: Click 이벤트 구현하기
    private val collectionItemClickListener: (
        View, Int) -> Unit = { _, articleSeq ->
    }

}