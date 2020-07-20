package com.example.dailyrates.app

import android.app.Application
import com.example.dailyrates.data.roomModule
import com.example.dailyrates.data.prefModule
import com.example.dailyrates.interactor.interactorModule
import com.example.dailyrates.networking.networkModule
import com.example.dailyrates.repository.repositoryModule
import com.example.dailyrates.utils.simpleCalendarModule
import com.example.dailyrates.view.settings.viewSettingsModule
import com.example.dailyrates.view.rates.viewRatesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RatesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RatesApp)
            modules(
                listOf(
                    networkModule,
                    viewRatesModule,
                    viewSettingsModule,
                    repositoryModule,
                    roomModule,
                    interactorModule,
                    simpleCalendarModule,
                    prefModule
                )
            )
        }
    }
}