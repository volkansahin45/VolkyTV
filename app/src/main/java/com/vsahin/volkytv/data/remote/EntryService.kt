package com.vsahin.volkytv.data.remote

import com.vsahin.volkytv.data.model.Entries
import retrofit2.http.GET

interface EntryService {
    companion object {
        const val BASE_URL = "https://vsahin.com/"
    }

    @GET("compose-video.json")
    suspend fun getEntries(): Entries
}