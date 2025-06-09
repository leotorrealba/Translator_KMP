package com.leotorrealba.translator_kmp.translate.data.local

import com.leotorrealba.translator_kmp.core.domain.util.CommonFlow
import com.leotorrealba.translator_kmp.core.domain.util.asCommonFlow
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource: HistoryDataSource  {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.asCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }

    override suspend fun deleteHistoryItemById(id: Long) {
        _data.value = _data.value.filterNot { it.id == id }

    }
}