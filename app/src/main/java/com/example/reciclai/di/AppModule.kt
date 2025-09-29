package com.example.reciclai.di

import android.content.Context
import androidx.room.Room
import com.example.reciclai.data.local.ReciclaiDatabase
import com.example.reciclai.data.local.UserDao
import com.example.reciclai.network.ReciclaiApiService
import com.example.reciclai.repository.*
import com.example.reciclai.util.Constants
import com.example.reciclai.util.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(preferencesManager: PreferencesManager): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val token = preferencesManager.getAuthToken()
            
            val newRequest = if (token != null && !originalRequest.url.encodedPath.contains("/auth/")) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else {
                originalRequest
            }
            
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideReciclaiApiService(retrofit: Retrofit): ReciclaiApiService {
        return retrofit.create(ReciclaiApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReciclaiDatabase(@ApplicationContext context: Context): ReciclaiDatabase {
        return Room.databaseBuilder(
            context,
            ReciclaiDatabase::class.java,
            "reciclai_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: ReciclaiDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ReciclaiApiService,
        preferencesManager: PreferencesManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, preferencesManager)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ReciclaiApiService,
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(apiService, userDao)
    }

    @Provides
    @Singleton
    fun provideCollectPointRepository(apiService: ReciclaiApiService): CollectPointRepository {
        return CollectPointRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(apiService: ReciclaiApiService): ScheduleRepository {
        return ScheduleRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRewardRepository(apiService: ReciclaiApiService): RewardRepository {
        return RewardRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideContentRepository(apiService: ReciclaiApiService): ContentRepository {
        return ContentRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(apiService: ReciclaiApiService): CommunityRepository {
        return CommunityRepositoryImpl(apiService)
    }
}