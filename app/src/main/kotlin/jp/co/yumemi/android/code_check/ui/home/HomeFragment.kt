package jp.co.yumemi.android.code_check.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.common.gone
import jp.co.yumemi.android.code_check.common.show
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.ui.adapters.GithubRepositoryDetailAdapter

@AndroidEntryPoint
class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var githubRepositoryDetailAdapter: GithubRepositoryDetailAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding?.vm = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycleViewAdapter()

        initiateGithubAccountAdapter()
        initObservers()
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
     * Initialize search functionality and observe input changes.
     */
    private fun initializeSearch() {
        binding?.searchInputText?.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                // Get the text from the input field
                val searchText = editText.text.toString()

                if (searchText.isEmpty()) {
                    // Show a message if the search input is empty
                    viewModel.errorState.value = ErrorState.Error("search input is empty")
                } else {
                    // Trigger a search when the search action is performed
                    viewModel.searchResults(searchText)
                    logMessage("Search initiated with query: $searchText")

                    // Hide the keyboard after initiating the search

                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initializeRecycleViewAdapter() {
        // Initializing the RecyclerView adapter
        Log.d("HomeFragment", "Initializing RecyclerView adapter")
        this.githubRepositoryDetailAdapter = GithubRepositoryDetailAdapter(object :
            GithubRepositoryDetailAdapter.OnItemClickListener {
            override fun itemClick(repo: GithubRepositoryData) {
                gotoRepositoryFragment(repo)
                logMessage("GitHub repository list updated")
            }
        })
        // Set the RecyclerView adapter
        binding?.recyclerView?.adapter = githubRepositoryDetailAdapter

        // Log message to indicate the completion of initialization
        Log.d("HomeFragment", "RecyclerView adapter initialized")
    }

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
     * Navigate to the RepositoryFragment with the selected repository item.
     *
     * @param item The selected GithubRepositoryData item.
     */
    fun gotoRepositoryFragment(item: GithubRepositoryData) {
        val action =
            HomeFragmentDirections.actionHomeFragment2ToRepositoryDetailFragment2(repositoryArgument= item)
        findNavController().navigate(action)
        logMessage("Navigating to RepositoryDetailFragment with item: ${item.name}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null
        logMessage("View destroyed")
    }

    // Helper function for logging messages with a specified tag
    private fun logMessage(message: String) {
        Log.d("SearchFragment", message)
    }

}