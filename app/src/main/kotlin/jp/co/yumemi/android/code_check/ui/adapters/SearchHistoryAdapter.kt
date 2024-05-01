package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.databinding.SearchHistoryItemBinding

class SearchHistoryAdapter : ListAdapter<SearchHistory, SearchHistoryViewHolder>(SearchHistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchHistoryItemBinding.inflate(inflater, parent, false)
        return SearchHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistory = getItem(position)
        holder.bind(searchHistory)
    }
}

class SearchHistoryViewHolder(private val binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(searchHistory: SearchHistory) {
        binding.searchHistory.text = searchHistory.query
        // Bind other properties if needed
        binding.searchHistory.text= searchHistory.timestamp.toString()

    }
}

class SearchHistoryDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
    override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
        return oldItem == newItem
    }
}