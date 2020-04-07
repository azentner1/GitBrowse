package com.demo.gitbrowse.data.model

import com.google.gson.annotations.SerializedName


data class Repo(val id: Long, val name: String, @SerializedName("stargazers_count") val stars: Int)