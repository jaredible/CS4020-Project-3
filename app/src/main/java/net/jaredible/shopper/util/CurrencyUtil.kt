package net.jaredible.shopper.util

object CurrencyUtil {

    fun convert(value: Int): String {
        return String.format("%,.2f", value / 100.0)
    }

}