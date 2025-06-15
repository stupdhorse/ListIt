package com.example.login_app

import androidx.room.Room
import com.example.login_app.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(get(), ProductDatabase::class.java, "products.db")
            .build()
    }
    single { get<ProductDatabase>().dao() }
    single { ProductRepository(get()) }
    viewModel { ProductViewModel(get()) }
}