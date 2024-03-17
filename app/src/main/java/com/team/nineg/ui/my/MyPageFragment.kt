package com.team.nineg.ui.my

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.kakao.sdk.user.UserApiClient
import com.team.nineg.BuildConfig
import com.team.nineg.R
import com.team.nineg.base.BaseFragment
import com.team.nineg.databinding.FragmentMyPageBinding
import com.team.nineg.dialog.LogoutDialog
import com.team.nineg.dialog.RevokeDialog
import com.team.nineg.ui.detail.RecordDetailActivity
import com.team.nineg.ui.login.UserViewModel
import com.team.nineg.util.ActivityUtil

class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: MyPageViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentMyPageName.setText(R.string.goody_profile_name)
        binding.fragmentMyPageProfile.load(R.drawable.ic_goody_profile)
        binding.fragmentMyPageVersion.text = BuildConfig.VERSION_NAME

        binding.fragmentMyPagePrivacyPolicyBtn.setOnClickListener {
            ActivityUtil.startExternalBrowser(binding.root.context, URL_PRIVACY_POLICY)
        }

        binding.fragmentMyPageTermsOfServiceBtn.setOnClickListener {
            ActivityUtil.startExternalBrowser(binding.root.context, URL_TERMS_OF_SERVICE)
        }

        binding.fragmentMyPageGoodyCardFormsBtn.setOnClickListener {
            ActivityUtil.startExternalBrowser(binding.root.context, URL_GOODY_CARD_FORMS)
        }

        binding.fragmentMyPageContactUsBtn.setOnClickListener {
            ActivityUtil.startExternalBrowser(binding.root.context, URL_CONTACT_US)
        }

        binding.fragmentMyPageLogoutBtn.setOnClickListener {
            val dialog = LogoutDialog(binding.root.context) {
                userViewModel.logout()
                findNavController().navigate(R.id.action_myPageFragment_to_loginFragment)

//                val navController = findNavController()
//                val startDestination = navController.graph.startDestination
//                val navOptions = NavOptions.Builder()
//                    .setPopUpTo(startDestination, true)
//                    .build()
//                navController.navigate(startDestination, null, navOptions)
//                logout()
            }
            dialog.show()
        }

        binding.fragmentMyPageRevokeBtn.setOnClickListener {
            val dialog = RevokeDialog(binding.root.context) {
                userViewModel.revoke()
                findNavController().navigate(R.id.action_myPageFragment_to_loginFragment)
//                revoke()
            }
            dialog.show()
        }
    }

    private fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                findNavController().navigate(R.id.action_myPageFragment_to_loginFragment)
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    private fun revoke() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            } else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    companion object {
        private const val TAG = "MyPageFragment"
        private const val URL_PRIVACY_POLICY = "https://smooth-cut-9b7.notion.site/1f03a089c18e4527b27e811ba18da721?pvs=4"
        private const val URL_TERMS_OF_SERVICE = "https://smooth-cut-9b7.notion.site/20078eea07894755879ed024f4a45b6e?pvs=4"
        private const val URL_GOODY_CARD_FORMS = "https://forms.gle/jNGAigYCVigjpeR58"
        private const val URL_CONTACT_US = "https://forms.gle/ayML38z73SKVRGc79"
    }
}