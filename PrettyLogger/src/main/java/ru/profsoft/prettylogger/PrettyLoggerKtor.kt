package ru.profsoft.prettylogger

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import io.ktor.client.plugins.logging.*

class PrettyLoggerKtor(private val logMessage: (String) -> Unit): Logger {

    override fun log(message: String) {
        if (printBetweenSymbols(
                message = message,
                startSymbol = '{',
                endSymbol = '}'
            )
        ) return

        if (printBetweenSymbols(
                message = message,
                startSymbol = '[',
                endSymbol = ']'
            )
        ) return
        printAll(message)
    }

    private fun printBetweenSymbols(message: String, startSymbol: Char, endSymbol: Char): Boolean {
        val startBody = message.indexOf(startSymbol)
        if (startBody == -1) {
            return false
        }
        val endBody = message.lastIndexOf(endSymbol)
        if (endBody == -1) {
            return false
        }
        val messageOne = message.substring(0 until startBody)
        printAll(messageOne)
        val messageBody = message.substring(startBody..endBody)
        printJson(messageBody)
        if (endBody == message.length) return true
        printAll(message.substring((endBody + 1) until message.length))
        return true
    }

    private fun printJson(message: String) {
        try {
            val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                .create().toJson(JsonParser().parse(message))
            printAll('\n' + prettyPrintJson)
        } catch (m: JsonSyntaxException) {
            printAll(message)
        }
    }

    private fun printAll(message: String) {
        logMessage(message)
    }
}