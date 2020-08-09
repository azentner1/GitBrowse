package com.demo.gitbrowse.data.api

import com.demo.gitbrowse.BuildConfig
import com.demo.gitbrowse.data.api.response.ReposResponse
import com.demo.gitbrowse.data.model.GitHubToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getToken(@Field("client_id") clientId: String, @Field("client_secret") clientSecret: String,
                 @Field("code") code: String) : Call<GitHubToken>

    @GET("https://api.github.com/search/repositories")
    fun fetchRepos(@Query("token") token: String, @Query("q") query: String, @Query("sort") sort: String, @Query("order") sortOrder: String) : Call<ReposResponse>

    companion object {
        operator fun invoke() : ApiService {
            val interceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()

            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(httpClient).baseUrl(BuildConfig.ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}