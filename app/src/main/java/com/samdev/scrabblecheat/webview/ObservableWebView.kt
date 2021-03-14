package com.samdev.scrabblecheat.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class ObservableWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    var scrollChangedCallback: OnScrollChangeListener? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        scrollChangedCallback?.onScrollChanged(l,t, oldl, oldt)
    }

    interface OnScrollChangeListener {
        fun onScrollChanged(currentHorizontalScroll: Int, currentVerticalScroll: Int, oldHorizontalScroll: Int, oldcurrentVerticalScroll: Int)

    }
}