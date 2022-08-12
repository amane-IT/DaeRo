package com.ssafy.daero.application

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.daero.R
import com.ssafy.daero.ui.login.LoginFragment
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.ui.root.sns.ArticleEditDayFragment
import com.ssafy.daero.ui.root.sns.ArticleWriteDayFragment
import com.ssafy.daero.utils.constant.FragmentType
import com.ssafy.daero.utils.view.toast

class MainActivity : AppCompatActivity() {
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(R.color.white))
            it.hide()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RootFragment.selectPosition = R.id.TripFragment
    }

    override fun onBackPressed() {
        val curFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host)!!.childFragmentManager.fragments[0]
        if (curFragment is RootFragment || curFragment is LoginFragment) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - backPressedTime
            backPressedTime = currentTime

            if (elapsedTime in 0..2000) {
                finish()
            } else {
                toast("뒤로가기 버튼을 한 번 더 누르면 종료됩니다.")
            }
        } else if (curFragment is ArticleWriteDayFragment) {
            if (curFragment.articleWriteViewModel.dayIndex > 0) {
                curFragment.articleWriteViewModel.dayIndex -= 1
            } else {
                curFragment.articleWriteViewModel.finishWriteArticle()
                App.prefs.isPosting = false
            }
            super.onBackPressed()
        } else if (curFragment is ArticleEditDayFragment) {
            if (curFragment.articleEditViewModel.dayIndex > 0) {
                curFragment.articleEditViewModel.dayIndex -= 1
            } else {
                curFragment.articleEditViewModel.initArticleEditRequest()
            }
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}