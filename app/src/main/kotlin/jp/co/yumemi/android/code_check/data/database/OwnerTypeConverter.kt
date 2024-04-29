package jp.co.yumemi.android.code_check.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import jp.co.yumemi.android.code_check.data.model.Owner
/**
 * Type converter class for converting Owner objects to and from JSON strings.
 * This class is used by Room database to persist Owner objects.
 */
class OwnerTypeConverter {
    // Gson instance for JSON serialization/deserialization
    private val gson = Gson()
    /**
     * Converts an Owner object to its JSON representation.
     *
     * @param owner The Owner object to convert.
     * @return The JSON string representation of the Owner object.
     */
    @TypeConverter
    fun ownerToString(owner: Owner?): String? {
        return gson.toJson(owner)
    }
    /**
     * Converts a JSON string to an Owner object.
     *
     * @param value The JSON string to convert.
     * @return The Owner object parsed from the JSON string.
     */
    @TypeConverter
    fun stringToOwner(value: String?): Owner? {
        return gson.fromJson(value, Owner::class.java)
    }
}