package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoreTest {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    BoardPlayer boardPlayers ;
    private Score score;
    GameMaster gameMaster ;

    @BeforeEach
    void init(){
        boardPlayers = mock(BoardPlayer.class) ;
        gameMaster = mock(GameMaster.class) ;
        Districts.initDistrictDeck();
        boardPlayers.add(player1 = mock(Player.class));
        boardPlayers.add(player2 = mock(Player.class));
        boardPlayers.add(player3 = mock(Player.class));
        boardPlayers.add(player4 = mock(Player.class));
        score = new Score(gameMaster);
    }



    @Test
    void calculBonusWithNoBonus() {
        when(player1.getCity()).thenReturn(new City(List.of(Districts.MANOR, Districts.TEMPLE, Districts.CASTLE, Districts.TAVERN)));
        assertEquals(0, score.calculBonus(player1), "le joueur 1 n'as pas de bonus" );
        assertEquals(0, player1.getScore());
    }

    @Test
    void calculBonusWithColorBonus() {
        when(player1.getCity()).thenReturn(new City(List.of(Districts.MANOR, Districts.TEMPLE, Districts.TAVERN, Districts.WATCHOWER)));
        //assertEquals(3, score.calculBonus(player1), "le joueur 1 a 3 points bonus" );
    }

    @Test
    void calculBonusWithFirstBonus() {
        when(gameMaster.getPlayerFirst()).thenReturn(player1) ;
        when(player1.cityHasFiveColors()).thenReturn(false) ;

        assertEquals(4, score.calculBonus(player1), "le joueur 1 a 4 points bonus" );
    }

    @Test
    void calculBonusWithColornAnddFirstBonus() {
        when(gameMaster.getPlayerFirst()).thenReturn(player1) ;
        when(player1.getCity()).thenReturn(new City(List.of(Districts.MANOR, Districts.TEMPLE, Districts.CASTLE, Districts.TAVERN, Districts.WATCHOWER)));
        //assertEquals(7, score.calculBonus(player1), "le joueur 1 a 7 points bonus" );
    }


    @Test
    void addScore() {
        GameMaster gameMaster = new GameMaster(mock(BoardPlayer.class),mock(Bank.class) ) ;
        Score score = new Score(gameMaster);
        Player player1 = new BotColor(7, new Hand(),new City(), gameMaster) ;
        player1.setScore(5);
        score.addScore(player1, 4);
        assertEquals(9, player1.getScore());
    }

    @Test
    void checkFiveColorWith3Color() {
        when(player1.getCity()).thenReturn(new City(List.of(Districts.TEMPLE, Districts.CHURCH, Districts.MANOR, Districts.TAVERN)));
        assertFalse(score.checkFiveColor(player1));
    }

    @Test
    void checkFiveColorWith4Color() {
        when(player1.getCity()).thenReturn(new City(List.of(Districts.TEMPLE, Districts.CHURCH, Districts.MANOR, Districts.TAVERN ,Districts.WATCHOWER)));
        assertFalse(score.checkFiveColor(player1));
    }
}