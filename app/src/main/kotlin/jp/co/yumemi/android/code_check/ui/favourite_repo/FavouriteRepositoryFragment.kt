package jp.co.yumemi.android.code_check.ui.favourite_repo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.database.AppDatabase
import jp.co.yumemi.android.code_check.data.database.ApplicationRepositoryDao
import jp.co.yumemi.android.code_check.databinding.FragmentFavouriteRepositoryBinding
import jp.co.yumemi.android.code_check.ui.adapters.FavouriteRepositoryAdapter
import javax.inject.Inject

/**
 * Fragment for displaying favorite repositories.
 */
@AndroidEntryPoint
class FavouriteRepositoryFragment : Fragment() {

    @Inject
    lateinit var applicationRepositoryDao: ApplicationRepositoryDao

    @Inject
    lateinit var appDatabase: AppDatabase

    // ViewModel instance for managing favorite repositories
    val viewModel: FavouriteRepositoryViewModel by viewModels()

    // Adapter for displaying favorite repositories
    private lateinit var favoriteAdapter: FavouriteRepositoryAdapter

    // View binding instance
    private var binding: FragmentFavouriteRepositoryBinding? = null

    /**
     * Creates the view for the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentFavouriteRepositoryBinding.inflate(inflater, container, false).apply {
            binding = this
            setUpRecyclerView(recyclerView)
            initObservers()
        }.root
    }

    /**
     * Sets up the RecyclerView.
     *
     * @param recyclerView The RecyclerView to set up.
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FavouriteRepositoryAdapter(mutableListOf(), viewModel)
                .also { favoriteAdapter = it }
        }
    }

    /**
     * Initializes observers for LiveData.
     */
    private fun initObservers() {
        viewModel.readAllData.observe(viewLifecycleOwner) { favoriteRepositories ->
            favoriteRepositories?.let {
                favoriteAdapter.updateList(it.toMutableList())
                Log.d("FavouriteFragment", "Favorite repository list updated")
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

    }
}

