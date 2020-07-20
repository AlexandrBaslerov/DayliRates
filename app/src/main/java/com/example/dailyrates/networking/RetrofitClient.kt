package com.example.dailyrates.networking

import com.example.dailyrates.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideRetrofit(get()) }
    factory { provideRatesApi(get()) }
    factory { ResponseHandler() }
}

fun provideRatesApi(retrofit: Retrofit): RatesApi =
    retrofit.create(RatesApi::class.java)

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(BuildConfig.API_URL)
        .client(okHttpClient).addConverterFactory(SimpleXmlConverterFactory.create()).build()


fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()


fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level =
        HttpLoggingInterceptor.Level.BODY
}



