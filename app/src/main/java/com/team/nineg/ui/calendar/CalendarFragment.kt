package com.team.nineg.ui.calendar

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import coil.load
import com.team.nineg.R
import com.team.nineg.base.BaseFragment
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.databinding.FragmentCalendarBinding
import com.team.nineg.ui.main.MainViewModel
import com.team.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    private val viewModel: CalendarViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                activityViewModel.refreshScreen()
            }
        }

    private val startPostingFormActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                activityViewModel.refreshScreen()

                val intent = result.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent?.getParcelableExtra(EXTRA_SAVE_GOODY, Goody::class.java)
                } else {
                    intent?.getParcelableExtra(EXTRA_SAVE_GOODY)
                }?.let { goody ->
                    ActivityUtil.startRecordDetailActivity(
                        binding.root.context,
                        goody,
                        startRecordDetailActivityForResult
                    )
                }
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserve()
        viewModel.requestGoodyList()
    }

    private fun initListener() {
        binding.fragmentCalendarImageFilter.setOnClickListener {
            when (childFragmentManager.findFragmentById(R.id.fragmentCalendarFragmentContainerView)) {
                is ScheduleFragment -> {
                    binding.fragmentCalendarImageFilter.load(R.drawable.ic_calendar)
                    showFeedFragment()
                }
                is FeedFragment -> {
                    binding.fragmentCalendarImageFilter.load(R.drawable.ab_grid)
                    showScheduleFragment()
                }
            }
        }

        binding.fragmentCalendarFloatingBtn.setOnClickListener {
            ActivityUtil.startPostingFormActivity(
                binding.root.context,
                startPostingFormActivityForResult
            )
        }
    }

    private fun initObserve() {
        activityViewModel.refreshScreen.observe(viewLifecycleOwner) {
            viewModel.requestGoodyList()
        }
    }

    private fun showScheduleFragment() {
        childFragmentManager.commit {
            replace(
                R.id.fragmentCalendarFragmentContainerView,
                ScheduleFragment.newInstance()
            )
        }
    }

    private fun showFeedFragment() {
        childFragmentManager.commit {
            replace(
                R.id.fragmentCalendarFragmentContainerView,
                FeedFragment.newInstance()
            )
        }
    }

    companion object {
        private const val TAG = "CalendarFragment"
        const val EXTRA_SAVE_GOODY = "save_goody"
    }
}