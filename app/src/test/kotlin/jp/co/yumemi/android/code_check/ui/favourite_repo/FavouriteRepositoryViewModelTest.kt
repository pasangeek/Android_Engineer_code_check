package jp.co.yumemi.android.code_check.ui.favourite_repo

import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.data.model.Owner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteRepositoryViewModelTest {

    @Mock
    private lateinit var favoriteRepositoryDao: FavoriteRepositoryDao
    @Test
    fun testInsertFavoriteRepository() = runBlocking {
        // Create a sample FavoriteRepository object
        val favoriteRepository = FavoriteRepositoryEntity(
            id = 1,
            name = "Test Repository",
            owner = Owner("owner_avatar_url"),
            language = "Kotlin",
            stargazersCount = "100",
            watchersCount = "50",
            forksCount = "20",
            openIssuesCount = "10"
        )

        // Insert the sample FavoriteRepository into the database
        favoriteRepositoryDao.insert(favoriteRepository)

        // Verify that the insert operation was successful
        Mockito.verify(favoriteRepositoryDao).insert(favoriteRepository)
    }
}