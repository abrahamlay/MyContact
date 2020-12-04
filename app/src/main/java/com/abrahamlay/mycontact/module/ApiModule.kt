package com.abrahamlay.mycontact.module

import com.abrahamlay.core.provider.WebApiProvider
import com.abrahamlay.data.apis.ContactApi
import com.abrahamlay.mycontact.BuildConfig
import org.koin.dsl.module


val apiModule = module {
    single { WebApiProvider.apply { accessToken = BuildConfig.ACCESS_TOKEN } }
    single {
        get<WebApiProvider>()
            .getRetrofit(BuildConfig.API_BASE_URL)
            .create(ContactApi::class.java)
    }
}