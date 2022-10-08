package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotColor;
import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;
import fr.unice.polytech.startingpoint.strategy.StrategyColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ThiefTest {
    Player player;
    Player p1 ;
    Player p2;
    Player p3;

    Strategy strategy;
    GameMaster gameMaster ;
    Bank bank = new Bank();
    BoardPlayer boardPlayer ;

    @BeforeEach
    void init(){
        boardPlayer = new BoardPlayer();
        p1 = mock(Player.class);
        when(p1.getCharacter()).thenReturn(new King(gameMaster));
        p2 = mock(Player.class);
        when(p2.getCharacter()).thenReturn(new Marchant(gameMaster));
        p3 = mock(Player.class);
        when(p3.getCharacter()).thenReturn(new Thief(gameMaster));
        boardPlayer.add(p1);boardPlayer.add(p2);boardPlayer.add(p3);
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
    }


    /*
    try to thief the richest
     */
    @Test
    void usePowerTest(){
        player = mock(BotColor.class);
        strategy = new StrategyColor(player,gameMaster);
        when(player.getCharacter()).thenReturn(new Thief(gameMaster));
        when(player.getStrategy()).thenReturn(strategy);

        boardPlayer.add(player);

        when(p1.getCoins()).thenReturn(3);
        when(p2.getCoins()).thenReturn(4); // richest
        when(p3.getCoins()).thenReturn(1);
        when(player.getCoins()).thenReturn(4);

        when(player.getIndex()).thenReturn(2);
        when(p2.getIndex()).thenReturn(4);
        when(p3.getIndex()).thenReturn(1);

        when(player.getPossibleCharactersAfter()).thenReturn(new ArrayList<>(Arrays.asList(new Architect(gameMaster), new Thief(gameMaster))));


        player.getCharacter().usePower();
        assertTrue(player.getPossibleCharactersAfter().contains(gameMaster.getCharacterThiefed()));

    }

}
