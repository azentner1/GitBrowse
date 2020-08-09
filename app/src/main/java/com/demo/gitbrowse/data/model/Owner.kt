package com.demo.gitbrowse.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Owner(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val url: String
) : Serializable