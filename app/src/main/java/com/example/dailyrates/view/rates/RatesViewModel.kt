package com.example.dailyrates.view.rates


import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import com.example.dailyrates.interactor.RatesInteractor
import com.example.dailyrates.mapers.mapRatesFromApiToDb
import com.example.dailyrates.networking.Status
import com.example.dailyrates.repository.RatesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.dsl.module

val viewRatesModule = module {
    factory {
        RatesViewModel(
            get(),
            get()
        )
    }
}

class RatesViewModel(
    repository: RatesRepository,
    private val interactor: RatesInteractor
) :
    ViewModel() {
    private val viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.IO + viewModelJob)

    val status = ObservableField<Status>()
    val lateData = ObservableField<String>()
    val earlyData = ObservableField<String>()

    val dailyRates: LiveData<List<DailyRatesWithUserSettings>> =
        repository.getDailyRates()


    init {

        scope.launch {
            status.set(Status.LOADING)
            val rates = interactor.getRatesForLastTwoAvailableDays()
            status.set(rates.status)
            interactor.updateDailyRates(rates.data?.let { mapRatesFromApiToDb(it) })
            lateData.set(rates.data?.first?.data)
            earlyData.set(rates.data?.second?.data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}