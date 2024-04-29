package jp.co.yumemi.android.code_check.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.data.model.Owner

@Entity(tableName = "favorite_repositories")
class FavoriteRepository (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String?,
    val owner: Owner?,
    val language: String?,
    val stargazersCount: String?,
    val watchersCount: String?,
    val forksCount: String?,
    val openIssuesCount: String?,
    var isFavorite: Boolean = false
)