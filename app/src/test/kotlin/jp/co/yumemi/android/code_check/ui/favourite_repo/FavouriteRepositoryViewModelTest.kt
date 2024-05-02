package jp.co.yumemi.android.code_check.ui.favourite_repo

import jp.co.yumemi.android.code_check.data.database.ApplicationRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.data.model.Owner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteRepositoryViewModelTest {
    // Mocking the ApplicationRepositoryDao
    @Mock
    private lateinit var applicationRepositoryDao: ApplicationRepositoryDao
    /**
     * Test case to verify the insertion of a single FavoriteRepositoryEntity into the database.
     */
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
        applicationRepositoryDao.insert(favoriteRepository)

        // Verify that the insert operation was successful
        Mockito.verify(applicationRepositoryDao).insert(favoriteRepository)
    }
    /**
     * Test case to verify the insertion of multiple hardcoded FavoriteRepositoryEntity objects into the database.
     */
    @Test
    fun testInsertHardcodedData() = runBlocking {
        // Create a sample list of FavoriteRepository objects with hardcoded data
        val hardcodedData = listOf(
            FavoriteRepositoryEntity(
                id = 1,
                name = "Test Repository 1",
                owner = Owner("owner_avatar_url_1"),
                language = "Kotlin",
                stargazersCount = "100",
                watchersCount = "50",
                forksCount = "20",
                openIssuesCount = "10"
            ),
            FavoriteRepositoryEntity(
                id = 2,
                name = "Test Repository 2",
                owner = Owner("owner_avatar_url_2"),
                language = "Java",
                stargazersCount = "200",
                watchersCount = "80",
                forksCount = "30",
                openIssuesCount = "15"
            )
            // Add more hardcoded data as needed
        )

        // Insert the hardcoded data into the database
        hardcodedData.forEach { repository ->
            applicationRepositoryDao.insert(repository)
        }

        // Verify that the insert operation was successful for each item
        hardcodedData.forEach { repository ->
            Mockito.verify(applicationRepositoryDao).insert(repository)
        }
    }
}