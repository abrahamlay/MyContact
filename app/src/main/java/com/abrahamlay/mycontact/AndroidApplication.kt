package com.abrahamlay.mycontact

import android.app.Application
import com.abrahamlay.mycontact.module.apiModule
import com.abrahamlay.mycontact.module.dataModule
import com.abrahamlay.mycontact.module.useCaseModule
import com.abrahamlay.mycontact.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(
                listOf(
                    dataModule,
                    apiModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}