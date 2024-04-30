package jp.co.yumemi.android.code_check.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.data.model.Owner
/**
 * Entity class representing a favorite repository.
 *
 * @param id The unique identifier of the repository (auto-generated).
 * @param name The name of the repository.
 * @param owner The owner of the repository.
 * @param language The primary language of the repository.
 * @param stargazersCount The number of stars (stargazers) the repository has.
 * @param watchersCount The number of watchers the repository has.
 * @param forksCount The number of forks the repository has.
 * @param openIssuesCount The number of open issues the repository has.
 * @param isFavorite Indicates whether the repository is marked as favorite.
 */
@Entity(tableName = "favorite_repositories")
class FavoriteRepositoryEntity (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name: String?,
    val owner: Owner?,
    val language: String?,
    val stargazersCount: String?,
    val watchersCount: String?,
    val forksCount: String?,
    val openIssuesCount: String?,
    var isFavorite: Boolean = false
)