package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    return this.substring(0, this.length.coerceAtMost(length + 1)).trim() + "..."
}

fun String.stripHtml(): String {
    return this.replace("<[^>]*>|[&<>'\"]".toRegex(), "").replace("\\s+".toRegex(), " ")
}