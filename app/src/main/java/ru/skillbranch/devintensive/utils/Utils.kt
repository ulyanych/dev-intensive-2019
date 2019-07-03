package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.trim()?.split(" ")

        val firstName  = if (parts.isNullOrEmpty() || parts[0].isBlank()) null else parts[0]
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val parts = listOfNotNull(firstName?.trim()?.firstOrNull(), lastName?.trim()?.firstOrNull())
        return if (parts.isNullOrEmpty()) null else parts.joinToString("").toUpperCase()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val mapChars: Map<String, String> = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya"
        )

        var transliterated = ""
        payload.forEach {
            val currentChar = it.toString()
            var newChar: String = mapChars.getOrElse(currentChar.toLowerCase()){currentChar}

            if (it.isUpperCase())
                newChar = newChar.capitalize()
            transliterated += newChar
        }

        return transliterated.replace(" ", divider)
    }
}