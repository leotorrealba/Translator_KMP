package com.leotorrealba.translator_kmp.di

import com.leotorrealba.translator_kmp.database.TranslateDatabase
import com.leotorrealba.translator_kmp.translate.data.history.SqlDelightHistoryDataSource
import com.leotorrealba.translator_kmp.translate.data.local.DatabaseDriverFactory
import com.leotorrealba.translator_kmp.translate.data.remote.HttpClientFactory
import com.leotorrealba.translator_kmp.translate.data.translate.KtorTranslateClient
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateClient
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase


class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
           TranslateDatabase(
               DatabaseDriverFactory().create()
           )
        )
    }

    val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }

}