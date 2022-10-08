package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.cards.Districts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

    Hand hand = new Hand();

    @Test
    void highestDistrictWithEmptyHand() {// cas 4 : The hand is empty
        Assertions.assertEquals(Districts.NULL, hand.highestDistrict(6), "Le quartier le plus fort jouable est le palace") ;
    }

    @Test
    void highestDistrictWithNoGold() {// cas 1 : The player has 0 PO
        hand = new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE));
        assertEquals(Districts.NULL, hand.highestDistrict(0), "Le joueur n'a pas d'argent pour jouer");
    }

    @Test
    void highestDistrictWithManorPlayable() {// cas 2 : The highest district playable is MANOR
        hand = new Hand(Arrays.asList(Districts.WATCHOWER, Districts.MARKET, Districts.FORTRES, Districts.COUNTER));
        assertEquals(Districts.COUNTER, hand.highestDistrict(3), "Le quartier le plus fort jouable est le manoire");
    }

    @Test
    void highestDistrictWithPalacePlayable() {// cas 3 : The highest district playable is PALACE
        hand = new Hand(Arrays.asList(Districts.TEMPLE, Districts.JAIL, Districts.CITYHOTEL, Districts.PORT));
        assertEquals(Districts.CITYHOTEL, hand.highestDistrict(8), "Le quartier le plus fort jouable est le palace") ;
    }

}