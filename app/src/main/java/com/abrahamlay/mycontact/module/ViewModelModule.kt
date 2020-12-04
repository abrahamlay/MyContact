package com.abrahamlay.mycontact.module

import com.abrahamlay.core.subscriber.BaseViewModel
import com.abrahamlay.home.viewmodel.DetailViewModel
import com.abrahamlay.home.viewmodel.HomeViewModel
import org.koin.dsl.module
import org.koin.androidx.experimental.dsl.viewModel


val viewModelModule = module {
    viewModel<BaseViewModel>()
    viewModel<HomeViewModel>()
    viewModel<DetailViewModel>()
}