package com.leotorrealba.translator_kmp.di

import com.leotorrealba.translator_kmp.translate.data.local.FakeHistoryDataSource
import com.leotorrealba.translator_kmp.translate.data.remote.FakeTranslateClient
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateClient
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase
import com.leotorrealba.translator_kmp.voice_to_text.data.FakeVoiceToTextParser
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}