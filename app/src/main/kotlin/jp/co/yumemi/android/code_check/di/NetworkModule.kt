package jp.co.yumemi.android.code_check.di

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.common.ConstantNetworkService.BASE_URL
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.sources.remote.GithubRepositoryApiService
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 * Dagger module providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the Application Context.
     */
    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }
    /**
     * Provides the NetworkConnectivityRepository instance with the application context.
     */
    @Singleton
    @Provides
    fun provideConnectivityRepository(context: Context): ConnectivityRepository {
        return ConnectivityRepository(context)
    }
    /**
     * Provides the base URL used for network requests.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    /**
     * Provides the converter factory used for JSON serialization and deserialization.
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides the HTTP client used for making network requests.
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return okHttpClient.build()
    }

    /**
     * Provides the implementation of the GithubRepositoryApiService interface.
     * It depends on the Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
        logMessage("Retrofit instance created with base URL: $baseUrl")
        return retrofit.build()
    }

    /**
     * Provides the implementation of the GithubRepositoryApiService interface.
     * It depends on the Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideGithubApiService(
        retrofit: Retrofit
    ): GithubRepositoryApiService {
        return retrofit.create(GithubRepositoryApiService::class.java)
    }

    /**
     * Provides the GithubRepository instance.
     * It depends on the GithubRepositoryApiService.
     */
    @Singleton
    @Provides
    fun provideGithubRepository(githubRepositoryApiService: GithubRepositoryApiService): GithubRepository {
        try {
            val githubRepository = GithubRepository(githubRepositoryApiService)
            logMessage("GithubRepository provided")
            return githubRepository
        } catch (e: Exception) {
            logMessage("Error while providing GithubRepository: ${e.message}")
            throw e  // Re-throw the exception for higher-level dialog_fragment handling
        }


    }
    /**
     * Logs a message with a specified tag using the DEBUG log level.
     *
     * @param message The message to log.
     */
    private fun logMessage(message: String) {
        Log.d("NetworkModule", message)
    }

}