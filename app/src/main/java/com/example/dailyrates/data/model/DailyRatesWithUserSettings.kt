package com.example.dailyrates.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates_table")
class DailyRatesWithUserSettings(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rate_id")
    val id: Int,
    @ColumnInfo(name = "rate_char_code")
    val charCode: String,
    @ColumnInfo(name = "rate_early_value")
    var earlyValue: Double,
    @ColumnInfo(name = "rate_later_value")
    var laterValue: Double,
    @ColumnInfo(name = "rate_scale")
    val scale: Int,
    @ColumnInfo(name = "rate_num_code")
    val numCode: String,
    @ColumnInfo(name = "rate_name")
    val name: String,
    @ColumnInfo(name = "rate_checked")
    var isVisibleForUser: Boolean = false,
    @ColumnInfo(name = "rate_position")
    var position: Int
)