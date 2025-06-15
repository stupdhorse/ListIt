package com.example.login_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine


@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModel(private val repository: ProductRepository): ViewModel()
{
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _products = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.NAME -> repository.sortProductsByName()
                SortType.PRICE -> repository.sortProductsByPrice()
                SortType.CATEGORY -> repository.sortProductsByCategory()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ProductState())
    val state = combine(_state, _sortType, _products) { state, sortType, products ->
        state.copy(
            products = products,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    repository.deleteProduct(event.product)
                }
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(isAddingProduct = false) }
            }
            ProductEvent.SaveProduct -> {
                val name = state.value.name
                val category = state.value.category
                val price = state.value.price

                if(name.isBlank() || price.isBlank() || category.isBlank()) {
                    return
                }

                val product = Product(
                    name = name,
                    price = price,
                    category = category,
                    id = state.value.productId
                )
                viewModelScope.launch {
                    repository.upsertProduct(product)
                }
                //resetuj stany
                _state.update {
                    it.copy(
                        isAddingProduct = false,
                        name = "",
                        price = "",
                        category = "",
                        productId = 0
                    )
                }
            }
            is ProductEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
            is ProductEvent.SetPrice -> {
                _state.update { it.copy(price = event.price) }
            }
            is ProductEvent.SetCategory -> {
                _state.update { it.copy(category = event.category) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(isAddingProduct = true) }
            }
            is ProductEvent.SortProducts -> {
                _sortType.value = event.sortType
            }
            is ProductEvent.EditProduct -> {
                // update stanu dla kliknietego produkru
                _state.update {
                    it.copy(
                        isAddingProduct = true,
                        productId = event.product.id,
                        name = event.product.name,
                        category = event.product.category,
                        price = event.product.price
                    )
                }
            }
        }
    }

}