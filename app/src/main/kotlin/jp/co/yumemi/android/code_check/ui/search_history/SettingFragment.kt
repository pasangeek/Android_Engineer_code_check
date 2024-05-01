package jp.co.yumemi.android.code_check.ui.search_history

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentSettingBinding
import jp.co.yumemi.android.code_check.ui.adapters.SearchHistoryAdapter
/**
 * Fragment to display settings and manage search history.
 */
@AndroidEntryPoint
class SettingFragment : Fragment() {

    // ViewModel for the fragment
    private val viewModel: SettingViewModel by viewModels()
    private var binding: FragmentSettingBinding? = null
    private lateinit var adapter: SearchHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchHistoryAdapter()
        binding?.recyclerView?.adapter = adapter
        Log.d(TAG, "RecyclerView and adapter set up successfully")

        // Observe search history data and update UI
        viewModel.allSearchHistory.observe(viewLifecycleOwner) { searchHistoryList ->
            adapter.submitList(searchHistoryList)
            Log.d(TAG, "Search history data changed: $searchHistoryList")
        }
        // Set up click listener for clear history button
        clearAllHistory()
    }
    /**
     * Set up click listener for the clear history button.
     */
    fun clearAllHistory() {
        binding?.btnHistoryClear?.setOnClickListener {
            viewModel.deleteSearchHistory()
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