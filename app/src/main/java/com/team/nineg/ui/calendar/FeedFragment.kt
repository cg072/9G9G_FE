package com.team.nineg.ui.calendar

import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.team.nineg.R
import com.team.nineg.adapter.CalendarAdapter
import com.team.nineg.base.BaseFragment
import com.team.nineg.databinding.FragmentFeedBinding
import com.team.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private val viewModel: FeedViewModel by viewModels()
    private val activityViewModel: CalendarViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private lateinit var adapter: CalendarAdapter

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val ssaid =
                    Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
                activityViewModel.requestGoodyList(ssaid)
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.fragment_feed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarRecyclerView()
        initObserve()
        viewModel.getFeed()
    }

    private fun initCalendarRecyclerView() {
        adapter = CalendarAdapter {
            ActivityUtil.startRecordDetailActivity(
                binding.root.context,
                it,
                startRecordDetailActivityForResult
            )
        }
        binding.fragmentFeedRecyclerView.adapter = adapter
        binding.fragmentFeedRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 3)
    }

    private fun initObserve() {
        viewModel.calendarUiList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        activityViewModel.goodyMap.observe(viewLifecycleOwner) {
            viewModel.setGoodyMap(it)
            viewModel.getFeed()
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
        fun newInstance() = FeedFragment()
    }

}