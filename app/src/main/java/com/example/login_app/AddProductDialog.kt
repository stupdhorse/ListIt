package com.example.login_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onEvent(ProductEvent.HideDialog) },
        confirmButton = {
            Button(onClick = {
                onEvent(ProductEvent.SaveProduct)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = {
                onEvent(ProductEvent.HideDialog)
            }) {
                Text("Cancel")
            }
        },
        text = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // wyswietl "Add product" lub "Edit product" zaleznie od stanu
                Text(text = if (state.productId == 0) "Add product" else "Edit product")

                // pole dodawania
                TextField(
                    value = state.name,
                    onValueChange = { onEvent(ProductEvent.SetName(it)) },
                    placeholder = { Text(text = "Product name") }
                )

                // pole dodawania produktu
                TextField(
                    value = state.price,
                    onValueChange = { onEvent(ProductEvent.SetPrice(it)) },
                    placeholder = { Text(text = "Product price") }
                )

                // kategoria produktu
                TextField(
                    value = state.category,
                    onValueChange = { onEvent(ProductEvent.SetCategory(it)) },
                    placeholder = { Text(text = "Product category") }
                )
            }
        }
    )
}
