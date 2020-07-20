package com.example.dailyrates.networking

import com.example.dailyrates.networking.models.DailyExRates
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {
    @GET("XmlExRates.aspx")
    suspend fun getExchangeRates(@Query("ondate") date: String? = null): DailyExRates
}
