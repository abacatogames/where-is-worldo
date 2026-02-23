import io.github.cbaumont.geo.CardinalDirection
import io.github.cbaumont.geo.CountriesTable
import io.github.cbaumont.geo.Distance
import io.github.cbaumont.geo.GeoDistance
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class GeoDistanceTest {

    @Test
    fun `calculates the distance between two countries - SW`() {
        val country1 = CountriesTable.find { it.name == "Brazil" }!!
        val country2 = CountriesTable.find { it.name == "Argentina" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(2916, CardinalDirection.SOUTH_WEST)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - S`() {
        val country1 = CountriesTable.find { it.name == "Niger" }!!
        val country2 = CountriesTable.find { it.name == "Nigeria" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(945, CardinalDirection.SOUTH)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - N`() {
        val country1 = CountriesTable.find { it.name == "Nigeria" }!!
        val country2 = CountriesTable.find { it.name == "Niger" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(945, CardinalDirection.NORTH)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - E`() {
        val country1 = CountriesTable.find { it.name == "Algeria" }!!
        val country2 = CountriesTable.find { it.name == "Libya" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(1553, CardinalDirection.EAST)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - W`() {
        val country1 = CountriesTable.find { it.name == "Togo" }!!
        val country2 = CountriesTable.find { it.name == "Ghana" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(217, CardinalDirection.WEST)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - NW`() {
        val country1 = CountriesTable.find { it.name == "Algeria" }!!
        val country2 = CountriesTable.find { it.name == "Morocco" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(942, CardinalDirection.NORTH_WEST)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - NE`() {
        val country1 = CountriesTable.find { it.name == "Chad" }!!
        val country2 = CountriesTable.find { it.name == "Egypt" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(1774, CardinalDirection.NORTH_EAST)

        assertEquals(expected, result)
    }

    @Test
    fun `calculates the distance between two countries - SE`() {
        val country1 = CountriesTable.find { it.name == "Bolivia" }!!
        val country2 = CountriesTable.find { it.name == "Paraguay" }!!

        val result = GeoDistance.create()(country1, country2)

        val expected = Distance(958, CardinalDirection.SOUTH_EAST)

        assertEquals(expected, result)
    }
}