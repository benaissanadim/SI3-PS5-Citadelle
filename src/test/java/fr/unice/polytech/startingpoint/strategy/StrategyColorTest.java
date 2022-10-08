
package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.cards.character.*;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrategyColorTest {

    Player player;
    Hand hand = new Hand();
    City city = new City();
    StrategyColor strategy;
    GameMaster gameMaster ;
    Bank bank = new Bank();
    BoardPlayer boardPlayer ;


    @BeforeEach
    void  init(){

        player = mock(Player.class);
        boardPlayer = new BoardPlayer();
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
        strategy = new StrategyColor(player,gameMaster);
    }

    @Test
    void chooseCoinsOrDistrictTrue(){
        when(player.getCity()).thenReturn(city);
        when(player.getCoins()).thenReturn(1);
        when(player.getHand()).thenReturn(hand);

        assertFalse(strategy.chooseCoinsOrDistrict());
    }

    @Test
    void districtToBuildNewColor() {
        hand = new Hand(Arrays.asList(Districts.CHURCH, Districts.MONASTERY, Districts.MANOR)); //blue ,yellow
        city = new City(Arrays.asList(Districts.CASTLE, Districts.PALACE)); //yellow
        when(player.getCity()).thenReturn(city);
        when(player.getCoins()).thenReturn(5);
        when(player.getHand()).thenReturn(hand);
        assertEquals(Districts.MONASTERY, strategy.districtToBuild(), "build max new color card not in city");
    }

    @Test
    void chooseADistrictWithAlreadyColorInCity(){
        city = new City(Arrays.asList(Districts.CHURCH, Districts.CASTLE, Districts.STALL)); // 2 cards yello and 1 red
        when(player.getCity()).thenReturn(city);
        when(player.getCoins()).thenReturn(1);
        when(player.getHand()).thenReturn(hand);
        assertEquals(Districts.MARKET, strategy.chooseADistrict(Arrays.asList(Districts.TEMPLE, Districts.MARKET)));
    }

    @Test
    void chooseADistrictWithOneColorNotInCity(){
        city = new City(Arrays.asList(Districts.COUNTER, Districts.TAVERN, Districts.PALACE)); // 2 cards yello and 1 red
        when(player.getCity()).thenReturn(city);
        when(player.getCoins()).thenReturn(1);
        when(player.getHand()).thenReturn(hand);
        assertEquals(Districts.TEMPLE, strategy.chooseADistrict(Arrays.asList(Districts.CASTLE, Districts.TEMPLE)));
    }

    @Test
    void chooseADistrictWithTwoColorNotInCity(){
        city = new City(Arrays.asList(Districts.TAVERN, Districts.CASTLE, Districts.STALL)); // 2 cards yello and 1 red
        when(player.getCity()).thenReturn(city);
        when(player.getCoins()).thenReturn(1);
        when(player.getHand()).thenReturn(hand);
        assertEquals(Districts.FORTRES, strategy.chooseADistrict(Arrays.asList(Districts.CHURCH, Districts.FORTRES)));
    }


    @Test
    void maxBonusFromCharacterColor(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.TAVERN, Districts.CASTLE, Districts.STALL)));
        assertTrue(strategy.maxBonusFromCharacterColor(Character.getCharactersDeck()) instanceof Marchant);
    }

    @Test
    void isBadDistrictTest(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MONASTERY, Districts.TEMPLE, Districts.MANOR)));
        assertTrue(strategy.isBadDistrict(Districts.TEMPLE));
    }

    @Test
    void pickCharacterTest(){
        city = new City(Arrays.asList(Districts.CASERNE, Districts.CASTLE, Districts.FORTRES));
        Player player = new Player(1,hand,city,gameMaster);
        List<Character> characterList = new ArrayList<>(Character.getCharactersDeck());
        Strategy strategy = new StrategyColor(player,gameMaster);
        strategy.pickCharacter(characterList);
        assertTrue(player.getCharacter() instanceof Soldier, "soldier we have 2 red cars in city");
    }


    @Test
    void bestCardTest(){
        Hand hand = new Hand();
        City city = new City((Arrays.asList(Districts.TEMPLE, Districts.CASTLE, Districts.FORTRES, Districts.TAVERN)));
        Player player =new Player(1 ,hand,city, gameMaster);
        player.setCoins(0);
        assertEquals(Districts.TAVERN,strategy.bestCard(player));
    }

    @Test
    void bestCharacterTest(){
        when(player.getHand()).thenReturn(new Hand()) ;
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CITYHOTEL)));
        Character character = strategy.bestCharacterToChoose(Character.getCharactersDeck());
        assertTrue(character instanceof King);
        }
}




