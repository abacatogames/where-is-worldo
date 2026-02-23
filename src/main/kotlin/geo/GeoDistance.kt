package io.github.cbaumont.geo

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

                val direction = determineDirection(geodesicData.azi1)

                Distance((geodesicData.s12 / 1000.00).roundToInt(), direction)
            }
    }
}

private fun determineDirection(azimuth: Double): CardinalDirection =
    when {
        azimuth in -10.0..<10.0 -> CardinalDirection.NORTH
        azimuth in 10.0..<67.5 -> CardinalDirection.NORTH_EAST
        azimuth in 67.5..<112.5 -> CardinalDirection.EAST
        azimuth in 112.5..<170.0 -> CardinalDirection.SOUTH_EAST
        azimuth >= 170.0 || azimuth < -170.0 -> CardinalDirection.SOUTH
        azimuth in -170.0..<-112.5 -> CardinalDirection.SOUTH_WEST
        azimuth in -112.5..<-67.5 -> CardinalDirection.WEST
        else -> CardinalDirection.NORTH_WEST
    }

data class Distance(val km: Int, val direction: CardinalDirection)

enum class CardinalDirection { SOUTH, WEST, NORTH, EAST, SOUTH_WEST, SOUTH_EAST, NORTH_WEST, NORTH_EAST }
