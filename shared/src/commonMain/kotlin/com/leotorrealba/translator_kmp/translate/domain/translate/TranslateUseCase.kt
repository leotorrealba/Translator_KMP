package com.leotorrealba.translator_kmp.translate.domain.translate

import com.leotorrealba.translator_kmp.core.domain.language.Language
import com.leotorrealba.translator_kmp.core.domain.util.Resource
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {
    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage, fromText, fromLanguage
            )
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )
            Resource.Success(translatedText)
        }catch (e: TranslateException){
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}