package jp.co.yumemi.android.code_check.ui.repository_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding
/**
 * Fragment responsible for displaying details of a GitHub repository.
 */
@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {

    private val args: RepositoryDetailFragmentArgs by navArgs()
    private var binding: FragmentRepositoryDetailBinding? = null // Change to nullable
    private lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRepositoryDetailBinding.inflate(inflater, container, false).also {
            binding = it

        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RepositoryDetailViewModel::class.java]

        binding?.apply {
            detailsVM = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.setRepositoryDetails(args.repositoryArgument)
        logMessage("Repository details set")
        observeRepositoryDetail()
    }

    /**
     * Observes changes in repository details and updates the UI accordingly.
     */
    private fun observeRepositoryDetail() {
        viewModel.gitHubRepositoryDetails.observe(viewLifecycleOwner) { repositoryDetail ->
            repositoryDetail?.let {
                binding?.ownerIconView?.load(repositoryDetail.owner?.avatarUrl) {

                }
            }
        }
    }

    /**
     * Helper function for logging messages with a specified tag.
     *
     * @param message The message to be logged.
     */
    private fun logMessage(message: String) {
        Log.d("RepositoryDetailFragment", message)
    }
}