package com.leotorrealba.translator_kmp.android.voice_to_text.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParser
import com.leotorrealba.translator_kmp.voice_to_text.presentation.VoiceToTextEvent
import com.leotorrealba.translator_kmp.voice_to_text.presentation.VoiceToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
    private val parser: VoiceToTextParser
): ViewModel(){

    private val viewmodel by lazy {
        VoiceToTextViewModel(
            parser = parser,
            coroutineScope = viewModelScope
            )
    }

    val state = viewmodel.state

    fun onEvent(event: VoiceToTextEvent){
        viewmodel.onEvent(event)
    }

}