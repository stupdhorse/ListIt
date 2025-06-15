package com.example.login_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(product: Product)
    @Delete
    suspend fun deleteProduct(product: Product)
    @Query("SELECT * FROM products ORDER BY price ASC")
    fun sortProductsByPrice(): Flow<List<Product>>
    @Query("SELECT * FROM products ORDER BY category ASC")
    fun sortProductsByCategory(): Flow<List<Product>>
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun sortProductsByName(): Flow<List<Product>>
}
