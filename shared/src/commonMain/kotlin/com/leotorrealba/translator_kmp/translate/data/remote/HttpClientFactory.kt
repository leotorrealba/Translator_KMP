package com.leotorrealba.translator_kmp.translate.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {

    fun create(): HttpClient

}