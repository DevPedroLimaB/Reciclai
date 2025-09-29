package com.example.reciclai.data.local

import androidx.room.*
import com.example.reciclai.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val points: Int,
    val level: Int,
    val totalRecycled: Double,
    val co2Saved: Double,
    val createdAt: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    
    @Query("DELETE FROM users")
    suspend fun clearUsers()
}

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ReciclaiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

class Converters {
    // Add any type converters if needed
}