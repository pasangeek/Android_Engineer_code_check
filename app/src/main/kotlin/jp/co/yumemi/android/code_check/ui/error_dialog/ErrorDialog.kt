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
    // ViewBinding for the dialog fragment
    private var binding: FragmentErrorDialogBinding? = null
    /**
     * Called to create the content view for this DialogFragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The Bundle containing the state of the fragment.
     * @return The root View of the inflated layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentErrorDialogBinding.inflate(inflater, container, false).apply {
            binding = this
            errorMessage.text = this@ErrorDialog.errorMessage // Set the error message
            setupCloseButton() // Set up the "close" button click listener
        }.root
    }
    /**
     * Sets up the click listener for the "close" button.
     */
    private fun setupCloseButton() {
        // Attach the click listener to the "close" button
        binding!!.closeButton.setOnClickListener {
            // Return the root view
            dismiss()
        }
    }
    /**
     * Called when the view hierarchy associated with the fragment is about to be destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null
        // Reset the error state in the ViewModel to null
        viewModel.errorState.value = null
    }

}