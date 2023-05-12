package com.university.androidchatbot

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.university.androidchatbot.auth.data.AuthRepository
import com.university.androidchatbot.auth.data.remote.AuthApi
import com.university.androidchatbot.auth.data.remote.AuthDataSource
import com.university.androidchatbot.core.data.Api
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.core.data.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthService(okHttpClient: OkHttpClient, gsonFactory: GsonConverterFactory): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.98.154:8080/")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(authService: AuthApi): AuthDataSource {
        return AuthDataSource(authService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepository(authDataSource)
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
        return OkHttpClient.Builder().apply {
            this.addInterceptor(tokenInterceptor)
        }.build()
    }
}