package com.example.dailyrates.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dailyrates.data.model.DailyRatesWithUserSettings

@Dao
interface RatesDao {

    @Query("SELECT * from rates_table where rate_checked =:value ORDER BY rate_position")
    fun getDailyRatesMainScreen(value: Boolean = true): LiveData<List<DailyRatesWithUserSettings>>

    @Query("SELECT * from rates_table ORDER BY rate_position")
    fun getDailyRatesSettingsScreen(): LiveData<List<DailyRatesWithUserSettings>>

    @Transaction
    suspend fun replaceAllRates(list: List<DailyRatesWithUserSettings>) {
        deleteAll()
        insertAll(list)
    }

    @Insert
    suspend fun insertAll(list: List<DailyRatesWithUserSettings>)

    @Query("DELETE FROM rates_table")
    suspend fun deleteAll()

    @Query("UPDATE rates_table SET rate_early_value = :earlyValue,rate_later_value = :laterValue where rate_id =:id")
    suspend fun update(id: Int, laterValue: Double, earlyValue: Double)

    @Query("UPDATE rates_table SET rate_checked = :checked where rate_char_code =:charCode")
    suspend fun update(charCode: String, checked: Boolean = true)

}