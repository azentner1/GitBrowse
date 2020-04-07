package com.demo.gitbrowse.data.model

import com.google.gson.annotations.SerializedName


data class GitHubToken(@SerializedName("access_token") val accessToken: String, @SerializedName("access_type") val accessType: String)