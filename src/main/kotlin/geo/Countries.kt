package io.github.cbaumont.geo

import kotlin.random.Random

fun String.isAValidCountry(validLocations: Collection<String> = CountryNames): Boolean =
    validLocations.asSequence().map(String::uppercase).contains(this.uppercase())

fun randomCountry(random: Random): String = CountryNames.random(random)

private val CountryNames: Set<String> = CountriesTable.map { it.name }.toSortedSet()

sealed interface GeoLocation {
    val name: String
    val latitude: Double
    val longitude: Double
}

data class Country(
    override val name: String,
    override val latitude: Double,
    override val longitude: Double
) : GeoLocation {
    companion object {
        fun of(name: String): Country =
            CountriesTable.find {
                it.name.equals(name, ignoreCase = true)
            } ?: error("Invalid country name.")
    }
}
