package com.example.reciclai.data.local.dao

import androidx.room.*
import com.example.reciclai.data.local.entity.RecyclingPointEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operações de pontos de reciclagem no banco de dados
 */
@Dao
interface RecyclingPointDao {

    @Query("SELECT * FROM recycling_points ORDER BY name ASC")
    fun getAllPointsFlow(): Flow<List<RecyclingPointEntity>>

    @Query("SELECT * FROM recycling_points ORDER BY name ASC")
    suspend fun getAllPoints(): List<RecyclingPointEntity>

    @Query("SELECT * FROM recycling_points WHERE id = :pointId")
    suspend fun getPointById(pointId: String): RecyclingPointEntity?

    @Query("""
        SELECT * FROM recycling_points 
        WHERE acceptedMaterials LIKE '%' || :material || '%'
        ORDER BY name ASC
    """)
    suspend fun getPointsByMaterial(material: String): List<RecyclingPointEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: RecyclingPointEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoints(points: List<RecyclingPointEntity>)

    @Update
    suspend fun updatePoint(point: RecyclingPointEntity)

    @Query("DELETE FROM recycling_points WHERE id = :pointId")
    suspend fun deletePoint(pointId: String)

    @Query("DELETE FROM recycling_points")
    suspend fun deleteAllPoints()

    @Query("DELETE FROM recycling_points WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)
}

