package com.ssafy.daero.ui.root.sns

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentArticleEditThumbnailBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ArticleEditThumbnailFragment :
    BaseFragment<FragmentArticleEditThumbnailBinding>(R.layout.fragment_article_edit_thumbnail) {

    private val articleEditViewModel: ArticleEditViewModel by viewModels({ requireParentFragment() })

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun initViews() {
        articleEditViewModel.articleEditRequest?.let {
            binding.editTextArticleEditTitle.setText(it.title)
            binding.editTextArticleEditComment.setText(it.tripComment)
            binding.switchArticleEditExpose.isChecked = it.expose == 'y'
        }
    }

    private fun observeData() {
        articleEditViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarArticleEditLoading.isVisible = it
        }
        articleEditViewModel.editState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    articleEditViewModel.initArticleEditRequest()
                    findNavController().navigate(R.id.action_articleEditThumbnailFragment_to_rootFragment)
                    articleEditViewModel.editState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 수정을 실패했습니다.")
                    articleEditViewModel.editState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageArticleEditBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.buttonArticleEditComplete.setOnClickListener {
            if (binding.editTextArticleEditTitle.text.toString().isBlank()) {
                toast("제목을 입력해주세요.")
                return@setOnClickListener
            }

            if (binding.editTextArticleEditComment.text.toString().isBlank()) {
                toast("내용을 입력해주세요.")
                return@setOnClickListener
            }

            articleEditViewModel.articleEditRequest?.let {
                it.title = binding.editTextArticleEditTitle.text.toString()
                it.tripComment = binding.editTextArticleEditComment.text.toString()
            }

            articleEditViewModel.editArticle()
        }
    }

    private fun setOtherListeners() {
        binding.switchArticleEditExpose.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                articleEditViewModel.articleEditRequest?.expose = 'y'
            } else {
                articleEditViewModel.articleEditRequest?.expose = 'n'
            }
        }
    }
}