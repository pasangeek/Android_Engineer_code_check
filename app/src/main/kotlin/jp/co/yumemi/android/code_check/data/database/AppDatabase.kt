package jp.co.yumemi.android.code_check.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity



@Database(entities = [FavoriteRepositoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OwnerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRepositoryDao(): FavoriteRepositoryDao

}

