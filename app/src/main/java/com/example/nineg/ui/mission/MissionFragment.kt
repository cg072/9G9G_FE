package com.example.nineg.ui.mission

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.domain.asGoody
import com.example.nineg.databinding.FragmentMissionBinding
import com.example.nineg.ui.calendar.CalendarFragment
import com.example.nineg.ui.main.MainViewModel
import com.example.nineg.ui.mission.adapter.MissionCardAdapter
import com.example.nineg.ui.mission.adapter.MissionCardRecyclerViewClickListener
import com.example.nineg.util.ActivityUtil
import com.example.nineg.util.ActivityUtil.startPostingFormActivity
import com.example.nineg.util.DateUtil
import com.example.nineg.util.HorizontalMarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity

@AndroidEntryPoint
class MissionFragment : BaseFragment<FragmentMissionBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_mission

    private lateinit var missionCardAdapter: MissionCardAdapter
    private val viewModel: MissionViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val ssaid =
                    Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent?.getParcelableExtra(CalendarFragment.EXTRA_SAVE_GOODY, Goody::class.java)
                } else {
                    intent?.getParcelableExtra(CalendarFragment.EXTRA_SAVE_GOODY)
                }?.let { goody ->
                    viewModel.updateTodayGoody()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initBinding()
        initTutorial()
        binding.btnEdit.setOnClickListener {
            startPostingFormActivity(requireContext(), startForResult)
        }
    }

    private fun initBinding() {
        binding.tvDate.text = DateUtil.getTodayWithDay()
        binding.view = this
        binding.viewModel = viewModel
    }

    private fun initObserve() {
        viewModel.missionCardList.observe(viewLifecycleOwner) {
            missionCardAdapter.submitList(it)
        }
        viewModel.backToFirstPosition.observe(viewLifecycleOwner) {
            backToFirstPosition()
        }
    }

    private fun initRecyclerView() {
        missionCardAdapter = MissionCardAdapter(object : MissionCardRecyclerViewClickListener {
            override fun onClickRecyclerViewBookMark(cardInfo: MissionCard) {
                viewModel.updateBookmarkMissionCard(cardInfo)
            }

            override fun onClickRecyclerViewItem(cardInfo: MissionCard) {
                if (cardInfo.level == 0) {
                    val goody = cardInfo.asGoody(DateUtil.getSimpleToday())
                    ActivityUtil.startRecordDetailActivity(
                        binding.root.context,
                        goody,
                        startRecordDetailActivityForResult
                    )
                } else {
                    startPostingFormActivity(requireContext(), startForResult, cardInfo)
                }
            }
        })
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

    private fun initTutorial() {
        if (viewModel.isFirstLaunch()) {
            tutorialMissionCard()
            viewModel.setIsFirstLaunch(false)
        }
    }

    private fun tutorialMissionCard() {
        GuideView.Builder(requireActivity())
            .setContentText(getString(R.string.TEXT_TUTORIAL_SLIDE_MISSION_CARD))
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.ivPointMissionCard)
            .setGravity(Gravity.center)
            .setGuideListener { tutorialEditButton() }
            .build()
            .show()
    }

    private fun tutorialEditButton() {
        GuideView.Builder(requireActivity())
            .setContentText(getString(R.string.TEXT_TUTORIAL_CREATE_MISSION_CARD))
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.btnEdit)
            .setGuideListener { activityViewModel.startTutorialNav() }
            .build()
            .show()
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
        private const val VISIBLE_FAB_POSITION = 4
    }
}