package com.example.dailyrates.repository

import androidx.lifecycle.LiveData
import com.example.dailyrates.data.RatesDao
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import com.example.dailyrates.networking.RatesApi
import com.example.dailyrates.networking.Resource
import com.example.dailyrates.networking.ResponseHandler
import com.example.dailyrates.networking.models.DailyExRates
import org.koin.dsl.module

val repositoryModule = module {
    factory { RatesRepository(get(), get(), get()) }
}

class RatesRepository(
    private val ratesApi: RatesApi,
    private val responseHandler: ResponseHandler,
    private val ratesDao: RatesDao
) {
    suspend fun getRatesByDay(date: String): Resource<DailyExRates> {
        return try {
            val response = ratesApi.getExchangeRates(date)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun replaceAllRates(list: List<DailyRatesWithUserSettings>) {
        ratesDao.replaceAllRates(list)
    }

    fun getDailyRates(): LiveData<List<DailyRatesWithUserSettings>> {
        return ratesDao.getDailyRatesMainScreen()
    }

    fun getDailyRatesSettingsScreen(): LiveData<List<DailyRatesWithUserSettings>> {
        return ratesDao.getDailyRatesSettingsScreen()
    }


    suspend fun update(id: Int, laterValue: Double, earlyValue: Double) {
        ratesDao.update(id, laterValue, earlyValue)
    }

    suspend fun update(charCode: String) {
        ratesDao.update(charCode)
    }
}
