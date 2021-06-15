package com.vsahin.volkytv.di

import com.vsahin.volkytv.data.remote.EntryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideEntryService(): EntryService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(EntryService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(EntryService::class.java)
    }
}