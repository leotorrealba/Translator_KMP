package com.leotorrealba.translator_kmp.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotorrealba.translator_kmp.translate.domain.history.HistoryDataSource
import com.leotorrealba.translator_kmp.translate.domain.translate.TranslateUseCase
import com.leotorrealba.translator_kmp.translate.presentation.TranslateEvent
import com.leotorrealba.translator_kmp.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
): ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent){
        viewModel.onEvent(event)
    }

}