package com.example.nineg.ui.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nineg.R
import com.example.nineg.adapter.CalendarAdapter
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentCalendarBinding
import com.example.nineg.dialog.CalendarFilterDialog
import com.example.nineg.ui.creation.PostingFormActivity
import com.example.nineg.ui.detail.RecordDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    companion object {
        private const val TAG = "CalendarFragment"
    }

    private val viewModel: CalendarViewModel by viewModels()

    private lateinit var adapter: CalendarAdapter
    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())

    private val startRecordDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }

    private val startPostingFormActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initListener()
        initCalendarRecyclerView()
        adapter.submitList(viewModel.getCalendarList(calendar))

    }


    private fun initData() {
        calendar = Calendar.getInstance()
        binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
    }

    private fun initListener() {
        binding.fragmentCalendarDateFilterContainer.setOnClickListener {
            showCalendarFilterDialog(it)
        }

        binding.fragmentCalendarFloatingBtn.setOnClickListener {
            startPostingFormActivity()
//            startRecordDetailActivity()
        }
    }

    private fun initCalendarRecyclerView() {
        adapter = CalendarAdapter()
        binding.fragmentCalendarRecyclerView.adapter = adapter
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
    }

    private fun showCalendarFilterDialog(it: View) {
        val dialog = CalendarFilterDialog(
            binding.root.context,
            it.bottom,
            calendar.clone() as Calendar
        ) { year, month ->
            calendar.set(year, month, 1)
            binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
            adapter.submitList(viewModel.getCalendarList(calendar))
        }

        dialog.show()
    }

    private fun startPostingFormActivity() {
        startPostingFormActivityForResult.launch(
            Intent(
                binding.root.context,
                PostingFormActivity::class.java
            )
        )
    }

    private fun startRecordDetailActivity() {
        startRecordDetailActivityForResult.launch(
            Intent(
                binding.root.context,
                RecordDetailActivity::class.java
            )
        )
    }
}