package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.King;
import fr.unice.polytech.startingpoint.motor.*;
import fr.unice.polytech.startingpoint.strategy.StrategyBasic;
import fr.unice.polytech.startingpoint.strategy.StrategyColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BotColorBasicTest {
    BotColorBasic bot ;
    GameMaster gameMaster ;


    @BeforeEach
    void init(){
        gameMaster = new GameMaster(mock(BoardPlayer.class), mock(Bank.class));
        bot = new BotColorBasic( 1,new Hand(), new City(), gameMaster);
        Districts.initDistrictDeck();
    }

    @Test
    void playColorTest(){
        bot.setCharacter(mock(King.class));
        bot.play();
        assertTrue(bot.getStrategy() instanceof StrategyColor);
    }

    @Test
    void playBasicTest(){
        City city = new City(Arrays.asList(Districts.CHURCH,Districts.FORTRES,Districts.TAVERN,Districts.CASTLE, Districts.COURSEOFMIRACLES));
        bot.setCity(city);
        assertTrue(bot.checkColorToBuild() >=5);
        bot.setCharacter(mock(King.class));
        bot.play();
        assertTrue(bot.getStrategy() instanceof StrategyBasic);
    }
}
