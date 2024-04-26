package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding

class GithubRepositoryDetailAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepositoryData, GithubRepositoryDetailAdapter.ViewHolder>(diff_util) {

    /**
     * Called when an item in the adapter is clicked.
     *
     * @param item The clicked GithubRepositoryData item.
     */
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
        /**
         * ViewHolder class for the adapter. Represents an item view in the RecyclerView.
         *
         * @param binding The ViewBinding object for the item layout.
         */
        init {
            // Set a click listener for the item view
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.itemClick(getItem(position))
                }
            }
        }

        fun bind(repo: GithubRepositoryData) {
            with(binding) {
                ivOwner.load(repo.owner?.avatarUrl)
                repositoryNameView.text = repo.name
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