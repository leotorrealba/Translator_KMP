package com.leotorrealba.translator_kmp.translate.data.local


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.leotorrealba.translator_kmp.database.TranslateDatabase

actual class DatabaseDriverFactory{
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(TranslateDatabase.Schema, "translate.db")
    }
}