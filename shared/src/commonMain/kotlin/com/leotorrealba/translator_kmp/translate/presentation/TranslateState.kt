package com.leotorrealba.translator_kmp.translate.presentation

import com.leotorrealba.translator_kmp.core.presentation.UiLanguage
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateError

data class TranslateState(
    val fromText: String = "",
    val  toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("da"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)
