package com.example.dailyrates.interactor


import com.example.dailyrates.BuildConfig
import com.example.dailyrates.data.Preferences
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import com.example.dailyrates.networking.Resource
import com.example.dailyrates.networking.ResponseHandler
import com.example.dailyrates.networking.Status
import com.example.dailyrates.networking.models.DailyExRates
import com.example.dailyrates.repository.RatesRepository
import com.example.dailyrates.utils.SimpleCalendar
import org.koin.dsl.module


val interactorModule = module {
    single { RatesInteractor(get(), get(), get(), get()) }
}

class RatesInteractor(
    private val repository: RatesRepository,
    private val simpleCalendar: SimpleCalendar,
    private val responseHandler: ResponseHandler,
    private val prefs: Preferences
) {
    private val MIN_COUNT_DAY = 2;

    suspend fun getRatesForLastTwoAvailableDays(
        listDates: List<String> = simpleCalendar.getDefaultDailyDates()
    ): Resource<Pair<DailyExRates?, DailyExRates?>> {

        val listSuccessRequests: List<Resource<DailyExRates>> =
            listDates.map { repository.getRatesByDay(it) }
                .filter { it.status == Status.SUCCESS }
                .sortedBy { it.data?.date }

        return if (listSuccessRequests.size >= MIN_COUNT_DAY) {
            responseHandler.handleSuccess(
                Pair(
                    //first is laterRates
                    listSuccessRequests[listSuccessRequests.size - 1].data,
                    //second is earlyRates
                    listSuccessRequests[listSuccessRequests.size - 2].data
                )
            )
        } else
            responseHandler.handleException(Exception("Service is unavailable"))
    }


    suspend fun updateDailyRates(rates: List<DailyRatesWithUserSettings>?) {
        if (prefs.isFirstLoading()) {
            prefs.setValueFirstLoading(false)
            rates?.let { repository.replaceAllRates(it) }
            BuildConfig.CHECKED_RATES.forEach { repository.update(it) }
        } else {
            rates?.forEach {
                repository.update(
                    id = it.id,
                    laterValue = it.laterValue,
                    earlyValue = it.earlyValue
                )
            }
        }
    }
}



