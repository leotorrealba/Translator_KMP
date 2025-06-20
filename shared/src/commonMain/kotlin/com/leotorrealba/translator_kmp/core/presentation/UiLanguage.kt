package com.leotorrealba.translator_kmp.core.presentation

import com.leotorrealba.translator_kmp.core.domain.language.Language

expect class UiLanguage {
    val language: Language

    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}