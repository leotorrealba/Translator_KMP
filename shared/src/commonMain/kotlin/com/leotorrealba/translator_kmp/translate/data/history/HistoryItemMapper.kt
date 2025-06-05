package com.leotorrealba.translator_kmp.translate.data.history

import com.leotorrealba.translator_kmp.translate.domain.history.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem(): HistoryItem{
    return HistoryItem(
        id = id,
        fromText = fromText,
        toText = toText,
        fromLanguageCode = fromLanguageCode,
        toLanguageCode = toLanguageCode
    )
}