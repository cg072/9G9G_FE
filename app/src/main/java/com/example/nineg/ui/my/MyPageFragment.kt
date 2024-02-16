package com.example.nineg.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentMyPageBinding

class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val viewModel: MyPageViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

    }

}