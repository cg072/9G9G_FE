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

    enum class GridType {
        Calendar,
        Feed
    }

    private val viewModel: CalendarViewModel by viewModels()

    private lateinit var adapter: CalendarAdapter
    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
    private var gridType: GridType = GridType.Calendar

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
        initObserve()
        viewModel.getCalendarList(calendar)
    }

    private fun initData() {
        calendar = Calendar.getInstance()
        binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
    }

    private fun initListener() {
        binding.fragmentCalendarImageFilter.setOnClickListener {
            /**
             * 세부 Fragment 로 나눌 예정 너무 조잡함
             */
            gridType = when (gridType) {
                GridType.Calendar -> {
                    convertFeedType()
                    GridType.Feed
                }
                GridType.Feed -> {
                    convertCalendarType()
                    GridType.Calendar
                }
            }
        }

        binding.fragmentCalendarDateFilterContainer.setOnClickListener {
            showCalendarFilterDialog(it)
        }

        binding.fragmentCalendarFloatingBtn.setOnClickListener {
            startPostingFormActivity()
//            startRecordDetailActivity()
        }

        /**
         * 제거 예정 임시로 생성
         */
        binding.fragmentCalendarTitle.setOnClickListener {
            startRecordDetailActivity()
        }
    }

    private fun convertCalendarType() {
        binding.fragmentCalendarDateFilterContainer.visibility = View.VISIBLE
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
        viewModel.getCalendarList(calendar)
    }

    private fun convertFeedType() {
        binding.fragmentCalendarDateFilterContainer.visibility = View.INVISIBLE
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 3)
        viewModel.getFeed()
    }

    private fun initCalendarRecyclerView() {
        adapter = CalendarAdapter()
        binding.fragmentCalendarRecyclerView.adapter = adapter
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
    }

    private fun initObserve() {
        viewModel.calendarUiList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
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

    private fun startPostingFormActivity() {
        /**
         * 1. 카드 선택할 경우 이미지, 타이틀, 내용 갖고 진입 (타이틀은 텍스트, 내용은 가이드 힌트)
         * 2. 직접 작성 선택할 경우, 내용 - 가이드 노출 일상 속에서 어떤 낭만을 찾아내셨나요? / 느꼈던 감정과 생각을 자유롭게 적어주세요 (선택)
         * 타이틀은 힌트 카드의 제목을 작성해주세요
         */

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