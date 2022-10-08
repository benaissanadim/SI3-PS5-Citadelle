package fr.unice.polytech.startingpoint.motor;
import fr.unice.polytech.startingpoint.cards.Districts;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    City city = new City();

    @Test
    void isBuildWithEmptyCity(){
        assertFalse(city.isBuild());
    }

    @Test
    void isBuildFalse(){
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CASERNE));
        assertFalse(city.isBuild());
    }

    @Test
    void isBuildTrue(){
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CASERNE, Districts.PALACE,
                Districts.CATHEDRAL, Districts.MONASTERY, Districts.CITYHOTEL, Districts.CHURCH));
        assertTrue(city.isBuild());
    }

    @Test
    void setOfColorsWithEmptyCity(){
        Set<String> colors = new HashSet<>();
        assertEquals(colors,city.setOfColors());
    }

    @Test
    void setOfColors(){//blue red yellow
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASERNE, Districts.CATHEDRAL, Districts.FORTRES));
        Set<String> colors = new HashSet<>(Arrays.asList("Red", "Yellow", "Blue"));
        assertEquals(colors,city.setOfColors());
    }

    @Test
    void setOfColorsWithOnlyGreen(){//blue red yellow
        city = new City(Arrays.asList(Districts.TAVERN, Districts.MARKET, Districts.CITYHOTEL, Districts.STALL));
        Set<String> colors = new HashSet<>(List.of("Green"));
        assertEquals(colors,city.setOfColors());
    }

    @Test
    void checkFiveColoWithEmptyCity(){
        assertFalse(city.checkFiveColor());
    }

    @Test
    void checkFiveColoFalse(){
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASERNE, Districts.CATHEDRAL, Districts.FORTRES));
        assertFalse(city.checkFiveColor());
    }

    @Test
    void checkFiveColoTrue(){
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASERNE,
                Districts.CATHEDRAL, Districts.CITYHOTEL,Districts.MAGICSCHOOL));
        assertTrue(city.checkFiveColor());
    }

    @Test
    void checkFiveColoTrueMore(){
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASERNE, Districts.CHURCH, Districts.STALL,
                Districts.CATHEDRAL, Districts.FORTRES, Districts.CITYHOTEL, Districts.CATHEDRAL, Districts.PORT , Districts.COURSEOFMIRACLES));
        assertTrue(city.checkFiveColor());
    }

    @Test
    void checkFiveColoWithCourseOfMiracleAndFiveColor(){
        city = new City(Arrays.asList(Districts.MANOR,
                Districts.CASERNE,
                Districts.CHURCH,
                Districts.STALL,
                Districts.CATHEDRAL,
                Districts.COURSEOFMIRACLES));
        assertTrue(city.checkFiveColor());
    }

    @Test
    void checkFiveColoWithCourseOfMiracleAndFourColorAndTwoViolet(){
        city = new City(Arrays.asList(Districts.MANOR,
                Districts.CASERNE,
                Districts.CHURCH,
                Districts.UNIVERSITY,
                Districts.CATHEDRAL,
                Districts.COURSEOFMIRACLES));
        assertTrue(city.checkFiveColor());
    }

    @Test
    void checkFiveColoWithCourseOfMiracleAndFourColorAndOneViolet(){
        city = new City(Arrays.asList(Districts.MANOR,
                Districts.CASERNE,
                Districts.CHURCH,
                Districts.CATHEDRAL,
                Districts.COURSEOFMIRACLES));
        assertFalse(city.checkFiveColor());
    }

    @Test
    public void hasCardWith1POTest() {
        city = new City(Arrays.asList(Districts.TEMPLE,
                Districts.CASERNE,
                Districts.CHURCH));
        assertTrue(city.hasCardWith1PO());
    }
}
