package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotBasic;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarchantTest {
    BotBasic bot1 ;
    Bank bank =new Bank();
    BoardPlayer players = new BoardPlayer() ;
    GameMaster gameMaster ;


    @BeforeEach
    void init(){
        gameMaster = new GameMaster(players, bank);
    }

    @Test
    void usePowerTest(){
        Hand hand = new Hand();
        City city = new City();
        bot1 = new BotBasic (1,hand,city, gameMaster);
        bot1.setCharacter(new Marchant(gameMaster));
        players.add(bot1);
        bot1.setCoins(5);

        bot1.getCharacter().usePower();

        assertEquals(bot1.getCoins(), 6);
    }
}
