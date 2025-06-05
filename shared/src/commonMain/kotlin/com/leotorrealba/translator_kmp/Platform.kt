package com.leotorrealba.translator_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform