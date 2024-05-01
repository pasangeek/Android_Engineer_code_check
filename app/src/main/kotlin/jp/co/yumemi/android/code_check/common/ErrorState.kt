package jp.co.yumemi.android.code_check.common
/**
 * Sealed class representing different states related to errors in the application.
 */
sealed class ErrorState {
    /**
     * Represents an error state with a specific error message.
     *
     * @property message The error message describing the error.
     */
    data class Error(val message: String) : ErrorState()
    data class Prompt(val message: String) : ErrorState()
}
