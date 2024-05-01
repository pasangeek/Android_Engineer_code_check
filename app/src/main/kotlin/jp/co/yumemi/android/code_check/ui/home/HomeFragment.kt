package jp.co.yumemi.android.code_check.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.common.gone
import jp.co.yumemi.android.code_check.common.show
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.ui.adapters.GithubRepositoryDetailAdapter
import jp.co.yumemi.android.code_check.ui.error_dialog.ErrorDialog
import jp.co.yumemi.android.code_check.ui.favourite_repo.FavouriteRepositoryViewModel
import javax.inject.Inject

/**
 * Fragment responsible for displaying and managing the home screen UI.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var githubRepositoryDetailAdapter: GithubRepositoryDetailAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initializeRecycleViewAdapter()
        initializeErrorDialog()
        initiateGithubAccountAdapter()

    }

    /**
     * Initialize observers for LiveData updates.
     */
    private fun initObservers() {
        viewModel.responseGithubRepositoryList.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Success<*> -> {
                    binding?.apply {
                        progressBar.gone()
                        animationView.gone()
                    }
                    val data = resultState.result as? List<GithubRepositoryData>
                    data?.let {
                        githubRepositoryDetailAdapter.submitList(it)
                        Log.d("SearchFragment", "GitHub repository list updated")
                    } ?: Log.e("SearchFragment", "Data is not of type List<GithubRepositoryData>")
                }

                is ResultState.Loading -> {
                    binding?.progressBar?.show()
                }

                is ResultState.Failure -> {
                    binding?.progressBar?.gone()
                }
            }
        }
    }

    /**
     * Initialize RecyclerView adapter.
     */
    private fun initializeRecycleViewAdapter() {
        val viewModel: FavouriteRepositoryViewModel by viewModels()
        // Get the root view of the fragment
// Get the root view of the fragment
        val rootView = binding?.root ?: return

        // Initializing the RecyclerView adapter

        Log.d("HomeFragment", "Initializing RecyclerView adapter")
        this.githubRepositoryDetailAdapter = GithubRepositoryDetailAdapter(
            object :
                GithubRepositoryDetailAdapter.OnItemClickListener {
                override fun itemClick(repo: GithubRepositoryData) {
                    gotoRepositoryFragment(repo)
                    logMessage("GitHub repository list updated")
                }
            }, viewModel,rootView
        )
        // Set the RecyclerView adapter
        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter

        // Log message to indicate the completion of initialization
        Log.d("HomeFragment", "RecyclerView adapter initialized")
    }

    /**
     * Initialize GitHub repository adapter.
     */
    private fun initiateGithubAccountAdapter() {

// Setting the RecyclerView adapter
        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter
// Observing changes in the GitHub repository list and updating the adapter
        viewModel.gitHubRepositoryList.observe(viewLifecycleOwner) {
            githubRepositoryDetailAdapter.submitList(it)
            // Check if the list is empty and set the visibility of the "empty" layout
            Log.d("SearchFragment", "GitHub repository list updated")
        }

        initializeSearch()
    }

    /**
     * Initialize search functionality and observe input changes.
     */
    private fun initializeSearch() {
        binding?.searchInputText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText =
                    s?.trim().toString() // Trim the input to remove leading and trailing spaces

                // Check if the trimmed search text is not empty and contains at least one non-space character
                if (searchText.isNotBlank()) {
                    // Check if the search text is valid (e.g., meets specific criteria)
                    if (isSearchTextValid(searchText)) {
                        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
                            if (isOnline) {
                                viewModel.searchResults(searchText)
                                logMessage("Search initiated with query: $searchText")
                            } else {
                                viewModel.errorState.value =
                                    ErrorState.Error("No internet connection available")
                            }
                        }
                    } else {
                        binding?.apply {
                            searchInputText.error = "Please enter a valid search query"
                            githubRepositoryDetailAdapter.submitList(emptyList())
                        }
                        logMessage("Invalid search query: $searchText")
                    }
                } else {
                    binding?.apply {
                        searchInputText.error = "Please enter a search query"
                        githubRepositoryDetailAdapter.submitList(emptyList())
                    }
                    logMessage("Search query cleared")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * Function to validate the search text.
     * You can customize the validation logic as per your requirements.
     */
    private fun isSearchTextValid(searchText: String): Boolean {
        // Example validation criteria: search text should not contain only spaces
        return searchText.trim().isNotEmpty()
    }

    /**
     * Navigate to the RepositoryFragment with the selected repository item.
     *
     * @param item The selected GithubRepositoryData item.
     */
    fun gotoRepositoryFragment(item: GithubRepositoryData) {
        val action =
            HomeFragmentDirections.actionHomeFragment2ToRepositoryDetailFragment2(repositoryArgument = item)
        findNavController().navigate(action)
        logMessage("Navigating to RepositoryDetailFragment with item: ${item.name}")
    }

    /**
     * Initialize error dialog.
     */
    private fun initializeErrorDialog() {

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorState ->
            when (errorState) {
                is ErrorState.Error -> {
                    val dialogFragment = ErrorDialog(errorState.message, viewModel)
                    dialogFragment.show(childFragmentManager, "ErrorDialog")
                }

                null -> {
                    // Handle null case
                    Log.e("ErrorDialog", "Received null error state")
                }
            }

        }
    }

    /**
     * Clears the binding reference onDestroyView to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null
        logMessage("View destroyed")
    }

    /**
     * Helper function for logging messages with a specified tag.
     *
     * @param message The message to be logged.
     */
    private fun logMessage(message: String) {
        Log.d("SearchFragment", message)
    }

}