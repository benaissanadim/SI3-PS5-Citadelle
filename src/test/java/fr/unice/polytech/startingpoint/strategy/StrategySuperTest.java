package fr.unice.polytech.startingpoint.strategy;


import fr.unice.polytech.startingpoint.bot.BotSuper;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StrategySuperTest {

    Player player;

    StrategySuper strategy;
    StrategySuper str1;
    StrategySuper str2;
    StrategySuper str3;


    GameMaster gameMaster;
    Bank bank = new Bank();
    BoardPlayer boardPlayer;

    Player p1;
    Player p2;
    Player p3;


    @BeforeEach
    void init() {
        player = mock(Player.class);
        p1 = mock(Player.class);
        p2 = mock(Player.class);
        p3 = mock(Player.class);
        boardPlayer = new BoardPlayer();
        boardPlayer.add(player);
        boardPlayer.add(p1);
        boardPlayer.add(p2);
        boardPlayer.add(p3);
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
        strategy = new StrategySuper(player, gameMaster);
        str1 = new StrategySuper(p1, gameMaster);
        str2 = new StrategySuper(p2, gameMaster);
        str3 = new StrategySuper(p3, gameMaster);

    }

    @Test
    void characterToKillSoldierInHead() {
        when(player.getScore()).thenReturn(18);
        when(p1.getScore()).thenReturn(10);
        when(p2.getScore()).thenReturn(7);
        when(p3.getScore()).thenReturn(15);

        assertTrue(strategy.characterToKill() instanceof Soldier);

    }

    @Test
    void characterToKillSoldierHasCard1PO() {

        when(player.hasCardWith1PO()).thenReturn(true);
        when(p1.hasCardWith1PO()).thenReturn(false);
        when(p2.hasCardWith1PO()).thenReturn(false);
        when(p3.hasCardWith1PO()).thenReturn(false);


        assertTrue(strategy.characterToKill() instanceof Soldier);
    }

    @Test
    void characterToKillThiefWeHavePlayerWithLotCoin() {
        when(player.getCoins()).thenReturn(3);
        when(p1.getCoins()).thenReturn(7);
        when(p2.getCoins()).thenReturn(5);
        when(p3.getCoins()).thenReturn(4);

        assertTrue(strategy.characterToKill() instanceof Thief);
    }

    @Test
    public void bestCharacterToChooseArchitect() {
        when(player.getIndex()).thenReturn(1);

        when(player.getCoins()).thenReturn(3);
        when(p1.getCoins()).thenReturn(6);
        when(p2.getCoins()).thenReturn(3);
        when(p3.getCoins()).thenReturn(1);

        when(player.getNumberOfCards()).thenReturn(3);
        when(p1.getNumberOfCards()).thenReturn(1);
        when(p2.getNumberOfCards()).thenReturn(1);
        when(p3.getNumberOfCards()).thenReturn(1);

        when(player.getCitySize()).thenReturn(3);
        when(p1.getCitySize()).thenReturn(5);
        when(p2.getCitySize()).thenReturn(4);
        when(p3.getCitySize()).thenReturn(3);


        assertTrue(strategy.bestCharacterToChoose(Character.getCharactersDeck()) instanceof Architect);


    }

    @Test
    public void bestCharacterToChooseAssassin() {
        when(player.getIndex()).thenReturn(1);

        when(player.getCoins()).thenReturn(3);
        when(p1.getCoins()).thenReturn(6);
        when(p2.getCoins()).thenReturn(3);
        when(p3.getCoins()).thenReturn(1);

        when(player.getNumberOfCards()).thenReturn(3);
        when(p1.getNumberOfCards()).thenReturn(1);
        when(p2.getNumberOfCards()).thenReturn(1);
        when(p3.getNumberOfCards()).thenReturn(1);

        when(player.getCitySize()).thenReturn(3);
        when(p1.getCitySize()).thenReturn(5);
        when(p2.getCitySize()).thenReturn(4);
        when(p3.getCitySize()).thenReturn(3);

        List<Character> characters = Arrays.asList(new Wizard(gameMaster), new Thief(gameMaster), new Assassin(gameMaster));

        assertTrue(strategy.choiceArchitectOrAssassin(characters) instanceof Assassin);
    }

    @Test
    public void playerChooseKing() {
        //le joueur qui est sur le point de constuire son avant dernier tour prend le King
        //==> le tuer et il est le cible de wizard


        when(p1.getCitySize()).thenReturn(6);

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        assertNotNull(boardPlayer.getPlayer6Districts() );

        Character c = str1.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof King);
        listOfCharacters.remove(c) ;


        c = strategy.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Assassin);
        assertTrue(strategy.characterToKill() instanceof King );
        listOfCharacters.remove(c) ;

        c = str2.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Soldier);
        assertEquals(str2.playerToSoldier(), p1);

    }


    @Test
    public void playerNearToWinChooseAssassin() {
        //le joueur qui est sur le point de constuire son avant dernier tour prend le Assassin
        //==> il tue Soldier le wizard essayent de le voler

        when(player.getCitySize()).thenReturn(6);

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        assertNotNull(boardPlayer.getPlayer6Districts() );

        listOfCharacters.remove(3);

        Character c = strategy.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Assassin);
        assertTrue(strategy.characterToKill() instanceof Soldier );
        listOfCharacters.remove(c) ;

        c = str1.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Soldier);
        assertEquals(player , str1.playerToSoldier());
        listOfCharacters.remove(c) ;

        c = str2.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Bishop);
        listOfCharacters.remove(c) ;

        c = str3.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Wizard);
        assertEquals(str3.playerToWizard(), player);
        listOfCharacters.remove(c) ;

    }

    @Test
    public void playerNearToWinChooseSoldier() {
        //le joueur qui est sur le point de constuire son avant dernier tour prend le soldier
        //==> le wizard et soldier essayent de le voler

        when(player.getCitySize()).thenReturn(6);

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());
        listOfCharacters.remove(3);

        Character c = str1.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Assassin);
        listOfCharacters.remove(c);


        c = strategy.bestCharacterToChoose(listOfCharacters);
        listOfCharacters.remove(c);
        assertTrue(c instanceof   Soldier);

        c = str2.bestCharacterToChoose(listOfCharacters);
        listOfCharacters.remove(c);
        assertTrue(c instanceof Bishop);

        c = str3.bestCharacterToChoose(listOfCharacters);
        assertTrue(c instanceof Wizard);
        assertEquals(str3.playerToWizard(), player);
    }

    @Test
    void choiceWizardTest(){

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        when(player.getNumberOfCards()).thenReturn(5);
        when(p1.getNumberOfCards()).thenReturn(1);
        when(p2.getNumberOfCards()).thenReturn(2);
        when(p3.getNumberOfCards()).thenReturn(3);
        assertTrue(strategy.choiceWizard(listOfCharacters).get() instanceof Wizard);

    }

    @Test
    void choiceThiefTest(){

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        when(player.getCoins()).thenReturn(7);
        when(p1.getCoins()).thenReturn(1);
        when(p2.getCoins()).thenReturn(2);
        when(p3.getCoins()).thenReturn(3);
        assertTrue(strategy.choiceThief(listOfCharacters).get() instanceof Thief);
    }

    @Test
    void choiceArchitectTest(){

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        when(player.getCoins()).thenReturn(4);
        when(p1.getCoins()).thenReturn(1);
        when(p2.getCoins()).thenReturn(2);
        when(p3.getCoins()).thenReturn(3);

        when(player.getNumberOfCards()).thenReturn(2);
        when(p1.getNumberOfCards()).thenReturn(1);
        when(p2.getNumberOfCards()).thenReturn(2);
        when(p3.getNumberOfCards()).thenReturn(3);

        when(player.getCitySize()).thenReturn(5);
        when(p1.getCitySize()).thenReturn(1);
        when(p2.getCitySize()).thenReturn(2);
        when(p3.getCitySize()).thenReturn(3);
        assertTrue(strategy.choiceArchitect(listOfCharacters).get() instanceof Architect);
    }

    @Test
    void choiceSoldierTest(){

        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());

        when(p1.getCitySize()).thenReturn(6);

        assertTrue(strategy.choiceCharacter(listOfCharacters) instanceof Soldier);
    }

    @Test
    void chooseBetweenTest(){
        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());
        assertTrue(strategy.choosebetween(listOfCharacters, "Soldier", "Assassin", "Bishop") instanceof Soldier);
        assertTrue(strategy.choosebetween(listOfCharacters,  "Assassin", "Bishop") instanceof Assassin);
    }

}
