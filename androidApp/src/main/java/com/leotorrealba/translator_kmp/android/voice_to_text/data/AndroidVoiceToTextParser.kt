package com.leotorrealba.translator_kmp.android.voice_to_text.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_CLIENT
import com.leotorrealba.translator_kmp.android.R
import com.leotorrealba.translator_kmp.core.domain.util.CommonMutableStateFlow
import com.leotorrealba.translator_kmp.core.domain.util.CommonStateFlow
import com.leotorrealba.translator_kmp.core.domain.util.asCommonStateFlow
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParser
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AndroidVoiceToTextParser(
    private val app: Application
): VoiceToTextParser, RecognitionListener {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    private val _state = CommonMutableStateFlow(
        MutableStateFlow(VoiceToTextParserState())
    )

    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.asCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update {
            VoiceToTextParserState()
        }

        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update { it.copy(
                error = app.getString(R.string.error_speech_recognition_unavailable)
            ) }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update { VoiceToTextParserState() }
        recognizer.stopListening()
    }

    override fun cancel() {
        recognizer.cancel()
    }

    override fun reset() {
        _state.value = VoiceToTextParserState()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsDb: Float) {
        _state.update { it.copy(
            powerRatio = rmsDb * (1f / (12f - (-2f)))
        ) }
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { it.copy(isSpeaking = false) }
    }

    override fun onError(code: Int) {
        if (code == ERROR_CLIENT) {
            return
        }
        _state.update { it.copy(error = "Error: $code") }
    }

    override fun onResults(result: Bundle?) {
        result
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update { it.copy(result = text) }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit

}