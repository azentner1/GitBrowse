package com.demo.gitbrowse.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.gitbrowse.data.api.ApiDataSource
import com.demo.gitbrowse.data.api.response.ReposResponse


class SearchRepositoryImpl(private val apiDataSource: ApiDataSource) : SearchRepository {

    private var reposLiveDataTemp = MutableLiveData<ReposResponse>()

    override val fetchedRepositories: LiveData<ReposResponse>
        get() = reposLiveDataTemp

    init {
        apiDataSource.fetchedRepos.observeForever {
            reposLiveDataTemp.postValue(it)
        }
    }

    override fun fetchRepositories(token: String, query: String?) {
        apiDataSource.fetchRepos(token, buildQuery(query), SORT_BY, SORT_ORDER)
    }

    private fun buildQuery(query: String?) = if (query == null || query.isEmpty() || query.length < 4) {
            DEFAULT_QUERY_PARAMS
        } else {
            query
        }


    companion object {
        private const val DEFAULT_QUERY_PARAMS: String = "kotlin"
        private const val SORT_BY: String = "starts"
        private const val SORT_ORDER: String = "desc"
    }

}