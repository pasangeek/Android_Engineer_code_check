package jp.co.yumemi.android.code_check.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.ui.favourite_repo.FavouriteRepositoryViewModel
import kotlinx.coroutines.launch

/**
 * Adapter for displaying GitHub repository details in a RecyclerView.
 *
 * @param itemClickListener The click listener for items in the adapter.
 */
class GithubRepositoryDetailAdapter(
    private val itemClickListener: OnItemClickListener,
    private val viewModel: FavouriteRepositoryViewModel
) : ListAdapter<GithubRepositoryData, GithubRepositoryDetailAdapter.ViewHolder>(diff_util) {

    // Interface definition for the click listener of items in the adapter.
    interface OnItemClickListener {
        fun itemClick(repo: GithubRepositoryData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create a ViewHolder
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder
        val gitHubRepositoryItem = getItem(position)
        holder.bind(gitHubRepositoryItem)
    }

    inner class ViewHolder(private val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set a click listener for the item view
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.itemClick(getItem(position))
                }
            }

            // Set click listener for the heart image view
            binding.heartImageView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val repository = getItem(position)
                    val favoriteRepository = FavoriteRepositoryEntity(
                        name = repository.name,
                        owner = repository.owner,
                        language = repository.language,
                        stargazersCount = repository.stargazersCount,
                        watchersCount = repository.watchersCount,
                        forksCount = repository.forksCount,
                        openIssuesCount = repository.openIssuesCount
                    )

                    // Toggle favorite status
                    viewModel.viewModelScope.launch {
                        val isFavorite = repository.name?.let { it1 -> viewModel.checkIfFavorite(it1) }
                        if (isFavorite == true) {
                            viewModel.removeFavoriteRepository(favoriteRepository)
                        } else {
                            viewModel.addFavoriteRepository(favoriteRepository)
                        }
                        // Notify the adapter that the dataset has changed
                        notifyItemChanged(bindingAdapterPosition)
                    }
                }
            }
        }

        /**
         * Binds the data to the ViewHolder.
         *
         * @param repo The GitHub repository data to bind.
         */
        fun bind(repo: GithubRepositoryData) {
            with(binding) {
                ivOwner.load(repo.owner?.avatarUrl)
                repositoryNameView.text = repo.name

                // Update heart icon color based on favorite status
                updateHeartIconColor(repo)
            }
        }

        /**
         * Updates the color of the heart icon based on the favorite status.
         *
         * @param repo The GitHub repository data.
         */
        private fun updateHeartIconColor(repo: GithubRepositoryData) {
            viewModel.viewModelScope.launch {
                val isFavorite = repo.name?.let { viewModel.checkIfFavorite(it) }
                if (isFavorite == true) {
                    // Set heart image view color to favorite color
                    binding.heartImageView.setColorFilter(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.favorite_color
                        )
                    )
                } else {
                    // Set heart image view color to non-favorite color
                    binding.heartImageView.setColorFilter(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.non_favorite_color
                        )
                    )
                }
            }
        }
    }

    companion object {
        // Define a DiffUtil.ItemCallback for efficient updates
        val diff_util = object : DiffUtil.ItemCallback<GithubRepositoryData>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                // Check if items have the same identifier (e.g., name)
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                // Check if the contents of items are the same (data equality)
                return oldItem == newItem
            }
        }
    }
}
