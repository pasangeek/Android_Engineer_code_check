package jp.co.yumemi.android.code_check.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import jp.co.yumemi.android.code_check.data.model.Owner

class OwnerTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun ownerToString(owner: Owner?): String? {
        return gson.toJson(owner)
    }

    @TypeConverter
    fun stringToOwner(value: String?): Owner? {
        return gson.fromJson(value, Owner::class.java)
    }
}