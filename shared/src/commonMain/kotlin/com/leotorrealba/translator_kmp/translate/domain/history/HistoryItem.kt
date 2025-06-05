package com.leotorrealba.translator_kmp.translate.domain.history

data class HistoryItem(
    val id: Long?,
    val fromText: String,
    val toText: String,
    val fromLanguageCode: String,
    val toLanguageCode: String
)
