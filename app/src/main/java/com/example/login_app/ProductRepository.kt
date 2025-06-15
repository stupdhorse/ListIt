package com.example.login_app

import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {
    suspend fun upsertProduct(product: Product) = dao.upsertProduct(product)
    suspend fun deleteProduct(product: Product) = dao.deleteProduct(product)
    fun sortProductsByName() = dao.sortProductsByName()
    fun sortProductsByPrice() = dao.sortProductsByPrice()
    fun sortProductsByCategory() = dao.sortProductsByCategory()
}
