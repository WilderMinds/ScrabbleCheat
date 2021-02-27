package com.samdev.scrabblecheat.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdev.scrabblecheat.R
import com.samdev.scrabblecheat.databinding.FragmentHomeBinding
import com.samdev.scrabblecheat.home.adapter.LegacyAdapter
import com.samdev.scrabblecheat.home.adapter.LetterScoreAdapter
import com.samdev.scrabblecheat.home.adapter.ResultsAdapter
import com.samdev.scrabblecheat.utils.ScoreComparator
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

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


    private fun observeResult() {
        val resultsAdapter = LegacyAdapter(mutableListOf(), false) //ResultsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = resultsAdapter

        viewModel.results.observe(viewLifecycleOwner, {
            it?.let {
                Timber.e("received (listSize=${it.size}) list => $it")
                Collections.sort(it, ScoreComparator())
                // resultsAdapter.submitList(it)
                resultsAdapter.updateList(it)
            }
        })
    }

    private fun setupLetterScore() {
        val letterScoreAdapter = LegacyAdapter(mutableListOf(), true) // LetterScoreAdapter()
        binding.rvScoreboard.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvScoreboard.adapter = letterScoreAdapter

        viewModel.letterScores.observe(viewLifecycleOwner, {
            it?.let {
                // letterScoreAdapter.submitList(it)
                letterScoreAdapter.updateList(it)
            }
        })
    }


}