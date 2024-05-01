package jp.co.yumemi.android.code_check.ui.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.code_check.databinding.FragmentSettingBinding
import jp.co.yumemi.android.code_check.ui.repository_detail.RepositoryDetailViewModel
@AndroidEntryPoint
class SettingFragment : Fragment() {

    // ViewModel for the fragment
    private val viewModel: SettingViewModel by viewModels()
    private var binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSettingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null

    }

}