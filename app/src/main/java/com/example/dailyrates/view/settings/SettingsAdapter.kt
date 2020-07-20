package com.example.dailyrates.view.settings

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyrates.R
import com.example.dailyrates.data.model.DailyRatesWithUserSettings
import kotlinx.android.synthetic.main.item_rate.view.txt_charCode
import kotlinx.android.synthetic.main.item_rate.view.txt_scale_name
import kotlinx.android.synthetic.main.item_rate_setting.view.*
import java.util.*
import kotlin.collections.ArrayList

class SettingsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
    }

    val dragHelper = DragHelper()


    val rates = ArrayList<DailyRatesWithUserSettings>()

    override fun getItemCount() = rates.size

    override fun getItemViewType(position: Int) = ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RatesSettingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rate_setting, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RatesSettingViewHolder).bind(rates[position])
    }

    fun addAll(newRatesSettings: List<DailyRatesWithUserSettings>) {
        rates.addAll(newRatesSettings)
        notifyDataSetChanged()
    }

    fun onItemMove(sourcePosition: Int, targetPosition: Int) {
        Collections.swap(rates, sourcePosition, targetPosition)
        notifyItemMoved(sourcePosition, targetPosition)
    }

    private inner class RatesSettingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.switch_show.setOnCheckedChangeListener { _, isChecked ->
                rates[adapterPosition].isVisibleForUser = isChecked
            }

            itemView.imv_reorder.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragHelper.startDrag(this)
                }
                false
            }
        }

        fun bind(rateSetting: DailyRatesWithUserSettings) {
            itemView.apply {
                txt_charCode.text = rateSetting.charCode
                txt_scale_name.text = resources.getString(
                    R.string.currency_scale_name,
                    rateSetting.scale,
                    rateSetting.name
                )
                switch_show.isChecked = rateSetting.isVisibleForUser
            }
        }
    }


    inner class DragHelper :
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                source: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                onItemMove(source.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
}
