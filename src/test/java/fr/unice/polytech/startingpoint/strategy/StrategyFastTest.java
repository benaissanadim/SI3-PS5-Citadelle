package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.motor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategyFastTest {

    Player player;
    StrategyFast strategy;
    GameMaster gameMaster ;
    Bank bank = new Bank();
    BoardPlayer boardPlayer ;

    @BeforeEach
    void  init(){
        player = mock(Player.class);
        boardPlayer = new BoardPlayer();
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
        strategy = new StrategyFast(player,gameMaster);
    }


    @Test
    public void CoinsOrDistrictTrue() {
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE)));
        when(player.getCoins()).thenReturn(2);
        when(player.getCity()).thenReturn(new City());
        assertTrue(strategy.chooseCoinsOrDistrict(), "because coins = 2 <3");
    }

    @Test
    public void CoinsOrDistrictFalse() {
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE)));
        when(player.getCoins()).thenReturn(5);
        when(player.getCity()).thenReturn(new City());
        assertFalse(strategy.chooseCoinsOrDistrict());
    }

    @Test
    void chooseADistrict(){
        when(player.getCity()).thenReturn(new City());
        assertEquals(strategy.chooseADistrict(Arrays.asList(Districts.CASTLE ,Districts.PALACE)), Districts.CASTLE);
    }

    @Test
    void districtToBuildTest(){
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE)));
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.CHURCH, Districts.TEMPLE, Districts.CASERNE)));
        when(player.getCoins()).thenReturn(5);
        assertEquals(strategy.districtToBuild(), Districts.MANOR);
    }

    @Test
    public void isBadDistrictValueTest(){
        when(player.getCity()).thenReturn(new City());
        assertTrue(strategy.isBadDistrict(Districts.CITYHOTEL),"the value card >2");
    }

    @Test
    void bestCardTest(){
        when(player.getHand()).thenReturn(new Hand());
        when(player.getCity()).thenReturn(new City((Arrays.asList(Districts.TEMPLE, Districts.CASTLE, Districts.FORTRES, Districts.TAVERN))));
        when(player.getCoins()).thenReturn(2);
        assertEquals(Districts.TAVERN,strategy.bestCard(player));
    }


    @Test
    void giveBadDistrictForPowerTest(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.CITYHOTEL, Districts.TAVERN)))  ;
        when(player.getHand()).thenReturn(new Hand(List.of(Districts.TAVERN)))  ;

        assertEquals(strategy.giveBadDistrictForPower(), Districts.TAVERN);
    }

}