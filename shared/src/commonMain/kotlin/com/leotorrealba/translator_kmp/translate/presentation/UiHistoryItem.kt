package com.leotorrealba.translator_kmp.translate.presentation

import com.leotorrealba.translator_kmp.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)
