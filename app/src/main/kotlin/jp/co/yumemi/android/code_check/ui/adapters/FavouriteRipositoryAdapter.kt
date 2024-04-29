package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.databinding.FavouriteLayoutItemBinding

class FavouriteRepositoryAdapter(private val favoriteRepositories: MutableList<FavoriteRepositoryEntity>) :
    RecyclerView.Adapter<FavouriteRepositoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FavouriteLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: FavoriteRepositoryEntity) {
            with(binding) {
                // Bind repository data to UI elements
                repositoryNameView.text = repository.name
                // Bind other data fields as needed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create a ViewHolder
        val binding = FavouriteLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder
        val repository = favoriteRepositories[position]
        holder.bind(repository)
    }

    override fun getItemCount(): Int {
        // Return the size of the dataset (number of favorite repositories)
        return favoriteRepositories.size
    }

    // Function to update the list of favorite repositories
    fun updateList(newList: List<FavoriteRepositoryEntity>) {
        favoriteRepositories.clear()
        favoriteRepositories.addAll(newList)
        notifyDataSetChanged() // Notify the adapter that the dataset has changed
    }
}