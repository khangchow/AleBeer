package com.chow.alebeer.di

import com.chow.alebeer.ui.main_screen.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MainRepository(get(), get()) }
}