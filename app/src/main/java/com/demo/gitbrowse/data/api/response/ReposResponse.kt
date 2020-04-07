package com.demo.gitbrowse.data.api.response

import com.demo.gitbrowse.data.model.Repo
import com.google.gson.annotations.SerializedName

data class ReposResponse(@SerializedName("items") val repos: List<Repo>)