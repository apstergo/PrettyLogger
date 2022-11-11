package ru.profsoft.androidtools

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import ru.profsoft.androidtools.model.TestPostModel

interface RetrofitSevice {
    @GET("posts")
    suspend fun getPosts(): Response<TestPostModel>
}