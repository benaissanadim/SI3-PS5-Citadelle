package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotBasic;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ArchitectTest {
    BotBasic bot1 ;
    BoardPlayer players ;
    GameMaster gameMaster ;


    @BeforeEach
    void init(){
        players = new BoardPlayer() ;
        gameMaster = new GameMaster(players, mock(Bank.class));
        Districts.initDistrictDeck();
    }

    @Test
    void usePowerTest(){
        Hand hand = new Hand();
        City city = new City();
        bot1 = new BotBasic (1,hand,city, gameMaster);
        bot1.setCharacter(new Architect(gameMaster));
        players.add(bot1);

        bot1.getCharacter().usePower();

        assertEquals(bot1.getStrategy().getNbToBuild(), 3);
        assertEquals(bot1.getNumberOfCards() , 2);
    }
}
