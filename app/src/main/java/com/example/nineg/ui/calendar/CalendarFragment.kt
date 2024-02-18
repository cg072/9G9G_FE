package com.example.nineg.ui.calendar

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.databinding.FragmentCalendarBinding
import com.example.nineg.ui.main.MainViewModel
import com.example.nineg.util.ActivityUtil
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
        val ssaid =
            Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.requestGoodyList(ssaid)
    }

    private fun initListener() {
        binding.fragmentCalendarImageFilter.setOnClickListener {
            when (childFragmentManager.findFragmentById(R.id.fragmentCalendarFragmentContainerView)) {
                is ScheduleFragment -> showFeedFragment()
                is FeedFragment -> showScheduleFragment()
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
            val ssaid =
                Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
            viewModel.requestGoodyList(ssaid)
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