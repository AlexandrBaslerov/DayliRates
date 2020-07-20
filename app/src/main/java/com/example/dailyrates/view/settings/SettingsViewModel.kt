package com.example.dailyrates.view.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import com.example.dailyrates.repository.RatesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.dsl.module

val viewSettingsModule = module {
    factory { SettingsViewModel(get()) }
}

class SettingsViewModel(
    private val repository: RatesRepository
) :
    ViewModel() {
    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Unconfined + viewModelJob)

    val dailyRates: LiveData<List<DailyRatesWithUserSettings>> =
        repository.getDailyRatesSettingsScreen()

    fun replaceAllRates(newListRates: List<DailyRatesWithUserSettings>) {
        scope.launch {
            repository.replaceAllRates(newListRates)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}