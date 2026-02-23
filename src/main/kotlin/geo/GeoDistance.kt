package io.github.cbaumont.geo

fun interface GeoDistance : (GeoLocation, GeoLocation) -> Distance {
    companion object {
        fun create(): GeoDistance =
            object : GeoDistance {
                override fun invoke(p1: GeoLocation, p2: GeoLocation): Distance {
                    TODO("Not yet implemented")
                }
            }
    }
}

data class Distance(val km: Double, val direction: CardinalDirection)

enum class CardinalDirection { SOUTH, WEST, NORTH, EAST, SOUTH_WEST, SOUTH_EAST, NORTH_WEST, NORTH_EAST }
