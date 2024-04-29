package jp.co.yumemi.android.code_check.ui.favourite_repo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yumemi.android.code_check.R

class FavouriteRepositoryFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteRepositoryFragment()
    }

    private lateinit var viewModel: FavouriteRepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_repository, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouriteRepositoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}