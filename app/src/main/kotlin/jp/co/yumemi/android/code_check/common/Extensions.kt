package jp.co.yumemi.android.code_check.common

import android.view.View

/**
* Sets the visibility of a View to VISIBLE.
*/
fun View.show() {
    visibility = View.VISIBLE
}
/**
 * Sets the visibility of a View to GONE.
 */
fun View.gone() {
    visibility = View.GONE
}
/**
 * Sets the visibility of a View to INVISIBLE.
 */
fun View.hide() {
    visibility = View.INVISIBLE
}
/**
 * Enables interaction with a View by setting its isEnabled property to true.
 */

fun View.enable() {
    isEnabled = true
}
/**
 * Disables interaction with a View by setting its isEnabled property to false.
 */
fun View.disable() {
    isEnabled = false
}

