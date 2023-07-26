package com.bytecode.petsy.util

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.CompositionLocalContext
import java.util.Locale

object LocaleUtils {
    fun setLocale(c: Context, language: String = "GB") =
        updateResources(c, language) //use locale codes

    private fun updateResources(context: Context, language: String) {
        var lang = "GB"

        when (language) {
            "GB" -> {
                lang = "GB"
            }

            "DK" -> {
                lang = "DA"
            }

            "RO" -> {
                lang = "RO"
            }

            "RS" -> {
                lang = "SR"
            }
        }
        context.resources.apply {
            var locale = Locale(lang)
            if (lang == "SR") {
                locale =
                    Locale.Builder().setLanguage("sr").setRegion("RS").build()
            }

            val config = Configuration(configuration)

            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, displayMetrics)
        }
    }
}
