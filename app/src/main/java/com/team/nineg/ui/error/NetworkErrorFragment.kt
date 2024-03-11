package com.team.nineg.ui.error

import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.team.nineg.R
import com.team.nineg.base.BaseFragment
import com.team.nineg.databinding.FragmentNetworkErrorBinding
import com.team.nineg.ui.main.MainViewModel

class NetworkErrorFragment : BaseFragment<FragmentNetworkErrorBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_network_error

    private val mainViewModel: MainViewModel by activityViewModels()
    private val networkErrorViewModel: NetworkErrorViewModel by viewModels()
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRetry.setOnClickListener {
            networkErrorViewModel.retryConnection()
        }
        initObserve()
    }

    private fun initObserve() {
        networkErrorViewModel.retryConnection.observe(viewLifecycleOwner) {
//            val ssaid = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
//            mainViewModel.initUserData(ssaid)
        }
    }

    companion object {
        private const val TAG = "NetworkErrorFragment"
    }
}