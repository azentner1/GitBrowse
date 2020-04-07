package com.demo.gitbrowse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.gitbrowse.BuildConfig
import com.demo.gitbrowse.data.api.ApiDataSource
import com.demo.nearbyvenues.utils.Constants
import com.demo.nearbyvenues.utils.SharedPrefsUtil

class MainActivityViewModel(private val apiDataSource: ApiDataSource) : ViewModel() {

    fun fetchToken(code: String) = liveData {
        emitSource(apiDataSource.fetchToken(
            BuildConfig.ClientId,
            BuildConfig.ClientSecret, code))
    }

    fun isUserLoggedIn() : Boolean {
        return SharedPrefsUtil.getString(Constants.USER_TOKEN).isNotEmpty()
    }

    fun fetchRepos(accessToken: String) = liveData {
        emitSource(apiDataSource.fetchRepos(accessToken))
    }

    fun getUserAccessToken(): String {
        return SharedPrefsUtil.getString(Constants.USER_TOKEN)
    }

    fun saveUserToken(accessToken: String) {
        SharedPrefsUtil.setString(Constants.USER_TOKEN, accessToken)
    }

}