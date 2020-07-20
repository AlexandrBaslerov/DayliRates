package com.example.dailyrates.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidApplication(), RatesRoomDataBase::class.java, "rates_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<RatesRoomDataBase>().rateDAO() }
}

@Database(entities = [DailyRatesWithUserSettings::class], version = 1, exportSchema = false)
abstract class RatesRoomDataBase : RoomDatabase() {
    abstract fun rateDAO(): RatesDao
}

