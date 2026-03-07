package com.abacatogames.geo

import kotlin.math.atan2
import kotlin.math.roundToInt
import net.sf.geographiclib.Geodesic

fun interface GeoDistance : (GeoLocation, GeoLocation) -> Distance {
    companion object {
        fun create(): GeoDistance =
            GeoDistance { firstLocation, secondLocation ->
                val geodesicData =
                    Geodesic.WGS84.Inverse(
                        firstLocation.latitude,
                        firstLocation.longitude,
                        secondLocation.latitude,
                        secondLocation.longitude
                    )

                val angle = angleInDegrees(firstLocation, secondLocation)
                val direction = determineDirection(angle)

                Distance((geodesicData.s12 / 1000.00).roundToInt(), direction)
            }
    }
}

private fun angleInDegrees(firstLocation: GeoLocation, secondLocation: GeoLocation): Double {
    val angle = atan2(
        secondLocation.longitude - firstLocation.longitude,
        secondLocation.latitude - firstLocation.latitude
    )
    return Math.toDegrees(angle)
}

private fun determineDirection(azimuth: Double): CardinalDirection {
    val normalised = (((azimuth + 360) % 360 + 11.25) / 22.5).toInt() % 16

    return when (normalised) {
        0, 15 -> CardinalDirection.NORTH
        1, 2 -> CardinalDirection.NORTH_EAST
        3, 4 -> CardinalDirection.EAST
        5, 6 -> CardinalDirection.SOUTH_EAST
        7, 8 -> CardinalDirection.SOUTH
        9, 10 -> CardinalDirection.SOUTH_WEST
        11, 12 -> CardinalDirection.WEST
        else -> CardinalDirection.NORTH_WEST
    }
}

data class Distance(val km: Int, val direction: CardinalDirection)

enum class CardinalDirection { SOUTH, WEST, NORTH, EAST, SOUTH_WEST, SOUTH_EAST, NORTH_WEST, NORTH_EAST }
