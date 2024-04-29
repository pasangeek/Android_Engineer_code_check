package jp.co.yumemi.android.code_check.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
/**
 * Room database class for the application.
 * This class defines the database configuration and provides access to DAOs.
 */
@Database(entities = [FavoriteRepositoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OwnerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to the DAO for interacting with the favorite repositories table.
     *
     * @return The DAO for favorite repositories.
     */
    abstract fun favoriteRepositoryDao(): FavoriteRepositoryDao

}

