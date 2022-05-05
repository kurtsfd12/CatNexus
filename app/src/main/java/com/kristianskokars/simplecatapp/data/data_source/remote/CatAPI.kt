package com.kristianskokars.simplecatapp.data.data_source.remote

import retrofit2.http.GET

interface CatAPI {
    @GET("images/search?limit=20")
    suspend fun getCats(): List<CatAPIModel>
}
