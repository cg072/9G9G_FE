package com.team.nineg.ui.mission

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.team.nineg.R
import com.team.nineg.base.BaseFragment
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.data.db.domain.asGoody
import com.team.nineg.databinding.FragmentMissionBinding
import com.team.nineg.ui.login.LoginFragment
import com.team.nineg.ui.login.UserViewModel
import com.team.nineg.ui.main.MainViewModel
import com.team.nineg.ui.mission.adapter.MissionCardAdapter
import com.team.nineg.ui.mission.adapter.MissionCardRecyclerViewClickListener
import com.team.nineg.util.ActivityUtil
import com.team.nineg.util.DateUtil
import com.team.nineg.util.HorizontalMarginItemDecoration
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
    private val userViewModel: UserViewModel by activityViewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                activityViewModel.refreshScreen()
            }
        }


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                activityViewModel.refreshScreen()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (!success) {
                    activity?.finish()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        initObserve()
        initRecyclerView()
        initBinding()
        binding.btnEdit.setOnClickListener {
            ActivityUtil.startPostingFormActivity(requireContext(), startForResult)
        }
    }

    private fun checkLogin() {
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                findNavController().navigate(R.id.action_missionFragment_to_loginFragment)
            } else {
                showTutorial()
            }
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

        activityViewModel.refreshScreen.observe(viewLifecycleOwner) {
            viewModel.updateTodayGoody()
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
                    ActivityUtil.startPostingFormActivityFromMissionCard(
                        requireContext(),
                        cardInfo,
                        startForResult
                    )
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

    private fun showTutorial() {
        if (viewModel.isFirstLaunch()) {
            tutorialMissionCard()
            viewModel.setIsFirstLaunch(false)
        }
    }

    private fun tutorialMissionCard() {
        GuideView.Builder(requireActivity())
            .setContentText(getString(R.string.text_tutorial_slide_mission_card))
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.ivPointMissionCard)
            .setGravity(Gravity.center)
            .setGuideListener { tutorialEditButton() }
            .build()
            .show()
    }

    private fun tutorialEditButton() {
        GuideView.Builder(requireActivity())
            .setContentText(getString(R.string.text_tutorial_create_mission_card))
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