package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.databinding.FavouriteLayoutItemBinding
import jp.co.yumemi.android.code_check.ui.favourite_repo.FavouriteRepositoryViewModel

/**
 * Adapter for displaying favorite repositories in a RecyclerView.
 *
 * @property favoriteRepositories The list of favorite repositories to display.
 * @property viewModel The ViewModel associated with the adapter.
 */
class FavouriteRepositoryAdapter(
    private val favoriteRepositories: MutableList<FavoriteRepositoryEntity>,
    private val viewModel: FavouriteRepositoryViewModel
) :
    RecyclerView.Adapter<FavouriteRepositoryAdapter.ViewHolder>() {
    /**
     * ViewHolder for displaying each favorite repository item.
     *
     * @property binding The binding object for the layout.
     */
    inner class ViewHolder(private val binding: FavouriteLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds repository data to the UI elements.
         *
         * @param repository The favorite repository entity to bind.
         */
        fun bind(repository: FavoriteRepositoryEntity) {
            with(binding) {
                // Load owner avatar image using coil image loading library
                ivOwner.load(repository.owner?.avatarUrl)

                // Bind repository data to UI elements
                repositoryNameView.text = repository.name
                // Set click listener for removing the repository from favorites
                heartImageView.setOnClickListener {
                    // Remove the repository from the list
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        viewModel.removeFavoriteRepository(repository)
                        notifyItemRemoved(position)


                    }
                }
            }
        }
    }

    /**
     * Creates ViewHolder instances for RecyclerView items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create a ViewHolder
        val binding =
            FavouriteLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Binds data to ViewHolder items.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder
        val repository = favoriteRepositories[position]
        holder.bind(repository)
    }

    /**
     * Returns the number of items in the dataset.
     */
    override fun getItemCount(): Int {
        // Return the size of the dataset (number of favorite repositories)
        return favoriteRepositories.size
    }

    /**
     * Updates the list of favorite repositories with new data.
     *
     * @param newList The new list of favorite repositories.
     */
    fun updateList(newList: List<FavoriteRepositoryEntity>) {
        favoriteRepositories.clear()
        favoriteRepositories.addAll(newList)
        notifyDataSetChanged() // Notify the adapter that the dataset has changed
    }

}