package com.example.login_app

sealed interface ProductEvent  {
    object SaveProduct: ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetCategory(val category: String) : ProductEvent
    data class SetPrice(val price: String) : ProductEvent
    object ShowDialog: ProductEvent
    object HideDialog: ProductEvent
    data class SortProducts(val sortType:SortType):ProductEvent
    data class DeleteProduct(val product: Product):ProductEvent
    data class EditProduct(val product: Product): ProductEvent
}