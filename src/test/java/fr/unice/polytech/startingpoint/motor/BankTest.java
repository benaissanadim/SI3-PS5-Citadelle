package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BankTest {
    Bank bank;
    GameMaster gameMaster ;

    Player player1;
    Player player2;

    Hand hand;
    City city = new City();

    @BeforeEach
    void setUp() {
        bank = new Bank();
        gameMaster=mock(GameMaster.class);
        hand = mock(Hand.class);
        city = mock(City.class) ;

        player1 = new Player(1, hand, city, gameMaster);
        player2 = new Player(2, hand, city, gameMaster);
    }

    @Test
    void addCoinsToPlayer() {
        bank.addCoinsToPlayer(5, player1);
        assertEquals(25, bank.getBankCoins());
        assertEquals(5, player1.getCoins());

        bank.addCoinsToPlayer(20, player2);
        assertEquals(5, bank.getBankCoins());
        assertEquals(20, player2.getCoins());


        //The bank can't give more than she have
        bank.addCoinsToPlayer(10 ,player1);
        assertEquals(0, bank.getBankCoins());
        assertEquals(10, player1.getCoins());
    }

    @Test
    void addBonusCoinsToPlayer() {
        bank.addBonusCoinsToPlayer(12, player1, "Bonus de test 1");
        assertEquals(18, bank.getBankCoins());
        assertEquals(12, player1.getCoins());

        bank.addBonusCoinsToPlayer(10, player2, "Test bonus 2");
        assertEquals(8, bank.getBankCoins());
        assertEquals(10, player2.getCoins());


        //The bank can't give more than she have
        bank.addBonusCoinsToPlayer(15 ,player1, "Test3");
        assertEquals(0, bank.getBankCoins());
        assertEquals(20, player1.getCoins());
    }

    @Test
    void removeCoinsToPlayer() {
        bank.addCoinsToPlayer(17 ,player1);

        bank.addCoinsToPlayer(20 ,player2);

        bank.removeCoinsToPlayer(5 ,player1);
        assertEquals(5, bank.getBankCoins());
        assertEquals(12, player1.getCoins());

        bank.removeCoinsToPlayer(8 ,player2);
        assertEquals(13, bank.getBankCoins());
        assertEquals(5, player2.getCoins());

        bank.removeCoinsToPlayer(10 ,player2);
        assertEquals(13, bank.getBankCoins());
        assertEquals(5, player2.getCoins());

    }

    @Test
    void transfertCoinsFromTo() {
        bank.addCoinsToPlayer(17 ,player1);

        bank.addCoinsToPlayer(20 ,player2);

        bank.transfertCoinsFromTo(player1, player2, 5);

        assertEquals(12, player1.getCoins());
        assertEquals(18, player2.getCoins());

        bank.transfertCoinsFromTo(player1, player2, 20);

        assertEquals(0, player1.getCoins());
        assertEquals(30, player2.getCoins());
    }

}