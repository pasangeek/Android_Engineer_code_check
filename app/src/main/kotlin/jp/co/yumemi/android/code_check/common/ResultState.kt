package jp.co.yumemi.android.code_check.common
/**
 * Sealed class representing different states of a result.
 */
sealed class ResultState {
    // Represents a loading state
    object Loading : ResultState()

    /**
     * Represents a successful result holding the actual value of type R.
     * @param result The result of the operation.
     */
    data class Success<out R>(val result: R) : ResultState()

    /**
     * Represents a failure state with an associated dialog_fragment message.
     * @param error The dialog_fragment message describing the failure.
     */
    data class Failure(val error: String) : ResultState()

}