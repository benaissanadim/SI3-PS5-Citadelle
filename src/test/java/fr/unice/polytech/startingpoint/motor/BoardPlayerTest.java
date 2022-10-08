package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardPlayerTest {

    BoardPlayer players;
    Player p1;
    Player p2;
    Player p3;

    @BeforeEach
    void init() {
        players = new BoardPlayer();
        p1 = mock(Player.class);
        p2 = mock(Player.class);
        p3 = mock(Player.class);
        players.add(p1);
        players.add(p2);
        players.add(p3);

    }

    @Test
    void playerWithMostCardsTest() {
        when(p1.getNumberOfCards()).thenReturn(5);
        when(p2.getNumberOfCards()).thenReturn(4);
        when(p3.getNumberOfCards()).thenReturn(2);

        assertEquals(p2, players.getPlayerWithMostCards(p1));
        assertEquals(p1, players.getPlayerWithMostCards(p2));
        assertEquals(p1, players.getPlayerWithMostCards(null));
    }

    @Test
    public void playerWithLargestCityTest() {
        when(p1.getCitySize()).thenReturn(7);
        when(p2.getCitySize()).thenReturn(3);
        when(p3.getCitySize()).thenReturn(5);

        assertEquals(p1, players.getPlayerWithLargestCity(null));
        assertEquals(p3, players.getPlayerWithLargestCity(p1));
        assertEquals(p1, players.getPlayerWithLargestCity(p3));
    }


    @Test
    void playerWithHighestScoreTest() {
        when(p1.getScore()).thenReturn(10);
        when(p2.getScore()).thenReturn(7);
        when(p3.getScore()).thenReturn(15);

        assertEquals(p3, players.getPlayerWithHighestScore());
        assertEquals(p3, players.getPlayerWithHighestScore());
    }


    @Test
    void playeWithMostCoinsTest() {
        when(p1.getCoins()).thenReturn(3);
        when(p2.getCoins()).thenReturn(5);
        when(p3.getCoins()).thenReturn(0);

        assertEquals(p2, players.getRichestPlayer(null));
        //assertEquals(p2, players.getRichestPlayer(p1));
        //assertEquals(p1, players.getRichestPlayer(p2));
    }

    @Test
    void playerWithManyColor() {
        when(p1.cityNbColors()).thenReturn(5);
        when(p2.cityNbColors()).thenReturn(2);
        when(p3.cityNbColors()).thenReturn(3);

        assertEquals(p1, players.gePlayerWithManyColors(null));
        assertEquals(p1, players.getRichestPlayer(p2));
    }

    @Test
    void playerRandom() {
        assertTrue(players.contains(players.getPlayerRandom()));
    }

    @Test
    public void setIndexes() {
        Player p1 = new Player(1, mock(Hand.class), mock(City.class), mock(GameMaster.class));
        Player p2 = new Player(2, mock(Hand.class), mock(City.class), mock(GameMaster.class));
        Player p3 = new Player(3, mock(Hand.class), mock(City.class), mock(GameMaster.class));


        BoardPlayer players = new BoardPlayer();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.setIndexes(3);

        assertEquals(2, players.get(0).getIndex());
        assertEquals(3, players.get(1).getIndex());
        assertEquals(1, players.get(2).getIndex());
    }

    @Test
    public void isOnlyHasCards1PO() {

        when(p1.hasCardWith1PO()).thenReturn(true);
        when(p2.hasCardWith1PO()).thenReturn(false);
        when(p3.hasCardWith1PO()).thenReturn(false);

        assertTrue(players.isOnlyHasCards1PO(p1));

    }



    @Test
    public void isOnlyHasCards1POFalse() {

        when(p1.hasCardWith1PO()).thenReturn(true);
        when(p2.hasCardWith1PO()).thenReturn(true);
        when(p3.hasCardWith1PO()).thenReturn(false);

        assertFalse(players.isOnlyHasCards1PO(p1));

    }

    @Test
    public void playerWith6District() {
        when(p1.getCitySize()).thenReturn(6);
        when(p2.getCitySize()).thenReturn(4);
        when(p3.getCitySize()).thenReturn(3);

        assertEquals(p1, players.getPlayer6Districts());
    }

    @Test
    public void playerWith7District() {
        when(p1.getCitySize()).thenReturn(7);
        when(p2.getCitySize()).thenReturn(4);
        when(p3.getCitySize()).thenReturn(3);

        assertEquals(p1, players.getPlayer7Districts());
    }

}
