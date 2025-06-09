package com.leotorrealba.translator_kmp.android.voice_to_text.di

import android.app.Application
import com.leotorrealba.translator_kmp.android.voice_to_text.data.AndroidVoiceToTextParser
import com.leotorrealba.translator_kmp.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class VoiceToTextModule {
    
    @Provides
    fun provideVoiceToTextParser(app: Application): VoiceToTextParser {
        return AndroidVoiceToTextParser(app)
    }

}