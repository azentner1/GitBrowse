package com.demo.gitbrowse.koin

import com.demo.gitbrowse.ui.MainActivityViewModel
import com.demo.gitbrowse.data.api.ApiDataSource
import com.demo.gitbrowse.data.api.ApiDataSourceImpl
import com.demo.gitbrowse.data.api.ApiService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    // api
    single<ApiService> { ApiService() }

    // data sources
    single<ApiDataSource> { ApiDataSourceImpl(get()) }

    // view models
    viewModel { MainActivityViewModel(get()) }
}