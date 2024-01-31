package com.example.nineg.ui.mission

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentMissionBinding
import com.example.nineg.ui.mission.adapter.MissionCardAdapter

class MissionFragment : BaseFragment<FragmentMissionBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_mission

    private lateinit var missionCardAdapter: MissionCardAdapter
    private val viewModel: MissionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.missionCards.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserve: ${it.size}")
            missionCardAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        missionCardAdapter = MissionCardAdapter()
        binding.rvMission.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = missionCardAdapter
        }

        PagerSnapHelper().also {
            it.attachToRecyclerView(binding.rvMission)
        }

        binding.rvMission.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvMission.canScrollHorizontally(1)) {
                    Log.d(TAG, "onScrollStateChanged: end")
                    viewModel.addMissionCard()
                }
            }
        })
    }

    companion object {
        private const val TAG = "MissionFragment"
    }
}