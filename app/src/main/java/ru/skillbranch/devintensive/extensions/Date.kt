package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND { override val plurals = listOf("секунду", "секунды", "секунд") },
    MINUTE { override val plurals = listOf("минуту", "минуты", "минут") },
    HOUR { override val plurals = listOf("час", "часа", "часов") },
    DAY { override val plurals = listOf("день", "дня", "дней") };

    abstract val plurals: List<String>

    fun plural(value: Int): String? {

        val remTen = abs(value).rem(10)
        val remHundred = abs(value).rem(100)

        return "${abs(value)} " + when {
            remHundred in 11..19 -> this.plurals[2]
            remTen in 2..4 -> this.plurals[1]
            remTen == 1 -> this.plurals[0]
            else -> this.plurals[2]
        }
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy", locale: Locale = Locale("ru")): String {
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(this)
}

fun Date.add(value:Int, unit: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(unit) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val timeDiff = date.time - this.time
    val prefix = if (timeDiff < 0) "через " else ""
    val postfix = if (timeDiff > 0) " назад" else ""

    return when {
        abs(timeDiff) <= 1 * SECOND -> "только что"
        abs(timeDiff) <= 45 * SECOND -> if (timeDiff > 0) "несколько секунд назад" else "через несколько секунд"
        abs(timeDiff) <= 75 * SECOND -> if (timeDiff > 0) "минуту назад" else "через минуту"
        abs(timeDiff) <= 45 * MINUTE -> prefix + TimeUnits.MINUTE.plural((timeDiff / MINUTE).toInt()) + postfix
        abs(timeDiff) <= 75 * MINUTE -> if (timeDiff > 0) "час назад" else "через час"
        abs(timeDiff) <= 22 * HOUR -> prefix + TimeUnits.HOUR.plural((timeDiff / HOUR).toInt()) + postfix
        abs(timeDiff) <= 26 * HOUR -> "день назад"
        abs(timeDiff) <= 360 * DAY -> prefix + TimeUnits.DAY.plural((timeDiff / DAY).toInt()) + postfix
        else -> if (timeDiff > 0) "более года назад" else "более чем через год"
    }
}