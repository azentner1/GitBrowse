package com.demo.gitbrowse.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.demo.gitbrowse.data.api.response.ReposResponse
import com.demo.gitbrowse.data.model.GitHubToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class ApiDataSourceImpl(private val apiService: ApiService) : ApiDataSource {

    override fun fetchToken(clientId: String, clientSecret: String, code: String) = liveData {
        emit(suspendCoroutine<GitHubToken> {
            apiService.getToken(clientId, clientSecret, code).enqueue(object : Callback<GitHubToken> {
            override fun onResponse(call: Call<GitHubToken>, response: Response<GitHubToken>) {
                if (response.body() != null && response.isSuccessful) {
                    it.resume(response.body()!!)
                } else {
                    it.resumeWithException(IllegalStateException("Response error"))
                }
            }

            override fun onFailure(call: Call<GitHubToken>, t: Throwable) {
                it.resumeWithException(t)
            }
        })})
    }

    override val fetchedRepos: LiveData<ReposResponse>
        get() = fetchedReposTemp

    private var fetchedReposTemp = MutableLiveData<ReposResponse>()

    override fun fetchRepos(token: String, query: String, sort: String, sortOrder: String) {
            apiService.fetchRepos(token, query, sort, sortOrder).enqueue(object : Callback<ReposResponse> {
            override fun onResponse(call: Call<ReposResponse>, response: Response<ReposResponse>) {
                if (response.body() != null && response.isSuccessful) {
                    fetchedReposTemp.postValue(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<ReposResponse>, t: Throwable) {

            }
        })
    }

}