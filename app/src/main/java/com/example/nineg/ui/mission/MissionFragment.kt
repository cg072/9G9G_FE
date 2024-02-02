package com.example.nineg.ui.mission

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentMissionBinding
import com.example.nineg.ui.mission.adapter.MissionCardAdapter
import com.example.nineg.util.DateUtil
import com.example.nineg.util.HorizontalMarginItemDecoration

class MissionFragment : BaseFragment<FragmentMissionBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_mission

    private lateinit var missionCardAdapter: MissionCardAdapter
    private val viewModel: MissionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initBinding()
    }

    private fun initBinding() {
        binding.tvDate.text = DateUtil.getToday()
        binding.view = this
        binding.viewModel = viewModel
    }

    private fun initObserve() {
        viewModel.missionCards.observe(viewLifecycleOwner) {
            missionCardAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        missionCardAdapter = MissionCardAdapter()
        binding.rvMission.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = missionCardAdapter
            addItemDecoration(HorizontalMarginItemDecoration(12))
        }

        PagerSnapHelper().also {
            it.attachToRecyclerView(binding.rvMission)
        }

        binding.rvMission.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                loadMoreItemsIfAtEnd()

                adjustFabVisibilityOnScroll(recyclerView)
            }
        })
        binding.rvMission.getChildAdapterPosition(binding.rvMission.getChildAt(0))
    }

    private fun loadMoreItemsIfAtEnd() {
        if (!binding.rvMission.canScrollHorizontally(1)) {
            viewModel.addMissionCard()
        }
    }

    private fun adjustFabVisibilityOnScroll(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visiblePosition = layoutManager.findLastVisibleItemPosition()

        if (visiblePosition >= VISIBLE_FAB_POSITION) {
            binding.fabBackToFirst.show()
        } else {
            binding.fabBackToFirst.hide()
        }
    }

    fun backToFirstPosition() {
        binding.rvMission.smoothScrollToPosition(POSITION_FIRST)
    }

    companion object {
        private const val TAG = "MissionFragment"
        private const val POSITION_FIRST = 0
        private const val VISIBLE_FAB_POSITION = 6
    }
}