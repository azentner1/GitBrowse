package com.demo.gitbrowse.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.gitbrowse.BuildConfig
import com.demo.gitbrowse.data.SearchRepository
import com.demo.gitbrowse.data.api.ApiDataSource
import com.demo.gitbrowse.data.api.response.ReposResponse
import com.demo.nearbyvenues.utils.Constants
import com.demo.nearbyvenues.utils.SharedPrefsUtil

class MainActivityViewModel(
    private val searchRepository: SearchRepository,
    private val apiDataSource: ApiDataSource
) : ViewModel() {

    private var query: String = ""

    var reposResponse = MutableLiveData<ReposResponse>()

    init {
        searchRepository.fetchedRepositories.observeForever {
            reposResponse.postValue(it)
        }
    }

    fun fetchToken(code: String) = liveData {
        emitSource(
            apiDataSource.fetchToken(
                BuildConfig.ClientId,
                BuildConfig.ClientSecret, code
            )
        )
    }

    fun isUserLoggedIn(): Boolean {
        return SharedPrefsUtil.getString(Constants.USER_TOKEN).isNotEmpty()
    }

    fun fetchRepos() {
        searchRepository.fetchRepositories(getUserAccessToken(), query)
    }

    private fun getUserAccessToken(): String {
        return SharedPrefsUtil.getString(Constants.USER_TOKEN)
    }

    fun saveUserToken(accessToken: String) {
        SharedPrefsUtil.setString(Constants.USER_TOKEN, accessToken)
    }

    fun searchQueryChanged(query: String) {
        this.query = query
        fetchRepos()
    }
}