package jp.co.yumemi.android.code_check.di.local

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.database.AppDatabase
import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import javax.inject.Singleton

/**
 * Dagger Hilt module responsible for providing instances related to the Room database.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    /**
     * Provides a [SharedPreferences] instance named "UserHistory" using the application context.
     * This instance allows storing and retrieving application-specific preferences persistently.
     *
     * @param context The [Context] obtained from the application.
     * @return The [SharedPreferences] instance for the "YumemiCodingTestApplication" with private mode.
     */
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("UserHistory", Context.MODE_PRIVATE)
    }

    /**
     * Provides the singleton instance of the Room database [AppDatabase].
     *
     * @param context The application context.
     * @return The singleton instance of [AppDatabase].
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the Data Access Object (DAO) for accessing favorite repositories.
     *
     * @param appDatabase The Room database instance.
     * @return The Data Access Object (DAO) for favorite repositories.
     */
    @Provides
    @Singleton
    fun provideFavoriteRepositoryDao(appDatabase: AppDatabase): FavoriteRepositoryDao {
        return appDatabase.favoriteRepositoryDao()
    }

}