package ru.profsoft.prettylogger

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class PrettyLogger(private val logMessage: (String) -> Unit) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson =
                    GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
                logMessage(prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                logMessage(message)
            }
        } else {
            logMessage(message)
            return
        }
    }
}