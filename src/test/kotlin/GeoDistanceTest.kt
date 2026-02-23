import io.github.cbaumont.geo.CountriesTable
import net.sf.geographiclib.Geodesic
import org.junit.jupiter.api.Test

class GeoDistanceTest {

    @Test
    fun `calculates the distance between two countries`() {

        val country2 = CountriesTable.find { it.name == "Argentina" }!!
        val country1 = CountriesTable.find { it.name == "Brazil" }!!


        val distance =
            Geodesic.WGS84.Inverse(country1.latitude, country1.longitude, country2.latitude, country2.longitude)

        println(distance.s12)
        println(distance.azi1)
    }
}