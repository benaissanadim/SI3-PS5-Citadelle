package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotBasic;

import fr.unice.polytech.startingpoint.bot.BotColor;
import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class SoldierTest {
    BoardPlayer players = new BoardPlayer();
    Bank bank =new Bank();
    GameMaster gameMaster ;
    Hand hand = new Hand();
    City city = new City();

    @BeforeEach
    void init(){
        gameMaster = new GameMaster(players, bank);
    }

    @Test
    void usePowertBotBasicNothingHappenTest(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotBasic(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(12);
        players.add(bot1);

        City city2 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setScore(4);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(9);
        players.add(bot3);

        BotBasic bot4 = new BotBasic (4, hand,city, gameMaster);
        bot4.setCharacter(new Architect(gameMaster));
        bot4.setScore(0);
        players.add(bot4);

        bot1.getCharacter().usePower();

        //rien ne passe il décide de tuer le player ayant plus de score
        // mais il est eveque

        assertEquals(bot1.getCity(), city1);
        assertEquals(bot2.getCity(), city2);
        assertEquals(bot3.getCity(), city3);
        assertEquals(bot4.getCity(), city);
    }

    @Test
    void usePowertBotBasicHighestScoreTest(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotBasic(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(6);
        players.add(bot1);

        City city2 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setScore(9);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(4);
        players.add(bot3);

        bot1.getCharacter().usePower();


        assertFalse(bot3.getHand().contains(Districts.TAVERN), "choose the min card of player with most card");
        assertEquals(6,bot1.getCoins(),"coins is less JAIL value +1");
        assertEquals(4, bot3.getScore()," the score is less  JAIL VALUE ");
    }

    @Test
    void usePowertWithCemetryInGameActivePower(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotBasic(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(6);
        players.add(bot1);

        City city2 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE, Districts.CEMETERY));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setCoins(5);
        gameMaster.setPlayerWhoGetCemetery(bot2);
        bot2.setScore(9);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(4);
        players.add(bot3);

        bot1.getCharacter().usePower();


        assertEquals(4, bot2.getCoins(),"bot buy the card destroyed with 1 euro");

        assertTrue(bot2.getHand().contains(Districts.CASTLE));
        assertEquals(3,bot1.getCoins(),"coins is less CASTLE value +1");
        assertEquals(9, bot2.getScore()," the score is less  CASTLE VALUE-3 ");
    }


    @Test
    void usePowertWithCemetryInGameNonActivePower(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotBasic(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(6);
        players.add(bot1);

        City city2 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE, Districts.CEMETERY));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setCoins(0);
        gameMaster.setPlayerWhoGetCemetery(bot2);
        bot2.setScore(9);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(4);
        players.add(bot3);

        bot1.getCharacter().usePower();

        assertEquals(0, bot2.getCoins(),"dont do anything dont have coins");

        assertFalse(bot2.getHand().contains(Districts.CASTLE));
        assertEquals(3,bot1.getCoins(),"coins is less CASTLE value +1");
        assertEquals(5, bot2.getScore()," the score is less  CASTLE VALUE-3 ");
    }


    @Test
    void usePowertBotFastLargest(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotBasic(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(6);
        players.add(bot1);

        City city2 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE, Districts.COURSEOFMIRACLES, Districts.FORTRES, Districts.TEMPLE));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setScore(18);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(4);
        players.add(bot3);

        bot1.getCharacter().usePower();

        assertFalse(bot2.getHand().contains(Districts.TEMPLE), "choose the min card of player with Largest  City");
        assertEquals(6,bot1.getCoins());
        assertEquals(17, bot2.getScore());
    }

    @Test
    void usePowertBotColorLargest(){
        City city1 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.COUNTER));
        Player bot1 = new BotColor(1 ,hand,city1, gameMaster);
        bot1.setCharacter(new Soldier(gameMaster));
        bot1.setCoins(10);
        players.add(bot1);

        //city 2 has 5 colors : green yellow violet red blue *2
        City city2 = new City(Arrays.asList(Districts.CITYHOTEL, Districts.CASTLE, Districts.COURSEOFMIRACLES, Districts.FORTRES, Districts.TEMPLE ,Districts.CHURCH));
        Player bot2 = new BotBasic(2,hand,city2, gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        bot2.setScore(18);
        players.add(bot2);

        City city3 = new City(Arrays.asList(Districts.TAVERN, Districts.TEMPLE, Districts.JAIL));
        BotBasic bot3 = new BotBasic (3, hand,city3, gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        bot3.setScore(4);
        players.add(bot3);

        bot1.getCharacter().usePower();

        assertFalse(bot2.getHand().contains(Districts.CITYHOTEL), "choose the card with color is only one time in city of player with 5 color");
        assertEquals(6,bot1.getCoins());
        assertEquals(13, bot2.getScore()," the score is less ");
    }





}