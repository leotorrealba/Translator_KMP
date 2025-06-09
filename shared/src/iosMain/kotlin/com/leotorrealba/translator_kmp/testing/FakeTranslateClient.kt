package com.leotorrealba.translator_kmp.testing

import com.leotorrealba.translator_kmp.core.domain.language.Language
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText = "fake translated text"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}