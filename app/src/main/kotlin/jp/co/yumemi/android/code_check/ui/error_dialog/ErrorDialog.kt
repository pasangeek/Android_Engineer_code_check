package jp.co.yumemi.android.code_check.ui.error_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import jp.co.yumemi.android.code_check.databinding.FragmentErrorDialogBinding
import jp.co.yumemi.android.code_check.ui.home.HomeViewModel

/**
 * DialogFragment to display an error message to the user.
 *
 * @param errorMessage The error message to be displayed.
 * @param viewModel The ViewModel associated with the fragment.
 */
class ErrorDialog(private val errorMessage: String, private val viewModel: HomeViewModel) :
    DialogFragment() {

    private var binding: FragmentErrorDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        // Set the error message in the view
        val view = binding!!.root
        // Set the error message in the view
        binding!!.errorMessage.text = errorMessage
        backToHomePageButton()

        return view
    }

    private fun backToHomePageButton() {
        // Attach the click listener to the "close" button
        binding!!.closeButton.setOnClickListener {
            // Return the root view
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null
        // Reset the error state in the ViewModel to null
        viewModel.errorState.value = null
    }

}