package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotBasic;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WizardTest {
    BoardPlayer players = new BoardPlayer();
    Bank bank =new Bank();
    GameMaster gameMaster ;
    Hand hand2 ;
    Player bot2 ;


    @BeforeEach
    void init(){
        gameMaster = new GameMaster(players, bank);
        Districts.initDistrictDeck();
        hand2 = new Hand(Arrays.asList(Districts.CHURCH, Districts.CASERNE, Districts.MANOR));
        bot2 = new BotBasic(2,hand2,new City(), gameMaster);
        bot2.setCharacter(new Assassin(gameMaster));
        players.add(bot2);

        Hand hand3 = new Hand(List.of(Districts.CHURCH));
        BotBasic bot3 = new BotBasic (3, hand3,new City(), gameMaster);
        bot3.setCharacter(new Bishop(gameMaster));
        players.add(bot3);

        BotBasic bot4 = new BotBasic (4, new Hand(),new City(), gameMaster);
        bot4.setCharacter(new Architect(gameMaster));
        players.add(bot4);
    }

    @Test
    void usePowerTestChangeHandManyBadCards(){
        Hand hand1 = new Hand(Arrays.asList(Districts.MONASTERY, Districts.TEMPLE));
        City city1 = new City(Arrays.asList(Districts.MONASTERY, Districts.TEMPLE));
        Player bot1 = new BotBasic(1 ,hand1,city1, gameMaster);
        bot1.setCharacter(new Wizard(gameMaster));
        players.add(bot1);

        bot1.getCharacter().usePower();

        assertEquals(hand2 ,bot1.getHand(), "the bot change his hand with who have many cards ");
        assertEquals(hand1 ,bot2.getHand());
    }

    @Test
    void usePowerTestExchangeWithDeck(){
        Hand hand1 = new Hand(Arrays.asList(Districts.MONASTERY, Districts.JAIL, Districts.TEMPLE, Districts.TAVERN , Districts.FORTRES));
        City city1 = new City(Arrays.asList(Districts.MONASTERY, Districts.TEMPLE, Districts.CITYHOTEL));
        Player bot1 = new BotBasic(1 ,hand1,city1, gameMaster);
        bot1.setCharacter(new Wizard(gameMaster));
        bot1.setCoins(5);
        players.add(bot1);

        bot1.getCharacter().usePower();

        assertEquals(Districts.JAIL,bot1.getHand().get(0));
        assertEquals(Districts.TAVERN,bot1.getHand().get(1));
        assertEquals(Districts.FORTRES,bot1.getHand().get(2));

    }
}
