package jp.co.yumemi.android.code_check.ui.favourite_repo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.database.AppDatabase
import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import jp.co.yumemi.android.code_check.databinding.FragmentFavouriteRepositoryBinding
import jp.co.yumemi.android.code_check.ui.adapters.FavouriteRepositoryAdapter
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteRepositoryFragment : Fragment() {

    @Inject
    lateinit var favoriteRepositoryDao: FavoriteRepositoryDao
    @Inject
    lateinit var appDatabase: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FavouriteRepositoryViewModel
    private lateinit var favoriteAdapter: FavouriteRepositoryAdapter
    private lateinit var binding:FragmentFavouriteRepositoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavouriteRepositoryBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(FavouriteRepositoryViewModel::class.java)
        favoriteAdapter = FavouriteRepositoryAdapter(mutableListOf())
        setUpRecyclerView(binding.recyclerView)
        initObservers()
        return binding?.root


    }
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = favoriteAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun initObservers() {
        viewModel.readAllData.observe(viewLifecycleOwner) { favoriteRepositories ->
            favoriteRepositories?.let {
                favoriteAdapter.updateList(it.toMutableList())
                Log.d("FavouriteFragment", "Favorite repository list updated")
            }
        }
    }

}