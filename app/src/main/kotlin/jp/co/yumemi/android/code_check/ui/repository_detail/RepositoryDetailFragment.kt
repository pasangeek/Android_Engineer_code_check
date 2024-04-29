package jp.co.yumemi.android.code_check.ui.repository_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

/**
 * Fragment responsible for displaying details of a GitHub repository.
 */
@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {
    // Arguments passed to the fragment
    private val args: RepositoryDetailFragmentArgs by navArgs()

    // View binding for the fragment
    private var binding: FragmentRepositoryDetailBinding? = null // Change to nullable

    // ViewModel for the fragment
    private lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentRepositoryDetailBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[RepositoryDetailViewModel::class.java]
        // Set up data binding
        binding?.apply {
            detailsVM = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        // Set repository details
        viewModel.setRepositoryDetails(args.repositoryArgument)
        logMessage("Repository details set")
        // Observe repository details changes

        observeRepositoryDetail()
        // Set up back button click listener
        backButton()

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
     * Set up click listener for the back button.
     */
    fun backButton() {
        binding?.backButton?.setOnClickListener {
            findNavController().navigateUp() // Navigate back to the previous fragment
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