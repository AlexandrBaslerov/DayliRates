package com.example.dailyrates.view.rates

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyrates.R
import com.example.dailyrates.databinding.ActivityRatesBinding
import com.example.dailyrates.view.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_rates.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatesActivity : AppCompatActivity() {

    private val ratesViewModule: RatesViewModel by viewModel()

    private lateinit var binding: ActivityRatesBinding

    private val currenciesAdapter = RatesAdapter()

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, RatesActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rates)
        binding.viewModel = ratesViewModule
        binding.executePendingBindings()

        binding.rvCurrencies.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            adapter = currenciesAdapter
        }
        ratesViewModule.dailyRates.observe(this, Observer { currenciesAdapter.replaceAll(it) })
        toolbar_settings.setOnClickListener { startActivity(SettingsActivity.newIntent(this)) }
    }


}