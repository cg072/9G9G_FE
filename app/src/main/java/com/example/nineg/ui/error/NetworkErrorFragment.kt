package com.example.nineg.ui.error

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentCalendarBinding
import com.example.nineg.databinding.FragmentNetworkErrorBinding
import com.example.nineg.ui.main.MainViewModel

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
            Log.d(TAG, "initObserve: retryConnection")
            val ssaid = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
            mainViewModel.initUserData(ssaid)
        }
    }

    companion object {
        private const val TAG = "NetworkErrorFragment"
    }
}