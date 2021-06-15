package com.vsahin.volkytv.data

import android.content.Context
import com.vsahin.volkytv.R
import com.vsahin.volkytv.data.model.Entries
import com.vsahin.volkytv.data.model.Entry
import com.vsahin.volkytv.data.remote.EntryService
import com.vsahin.volkytv.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val service: EntryService
) {
    private var entries: Entries = Entries()

    suspend fun getEntries(): Result<Entries> {
        return withContext(defaultDispatcher) {
            try {
                entries = service.getEntries()
                Result.Success(entries)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getEntry(id: String): Result<Entry> {
        return withContext(defaultDispatcher) {
            try {
                val entry = entries.entries.find { entry -> entry.id == id }
                if (entry == null) {
                    Result.Error(Exception(applicationContext.getString(R.string.item_is_not_available)))
                } else {
                    Result.Success(entry)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}