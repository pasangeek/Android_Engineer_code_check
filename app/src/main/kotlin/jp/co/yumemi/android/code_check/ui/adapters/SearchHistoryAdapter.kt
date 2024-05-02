package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.databinding.SearchHistoryItemBinding
/**
 * Adapter for the RecyclerView displaying search history items.
 *
 * @param diffCallback The callback for calculating the diff between two non-null items in a list.
 */
class SearchHistoryAdapter : ListAdapter<SearchHistory, SearchHistoryViewHolder>(SearchHistoryDiffCallback()) {
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchHistoryItemBinding.inflate(inflater, parent, false)
        return SearchHistoryViewHolder(binding)
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistory = getItem(position)
        holder.bind(searchHistory)
    }
}
/**
 * ViewHolder for displaying individual search history items.
 *
 * @param binding The ViewBinding instance for the item layout.
 */
class SearchHistoryViewHolder(private val binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds data to the views within the ViewHolder.
     *
     * @param searchHistory The search history item to bind.
     */
    fun bind(searchHistory: SearchHistory) {
        binding.searchQuery.text = searchHistory.query
        // Bind other properties if needed
        binding.searchTime.text= searchHistory.timestamp.toString()

    }
}
/**
 * Called to check whether two objects represent the same item.
 *
 * @param oldItem The old item to compare.
 * @param newItem The new item to compare.
 * @return True if the items have the same identifier, false otherwise.
 */
class SearchHistoryDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
    override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem.id == newItem.id
    }
    /**
     * Called to check whether two items have the same data.
     *
     * @param oldItem The old item to compare.
     * @param newItem The new item to compare.
     * @return True if the items have the same content, false otherwise.
     */
    override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem == newItem
    }
}