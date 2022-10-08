package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.cards.character.King;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class StrategyRandomTest {

    Player player;
    Hand hand = new Hand();
    City city = new City();
    StrategyRandom strategy;
    GameMaster gameMaster ;
    Bank bank = new Bank();
    BoardPlayer boardPlayer ;

    @BeforeEach
    void  init(){
        boardPlayer = new BoardPlayer();
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
        player = new BotRandom(1 ,hand,city, gameMaster);
        strategy = new StrategyRandom(player,gameMaster);
    }


    @Mock Districts district1 = mock(Districts.class);
    @Mock Districts district2 = mock(Districts.class);


    @Test
    public void chooseADistrict(){
        List<Districts> d = new ArrayList<>();
        d.add(district1);
        d.add(district2);
      assertTrue(d.contains(strategy.chooseADistrict(d)));
    }

    @Test
    void districtToBuild(){
        Districts d = strategy.districtToBuild();
        assertTrue(player.getHand().contains(d) || d.equals(Districts.NULL));
    }

    @Test
    void bestCardTest(){
        Districts.initDistrictDeck();
        player.setCoins(5);
        city = new City(Arrays.asList(Districts.CASERNE, Districts.CASTLE, Districts.FORTRES));
        Player p = new Player(2 ,hand,city, gameMaster);
        assertTrue(city.contains(strategy.bestCard(p)));
    }

    @Test
    void bestPlayerTest(){

        City city1 = new City((Arrays.asList(Districts.CASERNE, Districts.CASTLE, Districts.FORTRES, Districts.TAVERN)));
        Player p1 =new Player(2 ,hand,city1, gameMaster);
        boardPlayer.add(p1);

        City city2 = new City((Arrays.asList(Districts.CHURCH, Districts.CASTLE)));
        Player p2 = new Player (4 ,hand,city2, gameMaster);
        p2.setCharacter(new King(gameMaster));
        boardPlayer.add(p2);

        assertTrue(boardPlayer.contains(strategy.bestPlayer(p1)));
    }

    @Test
    void bestCharacterToChoose(){
        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());
        Character character = strategy.bestCharacterToChoose(listOfCharacters);
        assertTrue(listOfCharacters.contains(character));
    }


}