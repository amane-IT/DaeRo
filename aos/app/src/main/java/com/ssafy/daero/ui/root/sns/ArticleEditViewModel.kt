package com.ssafy.daero.ui.root.sns

import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleEditRequestDto
import com.ssafy.daero.data.dto.article.ArticleResponseDto
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.data.repository.SnsRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleEditViewModel : BaseViewModel() {
    private val snsRepository = SnsRepository.get()

    val articleState = MutableLiveData<Int>()
    val editState = MutableLiveData<Int>()

    var articleEditRequest: ArticleEditRequestDto? = null
    var tripStamps: List<List<TripStamp>> = emptyList()
    var dates: List<String> = emptyList()
    var expenses = mutableListOf<Expense>()
    var dayIndex = 0
    var editArticleSeq = 0

    var showProgress = MutableLiveData<Boolean>()

    fun getArticle(articleSeq: Int) {
        addDisposable(
            snsRepository.article(articleSeq)
                .subscribe({
                    makeArticleEditRequest(it.body()!!)
                    articleState.postValue(SUCCESS)
                }, { throwable ->
                    articleState.postValue(FAIL)
                })
        )
    }

    fun editArticle() {
        showProgress.postValue(true)
        articleEditRequest?.tripExpenses = makeExpenseString()

        addDisposable(
            snsRepository.editArticle(editArticleSeq, articleEditRequest!!)
                .subscribe({
                    editState.postValue(SUCCESS)
                    showProgress.postValue(false)
                }, { throwable ->
                    editState.postValue(FAIL)
                    showProgress.postValue(false)
                })
        )
    }

    private fun makeArticleEditRequest(article: ArticleResponseDto) {
        articleEditRequest = ArticleEditRequestDto(
            title = article.title,
            tripComment = article.trip_comment,
            tripExpenses = makeExpenseString(),
            rating = article.rating,
            expose = article.expose,
            records = article.records.map {
                it.day_comment
            }.toMutableList()
        )
        expenses = article.trip_expenses.toMutableList()
        tripStamps = article.records.map {
            it.trip_stamps
        }
        dates = article.records.map {
            it.datetime
        }
    }

    fun initArticleEditRequest() {
        articleEditRequest = null
        tripStamps = emptyList()
        dates = emptyList()
        expenses = mutableListOf()
        dayIndex = 0
        editArticleSeq = 0
    }

    private fun makeExpenseString(): String {
        if (expenses.isEmpty()) return ""

        var res = "["
        expenses.forEach {
            res += "$it,"
        }
        res = res.substring(0, res.length - 1)
        res += "]"
        return res
    }
}