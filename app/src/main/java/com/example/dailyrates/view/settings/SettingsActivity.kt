package com.example.dailyrates.view.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyrates.R
import com.example.dailyrates.databinding.ActivitySettingsBinding
import com.example.dailyrates.view.rates.RatesActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private val settingsViewModule: SettingsViewModel by viewModel()
    private lateinit var binding: ActivitySettingsBinding

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }

    private val settingsAdapter = SettingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.settingsViewModel = settingsViewModule
        binding.executePendingBindings()


        rv_rates_settings.apply {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = settingsAdapter
        }
        settingsViewModule.dailyRates.observe(this, Observer { settingsAdapter.addAll(it) })
        settingsAdapter.dragHelper.attachToRecyclerView(rv_rates_settings)

        toolbar_done.setOnClickListener {
            settingsAdapter.rates.forEachIndexed { index, rate ->
                rate.position = index
            }
            settingsViewModule.replaceAllRates(settingsAdapter.rates)
            startActivity(RatesActivity.newIntent(this))
        }
        toolbar_back.setOnClickListener { startActivity(RatesActivity.newIntent(this)) }
    }


}
