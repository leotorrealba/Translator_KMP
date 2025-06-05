package com.leotorrealba.translator_kmp.translate.domain.translate

import com.leotorrealba.translator_kmp.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String


}