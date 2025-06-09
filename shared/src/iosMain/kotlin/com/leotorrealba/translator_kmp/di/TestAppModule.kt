package com.leotorrealba.translator_kmp.di

import com.leotorrealba.translator_kmp.testing.FakeHistoryDataSource
import com.leotorrealba.translator_kmp.testing.FakeTranslateClient
import com.leotorrealba.translator_kmp.testing.FakeVoiceToTextParser
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase

class TestAppModule() : AppModule {

    override val historyDataSource = FakeHistoryDataSource()
    override val translateClient = FakeTranslateClient()
    override val translateUseCase = TranslateUseCase(translateClient, historyDataSource)
    override val voiceParser = FakeVoiceToTextParser()
}