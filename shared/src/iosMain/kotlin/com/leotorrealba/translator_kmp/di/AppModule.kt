package com.leotorrealba.translator_kmp.di

import com.leotorrealba.translator_kmp.database.TranslateDatabase
import com.leotorrealba.translator_kmp.translate.data.history.SqlDelightHistoryDataSource
import com.leotorrealba.translator_kmp.translate.data.local.DatabaseDriverFactory
import com.leotorrealba.translator_kmp.translate.data.remote.HttpClientFactory
import com.leotorrealba.translator_kmp.translate.data.translate.KtorTranslateClient
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateClient
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParser

interface AppModule {
    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: TranslateUseCase
    val voiceParser: VoiceToTextParser
}


class AppModuleImpl(
    parser: VoiceToTextParser
): AppModule {

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
           TranslateDatabase(
               DatabaseDriverFactory().create()
           )
        )
    }

    override val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    override val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }

    override val voiceParser: VoiceToTextParser = parser

}