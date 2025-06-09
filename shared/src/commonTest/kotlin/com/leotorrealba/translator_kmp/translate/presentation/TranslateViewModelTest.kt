package com.leotorrealba.translator_kmp.translate.presentation

import com.leotorrealba.translator_kmp.translate.data.local.FakeHistoryDataSource
import com.leotorrealba.translator_kmp.translate.data.remote.FakeTranslateClient
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.leotorrealba.translator_kmp.core.presentation.UiLanguage
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryItem


class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setUp() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = TranslateUseCase(
            client = client,
            historyDataSource = dataSource
        )

        viewModel = TranslateViewModel(
            translate = translate,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history item are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromText = "from",
                toText = "to",
                fromLanguageCode = "en",
                toLanguageCode = "es"
            )

            dataSource.insertHistoryItem(item)

            val state = awaitItem()
            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                toText = item.toText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isEqualTo(true)

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isEqualTo(false)
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}