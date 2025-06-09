package com.leotorrealba.translator_kmp.voice_to_text.domain

import com.leotorrealba.translator_kmp.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}