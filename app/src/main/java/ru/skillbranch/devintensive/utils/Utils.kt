package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts : List<String>? = fullName?.trim()?.split(" ")

        var firstName  = parts?.getOrNull(0)
        if (firstName.isNullOrEmpty()) firstName =  null
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload
    }

    fun initials(firstName: String?, lastName: String?): String? {
        val parts = listOf(firstName?.firstOrNull(), lastName?.firstOrNull())
        return parts.filterNotNull().joinToString(" ")
    }
}