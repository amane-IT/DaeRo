package com.ssafy.daero.ui.root.sns

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions

class ArticleMapView : MapView {
    constructor(context: Context):
            super(context)
    constructor(context: Context, naverMapOptions: NaverMapOptions):
            super(context, naverMapOptions)
    constructor(context: Context, attributeSet: AttributeSet):
            super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, int: Int):
            super(context, attributeSet, int)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d("CustomWebView", "touchevent")
        when(event?.action) {
            MotionEvent.ACTION_UP -> {
                Log.d("CustomWebView", "disallow Intercept")
                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d("CustomWebView", "allow Intercept")
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(event)
    }
}