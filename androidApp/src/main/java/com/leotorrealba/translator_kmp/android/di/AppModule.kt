package com.leotorrealba.translator_kmp.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.leotorrealba.translator_kmp.database.TranslateDatabase
import com.leotorrealba.translator_kmp.translate.data.history.SqlDelightHistoryDataSource
import com.leotorrealba.translator_kmp.translate.data.local.DatabaseDriverFactory
import com.leotorrealba.translator_kmp.translate.data.remote.HttpClientFactory
import com.leotorrealba.translator_kmp.translate.data.translate.KtorTranslateClient
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateClient
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient = httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(client: TranslateClient, dataSource: HistoryDataSource): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }

}