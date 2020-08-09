package com.demo.gitbrowse.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Repo(
    val id: Long, val name: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("watchers_count") val watchers: Int,
    @SerializedName("open_issues_count") val issues: Int,
    val owner: Owner,
    val private: Boolean,
    val language: String,
    @SerializedName("html_url") val url: String
) : Serializable