package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.BotBasic;

import fr.unice.polytech.startingpoint.bot.BotColor;
import fr.unice.polytech.startingpoint.bot.BotFast;
import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.Bank;
import fr.unice.polytech.startingpoint.motor.BoardPlayer;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;
import fr.unice.polytech.startingpoint.strategy.StrategyBasic;
import fr.unice.polytech.startingpoint.strategy.StrategyColor;
import fr.unice.polytech.startingpoint.strategy.StrategyFast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AssassinTest {
    Player player;
    Player p1 ;
    Player p2;
    Player p3;

    Strategy strategy;
    GameMaster gameMaster ;
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
        gameMaster = new GameMaster(boardPlayer, mock(Bank.class));
        Districts.initDistrictDeck();
    }


    /*
    Kill who have biggest score
     */
    @Test
    void usePowerTestStrategyBasic(){
        player = mock(BotBasic.class);
        strategy = new StrategyBasic(player,gameMaster);
        when(player.getCharacter()).thenReturn(new Assassin(gameMaster));
        when(player.getStrategy()).thenReturn(strategy);

       boardPlayer.add(player);

        when(p1.getScore()).thenReturn(3);
        when(p2.getScore()).thenReturn(5);
        when(p3.getScore()).thenReturn(0);
        when(player.getScore()).thenReturn(4);

        when(player.getIndex()).thenReturn(3);
        when(p2.getIndex()).thenReturn(1);

        when(player.getPossibleCharactersBefore()).thenReturn(new ArrayList<>(Arrays.asList(new Architect(gameMaster), new Thief(gameMaster))));

        player.getCharacter().usePower();
        assertTrue(player.getPossibleCharactersBefore().contains(gameMaster.getCharacterKilled()));

    }

    @Test
    void usePowerTestStrategyFast(){
        player = mock(BotFast.class);
        strategy = new StrategyFast(player,gameMaster);
        when(player.getCharacter()).thenReturn(new Assassin(gameMaster));
        when(player.getStrategy()).thenReturn(strategy);

        boardPlayer.add(player);

        when(p1.getCitySize()).thenReturn(3);
        when(p2.getCitySize()).thenReturn(5);
        when(p3.getCitySize()).thenReturn(0);
        when(player.getCitySize()).thenReturn(4);

        when(player.getIndex()).thenReturn(1);
        when(p2.getIndex()).thenReturn(4);

        when(player.getPossibleCharactersAfter()).thenReturn(new ArrayList<>(Arrays.asList(new Architect(gameMaster), new Thief(gameMaster))));

        player.getCharacter().usePower();
        assertTrue(player.getPossibleCharactersAfter().contains(gameMaster.getCharacterKilled()));

    }

    @Test
    void usePowerTestStrategyColor(){
        player = mock(BotColor.class);
        strategy = new StrategyColor(player,gameMaster);
        when(player.getCharacter()).thenReturn(new Assassin(gameMaster));
        when(player.getStrategy()).thenReturn(strategy);

        boardPlayer.add(player);

        when(p1.cityNbColors()).thenReturn(3);
        when(p2.cityNbColors()).thenReturn(4);
        when(p3.cityNbColors()).thenReturn(1);
        when(player.cityNbColors()).thenReturn(4);

        when(player.getIndex()).thenReturn(1);
        when(p2.getIndex()).thenReturn(4);

        when(player.getPossibleCharactersAfter()).thenReturn(new ArrayList<>(Arrays.asList(new Architect(gameMaster), new Thief(gameMaster))));

        player.getCharacter().usePower();
        assertTrue(player.getPossibleCharactersAfter().contains(gameMaster.getCharacterKilled()));

    }
}
