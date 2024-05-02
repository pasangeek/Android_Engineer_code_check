package jp.co.yumemi.android.code_check.repository

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Repository class responsible for monitoring network connectivity changes.
 * It utilizes Android's ConnectivityManager to observe network availability.
 *
 * @property context The application context used to access system services.
 * @property connectivityManager The ConnectivityManager instance to manage network connectivity.
 * @property _isConnected MutableStateFlow representing the current network connectivity status.
 * @property isConnected Flow representing the current network connectivity status as an observable stream.
 */
open class ConnectivityRepository @Inject constructor(@ApplicationContext context: Context) {
    // Connectivity manager to access network state
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Mutable state flow to emit connectivity status
    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected

    init {
        /**
         * Called when a network connection is established.
         *
         * @param network The newly connected network.
         */
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _isConnected.value = true
            }

            /**
             * Called when a network connection is lost.
             *
             * @param network The network that was lost.
             */
            override fun onLost(network: android.net.Network) {
                _isConnected.value = false
            }
        })
    }
}