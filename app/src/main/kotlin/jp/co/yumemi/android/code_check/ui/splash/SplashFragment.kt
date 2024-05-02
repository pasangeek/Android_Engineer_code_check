package jp.co.yumemi.android.code_check.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {
    // Duration of splash screen display
    private val SPLASH_DISPLAY_LENGTH = 5000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Delay for the specified duration before navigating to main content
        lifecycleScope.launch {
            delay(SPLASH_DISPLAY_LENGTH)
            navigateToMainContent()
        }
    }

    /**
     * Navigate to the main content fragment (HomeFragment).
     */
    private fun navigateToMainContent() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}