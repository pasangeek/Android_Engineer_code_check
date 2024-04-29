package jp.co.yumemi.android.code_check.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepository



@Database(entities = [FavoriteRepository::class],
    version = 1,
    exportSchema = false
)


