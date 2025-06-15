package com.example.login_app

data class ProductState(
    val products: List<Product> = emptyList(),
    val name : String = "",
    val category : String = "",
    val price : String = "",
    val isAddingProduct:Boolean=false,
    val sortType: SortType = SortType.NAME,
    val productId: Int = 0  // edycja/nowy produkt

)