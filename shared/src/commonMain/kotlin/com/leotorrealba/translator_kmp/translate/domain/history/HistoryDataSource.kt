package com.leotorrealba.translator_kmp.translate.domain.history

import com.leotorrealba.translator_kmp.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
    suspend fun deleteHistoryItemById(id: Long)
}