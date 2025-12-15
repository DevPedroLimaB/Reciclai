package com.example.reciclai.di

import android.content.Context
import androidx.room.Room
import com.example.reciclai.data.local.AppDatabase
import com.example.reciclai.data.local.dao.RecyclingPointDao
import com.example.reciclai.data.local.dao.UserDao
import com.example.reciclai.repository.LocalAuthRepository
import com.example.reciclai.shared.network.ApiService
import com.example.reciclai.shared.repository.*
import com.example.reciclai.shared.storage.StorageService
import com.example.reciclai.storage.AndroidStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // === Banco de Dados Local ===

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "reciclai_database"
        )
            .fallbackToDestructiveMigration() // Em produção, usar migrations
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideRecyclingPointDao(database: AppDatabase): RecyclingPointDao {
        return database.recyclingPointDao()
    }

    // === Storage e API ===

    @Provides
    @Singleton
    fun provideStorageService(@ApplicationContext context: Context): StorageService {
        return AndroidStorageService(context)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiService(baseUrl = "https://api.reciclai.com/")
    }

    // === Repositórios ===

    @Provides
    @Singleton
    fun provideLocalAuthRepository(userDao: UserDao): LocalAuthRepository {
        return LocalAuthRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        storageService: StorageService,
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, storageService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideContentRepository(apiService: ApiService): ContentRepository {
        return ContentRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCollectPointRepository(apiService: ApiService): CollectPointRepository {
        return CollectPointRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRecyclingRepository(
        apiService: ApiService
    ): RecyclingRepository {
        return RecyclingRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRewardRepository(
        apiService: ApiService
    ): RewardRepository {
        return RewardRepositoryImpl(apiService)
    }
}
