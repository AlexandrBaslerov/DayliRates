package com.example.dailyrates.mapers

import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import com.example.dailyrates.networking.models.DailyExRates


fun mapRatesFromApiToDb(pair: Pair<DailyExRates?, DailyExRates?>): List<DailyRatesWithUserSettings> {
    var list: List<DailyRatesWithUserSettings> = emptyList()
    pair.first!!.currencies.forEachIndexed { index, currency ->
        list += DailyRatesWithUserSettings(
            id = currency.id,
            laterValue = currency.rate,
            earlyValue = pair.second!!.currencies[index].rate,
            charCode = currency.charCode,
            scale = currency.scale,
            numCode = currency.numCode,
            name = currency.name,
            position = index
        )
    }
    return list
}