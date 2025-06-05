package com.leotorrealba.translator_kmp.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.leotorrealba.translator_kmp.core.domain.util.CommonFlow
import com.leotorrealba.translator_kmp.core.domain.util.asCommonFlow
import com.leotorrealba.translator_kmp.database.TranslateDatabase
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
): HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .asCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            toLanguageCode = item.toLanguageCode,
            fromLanguageCode = item.fromLanguageCode,
            toText = item.toText,
            fromText = item.fromText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteHistoryItemById(id: Long) {
        queries.deleteHistoryEntityById(id)
    }

}