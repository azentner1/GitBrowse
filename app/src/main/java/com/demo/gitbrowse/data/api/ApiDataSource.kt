package com.demo.gitbrowse.data.api

import androidx.lifecycle.LiveData
import com.demo.gitbrowse.data.api.response.ReposResponse
import com.demo.gitbrowse.data.model.GitHubToken


interface ApiDataSource {
    fun fetchToken(clientId: String, clientSecret: String, code: String) : LiveData<GitHubToken>

    val fetchedRepos: LiveData<ReposResponse>
    fun fetchRepos(token: String, query: String, sort: String, sortOrder: String)
}