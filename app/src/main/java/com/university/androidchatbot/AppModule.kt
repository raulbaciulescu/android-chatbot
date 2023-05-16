package com.university.androidchatbot

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.university.androidchatbot.repository.AuthRepository
import com.university.androidchatbot.api.AuthApi
import com.university.androidchatbot.api.AuthDataSource
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.repository.UserPreferencesRepository
import com.university.androidchatbot.api.MessageApi
import com.university.androidchatbot.api.MessageDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

//const val IP = "192.168.0.129"
const val IP = "192.168.98.154"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient, gsonFactory: GsonConverterFactory): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://$IP:8080/")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory
    ): MessageApi {
        return Retrofit.Builder()
            .baseUrl("http://$IP:8080/")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(authService: AuthApi): AuthDataSource {
        return AuthDataSource(authService)
    }

    @Provides
    @Singleton
    fun provideMessageDataSource(messageApi: MessageApi): MessageDataSource {
        return MessageDataSource(messageApi)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource,
        tokenInterceptor: TokenInterceptor
    ): AuthRepository {
        return AuthRepository(authDataSource, tokenInterceptor)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(app: Application): UserPreferencesRepository {
        return UserPreferencesRepository(app.applicationContext.userPreferencesDataStore)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply {
                this.addInterceptor(tokenInterceptor)
            }.build()
    }
}