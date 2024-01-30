package com.example.nineg.ui.mission

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.data.db.local.MissionCardInfo
import com.example.nineg.databinding.FragmentMissionBinding
import com.example.nineg.ui.mission.adapter.MissionCardAdapter

class MissionFragment : BaseFragment<FragmentMissionBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_mission

    private lateinit var missionCardAdapter: MissionCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvMission)

        missionCardAdapter.setOnItemClickListener {
            Log.d(TAG, "initRecyclerView: ${it.title}")
        }
        missionCardAdapter.submitList(TEST_DATA)
    }

    companion object {
        private const val TAG = "MissionFragment"
        private val TEST_DATA =  listOf(
            MissionCardInfo(
                id = 1,
                image = "https://i.ytimg.com/vi/RncY8aNDr8U/maxresdefault.jpg",
                title = "title1",
                description = "description1",
                isBookmarked = true
            ),
            MissionCardInfo(
                id = 2,
                image = "https://m.segye.com/content/image/2023/07/06/20230706511066.jpg",
                title = "title2",
                description = "description2",
                isBookmarked = false
            ),
            MissionCardInfo(
                id = 3,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title3",
                description = "description3",
                isBookmarked = false
            ),
            MissionCardInfo(
                id = 4,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title4",
                description = "description4"
            ),
            MissionCardInfo(
                id = 5,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title5",
                description = "description5"
            ),
            MissionCardInfo(
                id = 6,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title6",
                description = "description6"
            ),
            MissionCardInfo(
                id = 7,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title7",
                description = "description7"
            ),
            MissionCardInfo(
                id = 8,
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                title = "title8",
                description = "description8"
            )
        )
    }
}