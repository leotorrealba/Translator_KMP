package com.leotorrealba.translator_kmp.translate.presentation

import com.leotorrealba.translator_kmp.core.presentation.UiLanguage

sealed class TranslateEvent {
    data class ChooseFromLanguage(val language: UiLanguage): TranslateEvent()
    data class ChooseToLanguage(val language: UiLanguage): TranslateEvent()
    object StopChoosingLanguage: TranslateEvent()
    object SwapLanguages: TranslateEvent()
    data class ChangeTranslationText(val text: String): TranslateEvent()
    object Translate: TranslateEvent()
    object OpenFromLanguageDropdown: TranslateEvent()
    object OpenToLanguageDropdown: TranslateEvent()
    object CloseTranslation: TranslateEvent()
    data class SelectHistoryItem(val item: UiHistoryItem): TranslateEvent()
    object EditTranslation: TranslateEvent()
    object RecordAudio: TranslateEvent()
    data class SubmitVoiceResult(val result: String?): TranslateEvent()
    object OnErrorSeen: TranslateEvent()

}