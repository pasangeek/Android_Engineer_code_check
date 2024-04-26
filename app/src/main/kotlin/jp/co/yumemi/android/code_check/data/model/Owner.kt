package jp.co.yumemi.android.code_check.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
/**
 * Data class representing the owner of a GitHub repository.
 *
 * @property avatarUrl The URL of the owner's avatar image.
 */
data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String
) : Serializable