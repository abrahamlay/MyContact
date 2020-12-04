package com.abrahamlay.mycontact.module

import com.abrahamlay.domain.AndroidUIThread
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.interactors.AddContact
import com.abrahamlay.domain.interactors.GetContactDetail
import com.abrahamlay.domain.interactors.GetContacts
import org.koin.dsl.module

val useCaseModule = module {
    single<PostExecutionThread> { return@single AndroidUIThread() }
    factory { GetContacts(get(), get()) }
    factory { GetContactDetail(get(), get()) }
    factory { AddContact(get(), get()) }
}