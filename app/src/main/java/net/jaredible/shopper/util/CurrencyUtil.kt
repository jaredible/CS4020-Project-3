package net.jaredible.shopper.util

object CurrencyUtil {

    fun format(value: Int): String {
        return String.format("%,.2f", value / 100.0)
    }

}