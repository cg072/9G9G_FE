package com.example.nineg.ui.calendar

import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nineg.R
import com.example.nineg.adapter.CalendarAdapter
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentScheduleBinding
import com.example.nineg.dialog.CalendarFilterDialog
import com.example.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>() {

    private val viewModel: ScheduleViewModel by viewModels()
    private val activityViewModel: CalendarViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var adapter: CalendarAdapter
    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val ssaid =
                    Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
                activityViewModel.requestGoodyList(ssaid)
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.fragment_schedule

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
        initCalendarRecyclerView()
        initObserve()
        initListener()

        val ssaid = Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d(TAG, "kch ssaid : ${ssaid}")
        viewModel.getCalendarList(calendar)
    }

    private fun initCalendarRecyclerView() {
        adapter = CalendarAdapter {
            ActivityUtil.startRecordDetailActivity(
                binding.root.context,
                it,
                startRecordDetailActivityForResult
            )
        }
        binding.fragmentCalendarRecyclerView.adapter = adapter
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
    }

    private fun initObserve() {
        viewModel.calendarUiList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        activityViewModel.goodyMap.observe(viewLifecycleOwner) {
            viewModel.setGoodyMap(it)
            viewModel.getCalendarList(calendar)
        }
    }

    private fun initListener() {
        binding.fragmentCalendarDateFilterContainer.setOnClickListener {
            showCalendarFilterDialog(it)
        }
    }

    private fun showCalendarFilterDialog(it: View) {
        val dialog = CalendarFilterDialog(
            binding.root.context,
            it.bottom,
            calendar.clone() as Calendar
        ) { year, month ->
            calendar.set(year, month, 1)
            binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
            viewModel.getCalendarList(calendar)
        }

        dialog.show()
    }

    companion object {
        private val TAG = this::class.java.simpleName
        fun newInstance() = ScheduleFragment()
    }
}