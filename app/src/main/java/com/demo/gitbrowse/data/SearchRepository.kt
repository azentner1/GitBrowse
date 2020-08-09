package com.demo.gitbrowse.data

import androidx.lifecycle.LiveData
import com.demo.gitbrowse.data.api.response.ReposResponse


interface SearchRepository {

    val fetchedRepositories: LiveData<ReposResponse>
    fun fetchRepositories(token: String, query: String?)
}