package io.github.cbaumont

import java.time.LocalDate
import java.time.LocalTime.MIN
import java.time.ZoneOffset.UTC
import kotlin.random.Random

var lastUpdated: LocalDate = LocalDate.now()
fun generateWordForDate(
    date: LocalDate,
    randomizer: (Random) -> String = ::randomCountry
): String {
    if (date > lastUpdated) {
        lastUpdated = date
    }
    return randomizer(Random(lastUpdated.toEpochSecond(MIN, UTC)))
}
