package com.abrahamlay.mycontact.module

import com.abrahamlay.data.repository.ContactRepository
import com.abrahamlay.domain.repoimpl.ContactRepositoryImpl
import org.koin.dsl.module


val dataModule = module {
    single<ContactRepository> {
        ContactRepositoryImpl(
            get()
        )
    }
}