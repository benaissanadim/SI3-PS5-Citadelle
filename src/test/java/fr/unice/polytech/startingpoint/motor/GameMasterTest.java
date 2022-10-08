package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.Assassin;
import fr.unice.polytech.startingpoint.cards.character.King;
import fr.unice.polytech.startingpoint.cards.character.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameMasterTest {
    Player player1;
    Hand hand1 = new Hand();
    City city1 = new City();

    Player player2;
    Hand hand2 = new Hand();
    City city2 = new City();

    Player player3;
    Hand hand3 = new Hand();
    City city3 = new City();

    Player player4;
    Hand hand4 = new Hand();
    City city4 = new City();

    BoardPlayer boardPlayers = new BoardPlayer();
    GameMaster gameMaster = new GameMaster(boardPlayers,new Bank() ) ;

    @BeforeEach
    void init(){
        Districts.initDistrictDeck();
        boardPlayers.add(player1 = new BotColor(1, hand1,city1, gameMaster));
        boardPlayers.add(player2 = new BotFast(2, hand2,city2, gameMaster));
        boardPlayers.add(player3 = new BotRandom(3,  hand3,city3, gameMaster));
        boardPlayers.add(player4 = new BotBasic(4,  hand4,city4, gameMaster));
    }

    @Test
    void checkIfCardCanBePlaced() {
        city1 = new City(List.of(Districts.CASTLE, Districts.TEMPLE, Districts.JAIL));
        player1.setCity(city1);
        player1.setCoins(5);
        assertFalse(gameMaster.checkIfCardCanBePlaced(Districts.CASTLE, player1));
        assertTrue(gameMaster.checkIfCardCanBePlaced(Districts.MONASTERY, player1));
        assertFalse(gameMaster.checkIfCardCanBePlaced(Districts.NULL, player1));
    }

    @Test
    void playerPlaceACard() {
        city1 = new City(List.of(Districts.CASTLE, Districts.TEMPLE, Districts.JAIL));
        hand1 = new Hand(List.of(Districts.TAVERN, Districts.MARKET, Districts.PORT));
        player1.setCity(city1);
        player1.setHand(hand1);
        player1.setCoins(5);

        gameMaster.playerPlaceACard(player1, Districts.PORT);
        assertEquals(List.of(Districts.CASTLE, Districts.TEMPLE, Districts.JAIL, Districts.PORT), city1);
        assertEquals(List.of(Districts.TAVERN, Districts.MARKET), hand1);
        assertEquals(1, player1.getCoins());
    }

    @Test
    void addDistrictToPlayerHand() {
        gameMaster.addDistrictToPlayerHand(Districts.PORT, player1, true);
        assertEquals(List.of(Districts.PORT), hand1);
    }

    @Test
    void checkIfPlayerIsCharacter() {
        player1.setCharacter(new Wizard(gameMaster));

        assertTrue(gameMaster.checkIfPlayerIsCharacter(player1, new Wizard(gameMaster)));
        assertFalse(gameMaster.checkIfPlayerIsCharacter(player1,new Assassin(gameMaster)));
    }

    @Test
    void addScoreToPlayer() {
        player1.setScore(0);
        gameMaster.addScoreToPlayer(10,player1);
        assertEquals(player1.getScore(),10);

        gameMaster.addScoreToPlayer(4,player1);
        assertNotEquals(player1.getScore(),15);
    }

    @Test
    void coinColorBonus() {
        city1 = new City(Arrays.asList(Districts.CASTLE, Districts.PALACE, Districts.TEMPLE, Districts.MANOR));
        player1.setCity(city1);

        assertEquals(3,gameMaster.coinColorBonus(player1,new King(gameMaster)));

    }

    @Test
    void checkCharacterKilled() {
        King king = new King(gameMaster);
        gameMaster.setCharacterKilled(king);
        assertTrue(gameMaster.checkCharacterKilled(king)) ;
    }

    @Test
    void checkCharacterThiefed() {
        King king = new King(gameMaster);
        gameMaster.setCharacterThiefed(king);
        player2.setCoins(5);
        gameMaster.setPlayerThiefer(player2);
        player1.setCharacter(king);
        player1.setCoins(2);
        assertTrue(gameMaster.checkCharacterThiefed(king,player1)) ;
        assertEquals(7,player2.getCoins());
    }

    @Test
    void changePlayersHand() {
        hand1 = new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE));
        player1.setHand(hand1);
        hand2 = new Hand(List.of(Districts.CITYHOTEL));
        player2.setHand(hand2);
        gameMaster.changePlayersHand(player1,player2);
        assertEquals(player1.getHand(), hand2);
        assertEquals(player2.getHand(), hand1);
    }

}