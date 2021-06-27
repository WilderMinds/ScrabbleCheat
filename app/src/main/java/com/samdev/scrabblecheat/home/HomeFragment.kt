package com.samdev.scrabblecheat.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdev.scrabblecheat.App
import com.samdev.scrabblecheat.R
import com.samdev.scrabblecheat.databinding.FragmentHomeBinding
import com.samdev.scrabblecheat.home.adapter.ResultsAdapter
import com.samdev.scrabblecheat.model.WordResult
import com.samdev.scrabblecheat.utils.ScoreComparator
import com.samdev.scrabblecheat.webview.WebViewFragment
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        // binding.adapter = resultsAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLetterScore()
        observeResult()
    }


    /**
     * Display each letter and score assigned to it
     */
    private fun setupLetterScore() {
        // val letterScoreAdapter = LegacyAdapter(mutableListOf(), true)
        val letterScoreAdapter = ResultsAdapter(true, this)
        binding.rvScoreboard.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        binding.rvScoreboard.adapter = letterScoreAdapter
        // binding.rvScoreboard.setHasFixedSize(true)

        viewModel.letterScores.observe(viewLifecycleOwner, {
            it?.let {
                //letterScoreAdapter.updateList(it)
                letterScoreAdapter.submitList(it)
            }
        })
    }


    /**
     * Display each possible word that can be formed from the letters
     * provided
     */
    private fun observeResult() {
        val resultsAdapter = ResultsAdapter(false, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = resultsAdapter
        binding.recyclerView.setHasFixedSize(true)

        viewModel.results.observe(viewLifecycleOwner, {
            it?.let {
                Timber.e("received (listSize=${it.size}) list => $it")
                Collections.sort(it, ScoreComparator())
                resultsAdapter.submitList(it)
            }
        })
    }


    /**
     * List click callback
     */
    override fun onItemClicked(item: WordResult) {
        searchWord(item.word)
    }


    /**
     * Search word definition on google
     */
    private fun searchWord(word: String) {
        val connected = isConnected()
        Timber.e("connected = $connected")
        viewModel.connected.postValue(connected)
        viewModel.selectedWord.postValue(word)

        val queryUrl = App.instance.generateDictionaryUrl(word)
        showBottomSheet(queryUrl)
    }


    /**
     * Dictionary webView to show the word definition
     *
     * Check webView availability before showing it
     */
    private fun showBottomSheet(url: String) {
        val webViewAvailable = App.instance.isWebViewAvailable()
//        if (!webViewAvailable) {
//            return
//        }

        val webViewFragment = WebViewFragment()
        webViewFragment.show(childFragmentManager, webViewFragment.tag)
    }


    /**
     * Ensure network connectivity when getting word definitions
     */
    private fun isConnected() : Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}