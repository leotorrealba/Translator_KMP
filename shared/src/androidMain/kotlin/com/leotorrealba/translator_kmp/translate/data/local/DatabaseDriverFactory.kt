package com.leotorrealba.translator_kmp.translate.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.leotorrealba.translator_kmp.database.TranslateDatabase

actual class DatabaseDriverFactory(
    private val context: Context
){
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(TranslateDatabase.Schema, context, "translate.db")
    }
}