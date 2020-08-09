package com.demo.gitbrowse.koin

import com.demo.gitbrowse.data.SearchRepository
import com.demo.gitbrowse.data.SearchRepositoryImpl
import com.demo.gitbrowse.data.api.ApiDataSource
import com.demo.gitbrowse.data.api.ApiDataSourceImpl
import com.demo.gitbrowse.data.api.ApiService
import com.demo.gitbrowse.ui.detail.RepoDetailViewModel
import com.demo.gitbrowse.ui.main.MainActivityViewModel
import com.demo.gitbrowse.ui.user.UserDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    // api
    single<ApiService> { ApiService() }

    // data sources
    single<ApiDataSource> { ApiDataSourceImpl(get()) }

    // repositories
    single<SearchRepository>{ SearchRepositoryImpl(get()) }

    // view models
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { RepoDetailViewModel() }
    viewModel { UserDetailViewModel() }
}