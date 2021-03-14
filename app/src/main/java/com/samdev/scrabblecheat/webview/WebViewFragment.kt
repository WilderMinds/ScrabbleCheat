package com.samdev.scrabblecheat.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samdev.scrabblecheat.App
import com.samdev.scrabblecheat.databinding.FragmentWebviewBinding
import com.samdev.scrabblecheat.home.HomeViewModel
import timber.log.Timber


class WebViewFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentWebviewBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private var mCurrentWebViewScrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(
            inflater,
            container,
            false
        )
        binding.vm = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebViewBehavior()
        initWebView()
        observeSearchWord()
    }


    private fun setupWebViewBehavior() {
        initSheetBehavior()
        initScrollBehavior()
    }


    /**
     * Handle internal scrolling of the webview in the bottomsheet
     */
    private fun initSheetBehavior() {
        (dialog as BottomSheetDialog).setOnShowListener {
            val bsDialog = it as BottomSheetDialog
            val bottomSheet =
                bsDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

            bottomSheet?.let { sheet ->
                BottomSheetBehavior.from(sheet).let { behavior ->
                    behavior.addBottomSheetCallback(object :
                        BottomSheetBehavior.BottomSheetCallback() {
                        override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            // if 'mCurrentWebViewScrollY' is greater than '0', then it means that there is more
                            // content at the top, so webview can still scroll up.
                            // In that case, keep the bottomSheet expanded till we get to the top of the webview.
                            //
                            // But when the 'mCurrentWebViewScrollY' = 0, the it means we are at the top of the
                            // webview content and cannot scroll anymore.
                            // Hence, we allow the bottomsheet to do its thing and collapse
                            if (newState == BottomSheetBehavior.STATE_DRAGGING && mCurrentWebViewScrollY > 0) {
                                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                        }
                    })
                }
            }
        }
    }


    /**
     * Track the current scroll position of the webview so we can prevent
     * interference of the bottomsheet gestures
     */
    private fun initScrollBehavior() {
        binding.webView.scrollChangedCallback = object : ObservableWebView.OnScrollChangeListener {
            override fun onScrollChanged(
                currentHorizontalScroll: Int,
                currentVerticalScroll: Int,
                oldHorizontalScroll: Int,
                oldcurrentVerticalScroll: Int
            ) {
                mCurrentWebViewScrollY = currentVerticalScroll
                Timber.e("mCurrentWebViewScrollY => $mCurrentWebViewScrollY")
            }
        }
    }


    /**
     * Initialize the webview
     */
    private fun initWebView() {
        // init client
        binding.webView.webChromeClient = WebChromeClient()

        // init web settings
        binding.webView.settings.apply {
            javaScriptEnabled = true
            displayZoomControls = true
            setSupportMultipleWindows(true)
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                val uri = Uri.parse(url)
                return false
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val uri = request.url
                return false
            }

            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url)
                Timber.e("url finished => $url")
                hideProgress()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Timber.e("page started => $url")
            }
        }
    }


    /**
     * Indicate that the loading process is complete
     */
    private fun hideProgress() {
        binding.progress.isIndeterminate = false
    }


    /**
     * Listen for selected word and seach the definition
     */
    private fun observeSearchWord() {
        viewModel.selectedWord.observe(viewLifecycleOwner, {
            it?.let {
                val queryUrl = App.instance.generateDictionaryUrl(it)
                binding.webView.loadUrl(queryUrl)
            }
        })
    }


    /**
     * Clear cache when the bottomsheet is dismissed
     */
    override fun onStop() {
        super.onStop()
        binding.webView.clearCache(true)
        Timber.e("on stop called")
    }
}