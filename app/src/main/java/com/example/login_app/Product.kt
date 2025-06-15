package com.example.login_app

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "products")
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val price: String
)