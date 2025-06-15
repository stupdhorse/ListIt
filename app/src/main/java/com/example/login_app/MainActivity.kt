package com.example.login_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.login_app.ui.theme.Login_AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // Wstrzyknięcie ViewModelu za pomocą Koin
    private val viewModel: ProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login_AppTheme {
                val state by viewModel.state.collectAsState()
                ProductsScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
