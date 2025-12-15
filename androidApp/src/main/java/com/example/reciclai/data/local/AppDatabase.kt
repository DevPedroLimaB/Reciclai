package com.example.reciclai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.reciclai.data.local.dao.RecyclingPointDao
import com.example.reciclai.data.local.dao.UserDao
import com.example.reciclai.data.local.entity.RecyclingPointEntity
import com.example.reciclai.data.local.entity.UserEntity

/**
 * Banco de dados local usando Room Database
 *
 * Armazena:
 * - Dados de usuários (login persistente)
 * - Pontos de reciclagem (cache local)
 */
@Database(
    entities = [
        UserEntity::class,
        RecyclingPointEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun recyclingPointDao(): RecyclingPointDao
}

