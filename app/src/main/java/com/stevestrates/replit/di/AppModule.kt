package com.stevestrates.replit.di

import com.stevestrates.replit.apis.IReplitApi
import com.stevestrates.replit.apis.ReplitApi
import com.stevestrates.replit.apis.ReplitApiService
import com.stevestrates.replit.features.replit.ReplitRepository
import com.stevestrates.replit.features.replit.ReplitViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    single<ReplitApiService> {
        Retrofit.Builder()
            .baseUrl("https://eval-backend.stevestrates.repl.co")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReplitApiService::class.java)
    }

    factory<IReplitApi> { ReplitApi(get()) }

    single { ReplitRepository(get()) }

    single { ReplitViewModel(get()) }
}