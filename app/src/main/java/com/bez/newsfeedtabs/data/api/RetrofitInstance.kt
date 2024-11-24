package com.bez.newsfeedtabs.data.api

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://www.ynet.co.il/"

    val api: NewsService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }
}
