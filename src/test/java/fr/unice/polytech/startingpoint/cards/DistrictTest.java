package fr.unice.polytech.startingpoint.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DistrictTest {

    @BeforeEach
    void init(){
        Districts.initDistrictDeck();
    }

    @Test
    void removeCard() {
        int totalCardBeginning = 0;
        for (Map.Entry<Districts, Integer> entry : Districts.getDistrictDeck().entrySet()) {
            totalCardBeginning += entry.getValue();
        }
        assertEquals(65, totalCardBeginning, "The deck contain 65 cards at the beginning");
        assertEquals(5, Districts.getDistrictDeck().get(Districts.MANOR), "There is 5 MANOR at the beginning");
        assertEquals(3, Districts.getDistrictDeck().get(Districts.WATCHOWER), "There is 5 WATCHOWER at the beginning");
        assertEquals(2, Districts.getDistrictDeck().get(Districts.CITYHOTEL), "There is 5 CITYHOTEL at the beginning");

        Districts.removeCard(Districts.MANOR);
        Districts.removeCard(Districts.WATCHOWER);
        Districts.removeCard(Districts.CITYHOTEL);

        int totalCardEnding = 0;
        for (Map.Entry<Districts, Integer> entry : Districts.getDistrictDeck().entrySet()) {
            totalCardEnding += entry.getValue();
        }
        assertEquals(62, totalCardEnding, "The deck contain 62 cards at the end");
        assertEquals(4, Districts.getDistrictDeck().get(Districts.MANOR), "There is 4 MANOR at the end");
        assertEquals(2, Districts.getDistrictDeck().get(Districts.WATCHOWER), "There is 2 WATCHOWER at the end");
        assertEquals(1, Districts.getDistrictDeck().get(Districts.CITYHOTEL), "There is 1 CITYHOTEL at the end");
    }

    @Test
    void maxDistrict() {
        assertEquals(Districts.PORT, Districts.maxDistrict(Districts.PORT, Districts.JAIL));
        assertEquals(Districts.CITYHOTEL, Districts.maxDistrict(Districts.CATHEDRAL, Districts.CITYHOTEL));
        assertEquals(Districts.CASTLE, Districts.maxDistrict(Districts.COUNTER, Districts.CASTLE),
                "If they have the same card point, the second is returned");
    }

    @Test
    void minDistrict() {
        assertEquals(Districts.CHURCH, Districts.minDistrict(Districts.CHURCH, Districts.FORTRES));
        assertEquals(Districts.JAIL, Districts.minDistrict(Districts.PALACE, Districts.JAIL));
        assertEquals(Districts.TAVERN, Districts.minDistrict(Districts.TEMPLE, Districts.TAVERN),
                "If they have the same card point, the second is returned");
    }


}