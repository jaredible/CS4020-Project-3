package net.jaredible.jshop.util

object CurrencyUtil {

    fun format(value: Int): String {
        return String.format("%,.2f", value / 100.0)
    }

}