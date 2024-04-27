package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import jp.co.yumemi.android.code_check.sources.GithubRepositoryApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Represents a repository for accessing GitHub-related data.
 *
 * @param githubRepositoryApiService The API service used for making GitHub API requests.
 */
open class GithubRepository @Inject constructor(private val githubRepositoryApiService: GithubRepositoryApiService) {

    /**
     * Retrieves GitHub account data from the data source based on the provided query.
     *
     * @param searchQuery The repository_search query for repositories.
     * @return The flow emitting GitHub server data or a network error.
     */
    fun getGitHubAccountFromDataSource(searchQuery: String): Flow<GithubServerResponse> = flow {
        val response = getResponseFromRemoteService(searchQuery)
        if (response != null) {
            emit(response)
        } else {
            throw Exception("Network error occurred")
        }
    }.catch { e ->
        logMessage("Error during data retrieval: ${e.message}")
        // Emit null or a default value as per your requirement

    }.flowOn(Dispatchers.IO)

    /**
     * Retrieves the response from the remote GitHub API service based on the provided query.
     *
     * @param responseQuery The repository_search query for repositories.
     * @return The GitHub server response or null if a network error occurred.
     */
    private suspend fun getResponseFromRemoteService(responseQuery: String): GithubServerResponse? {
        return try {
            val response = githubRepositoryApiService.getRepositories(responseQuery)
            if (response.isSuccessful) {
                response.body()
            } else {
                logMessage("GitHub API request failed with code: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            logMessage("Error during API request: ${e.message}")
            null
        }
    }

    private fun logMessage(message: String) {
        Log.d("GithubRepository", message)
    }
}