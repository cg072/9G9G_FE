package com.example.nineg.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentMyPageBinding
import com.example.nineg.util.ActivityUtil

class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val viewModel: MyPageViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_page

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

    companion object {
        private const val URL_PRIVACY_POLICY = "https://smooth-cut-9b7.notion.site/1f03a089c18e4527b27e811ba18da721?pvs=4"
        private const val URL_TERMS_OF_SERVICE = "https://smooth-cut-9b7.notion.site/20078eea07894755879ed024f4a45b6e?pvs=4"
        private const val URL_GOODY_CARD_FORMS = "https://forms.gle/jNGAigYCVigjpeR58"
        private const val URL_CONTACT_US = "https://forms.gle/ayML38z73SKVRGc79"
    }
}