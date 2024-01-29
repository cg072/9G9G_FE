package com.example.nineg.ui.mission

import android.os.Bundle
import android.view.View
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentMissionBinding

class MissionFragment: BaseFragment<FragmentMissionBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_mission

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}