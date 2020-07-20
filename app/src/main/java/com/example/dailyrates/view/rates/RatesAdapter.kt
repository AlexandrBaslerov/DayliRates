package com.example.dailyrates.view.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyrates.R
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
    }

    private val rates = ArrayList<DailyRatesWithUserSettings?>()

    override fun getItemCount() = rates.size

    override fun getItemViewType(position: Int) = ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RatesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as RatesViewHolder).bind(rates[position]!!)

    fun replaceAll(newDailyRates: List<DailyRatesWithUserSettings>) {
        rates.clear()
        rates.addAll(newDailyRates)
        notifyDataSetChanged()
    }

    class RatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rates: DailyRatesWithUserSettings) {
            itemView.apply {
                txt_charCode.text = rates.charCode
                txt_scale_name.text = resources.getString(
                    R.string.currency_scale_name,
                    rates.scale,
                    rates.name
                )
                txt_earlierDateRate.text = rates.earlyValue.toString()
                txt_latterDateRate.text = rates.laterValue.toString()
            }
        }
    }
}
