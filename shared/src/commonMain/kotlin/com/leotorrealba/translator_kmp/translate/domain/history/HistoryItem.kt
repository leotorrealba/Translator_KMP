package com.leotorrealba.translator_kmp.translate.domain.history

import com.leotorrealba.translator_kmp.core.domain.language.Language

data class HistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguageCode: String,
    val toLanguageCode: String
)
