package com.team.nineg.ui.login

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.team.nineg.R
import com.team.nineg.base.BaseFragment
import com.team.nineg.base.UiState
import com.team.nineg.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
            logout()
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            successLogin()
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        listener()
    }

    private fun listener() {
        binding.activityLoginKakaoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_missionFragment)
//            loginKakao()
        }
    }

    private fun observe() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_missionFragment)
                }
                is UiState.Error -> {
                    Toast.makeText(binding.root.context, R.string.login_error_message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun loginKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(binding.root.context)) {
            UserApiClient.instance.loginWithKakaoTalk(binding.root.context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(binding.root.context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    successLogin()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(binding.root.context, callback = callback)
        }
    }

    private fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    private fun successLogin() {
        val ssaid = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.initUserData(ssaid)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}