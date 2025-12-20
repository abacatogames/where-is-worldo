package io.github.cbaumont

import java.util.Locale

fun String.isAValidCountry(validLocations: Collection<String> = Countries): Boolean =
    validLocations.asSequence().map(String::uppercase).contains(this.uppercase())

private val Countries: Set<String> = Locale.getISOCountries()
    .map { code -> Locale.Builder().setRegion(code).build().getDisplayCountry(Locale.ENGLISH) }
    .toSortedSet()