package com.example.login_app
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.login_app.ui.theme.nicePink

@Composable
fun ProductsScreen(state: ProductState, onEvent: (ProductEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ProductEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add product"
                )
            }
        },
    ) { padding ->
        if (state.isAddingProduct) {
            AddProductDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // przyciski sortowania
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ProductEvent.SortProducts(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ProductEvent.SortProducts(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.products) { product ->
                // kazdy element listy jest kafelkiem
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(ProductEvent.EditProduct(product))  // Trigger dla edit event
                        }
                        .background(nicePink, shape = RoundedCornerShape(16.dp)) //kolor kafelkow
                        .padding(16.dp)
                ) {
                    // kolumna nazwa
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = product.name,
                            fontSize = 20.sp
                        )
                        Text(
                            text = product.category,
                            fontSize = 12.sp
                        )
                    }

                    // kolumna cena
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .padding(end = 8.dp)
                            .padding(top = 12.dp)
                    ) {4
                        Text(
                            text = "$${product.price}",
                            fontSize = 20.sp
                        )
                    }

                    IconButton(onClick = {
                        onEvent(ProductEvent.DeleteProduct(product))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete product"
                        )
                    }
                }
            }
        }
    }
}