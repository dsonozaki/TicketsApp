package com.sonozaki.ticketsapp.utils

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isNullOrEmpty()) return null
        val regex = Regex(RUSSIAN_FILTER)
        return if (source.matches(regex)) {
            null
        } else {
            val regex = Regex(RUSSIAN_SYMBOLS_FILTER)
            source.replace(regex, "")
        }
    }

    companion object {
        private const val RUSSIAN_FILTER = "^[а-яА-ЯёЁ\\s]+$"
        private const val RUSSIAN_SYMBOLS_FILTER = "[^а-яА-ЯёЁ]"
    }
}