package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.BotColor;
import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.cards.character.*;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StrategyBasicTest {

    Player player;
    Hand hand = new Hand();
    City city = new City();
    StrategyBasic strategy;
    GameMaster gameMaster ;
    Bank bank = new Bank();
    BoardPlayer boardPlayer ;

    @BeforeEach
    void  init(){
        player = mock(Player.class);
        boardPlayer = new BoardPlayer();
        gameMaster = new GameMaster(boardPlayer, bank);
        Districts.initDistrictDeck();
        strategy = new StrategyBasic(player,gameMaster);
    }

    @Test
    public void isBadDistrictValueTest(){
        when(player.getCoins()).thenReturn(2);
        assertTrue(strategy.isBadDistrict(Districts.CITYHOTEL),"the value of card is bad because we can't build it event next tou");
    }

    @Test
    public void isBadDistrictInCityTest(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MONASTERY, Districts.TEMPLE, Districts.MANOR)));
        assertTrue(strategy.isBadDistrict(Districts.MANOR),"the card is already build");
    }

    @Test
    void chooseADistrictNotInCity(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        assertEquals(Districts.PALACE,strategy.chooseADistrict(Arrays.asList(Districts.CASTLE,Districts.PALACE)));
    }

    @Test
    void chooseADistrictMax(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        assertEquals(Districts.PALACE,strategy.chooseADistrict(Arrays.asList(Districts.CHURCH,Districts.PALACE )));
    }
    @Test
    void districtToBuildNull() {
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE, Districts.CATHEDRAL)));
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE)));
        when(player.getCoins()).thenReturn(3) ;
        assertEquals(Districts.NULL, strategy.districtToBuild(), "only CATHEDRAL is good district but don't have enough money");
    }

    @Test
    void districtToBuildCathedrall() {
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE, Districts.CATHEDRAL)));
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.PALACE)));
        when(player.getCoins()).thenReturn(5);
        assertEquals(Districts.CATHEDRAL, strategy.districtToBuild(), "The player Can Now build Cathedral");
    }

    @Test
    void CoinsOrDistrictHandEmptyFalse() {
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        when(player.getCoins()).thenReturn(5);
        when(player.getHand()).thenReturn(new Hand());
        assertFalse(strategy.chooseCoinsOrDistrict(),"choose pick a card : hand is empty") ;
    }

    @Test
    void CoinsOrDistrictCantBuildFalse() {
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE ))) ;
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        when(player.getCoins()).thenReturn(3);
        assertFalse(strategy.chooseCoinsOrDistrict(),"choose pick a card : nbBadCars is a lot") ;
    }

    @Test
    void CoinsOrDistrictTrue() {
        when(player.getHand()).thenReturn(new Hand(List.of(Districts.CHURCH)) );
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        when(player.getCoins()).thenReturn(0);
        assertTrue(strategy.chooseCoinsOrDistrict(),"don't have enoug money");
    }

    @Test
    public void placeACardTest() {
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        when(player.getCoins()).thenReturn(5);
        when(player.getHand()).thenReturn(new Hand(List.of(Districts.CITYHOTEL)));
        strategy.placeACard(Districts.CITYHOTEL,player, gameMaster);
        assertEquals(player.getCity() ,Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CITYHOTEL) );
    }

    @Test
    void bestCharachterArchitecht(){
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CITYHOTEL))) ;
        when(player.getCoins()).thenReturn(6);
        when(player.getCity()).thenReturn(new City());
        Character character = strategy.bestCharacterToChoose(Character.getCharactersDeck());
        assertTrue(character instanceof Architect);
    }

    @Test
    void bestCharachterWizard(){
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CITYHOTEL))) ;
        when(player.getCoins()).thenReturn(2);
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE, Districts.CITYHOTEL)));
        List<Character> characters = Arrays.asList(new Wizard(gameMaster), new Thief(gameMaster),  new Assassin(gameMaster));
        Character character = strategy.bestCharacterToChoose(characters);
        assertTrue(character instanceof Wizard);
    }


    @Test
    void pickCard(){
        hand = new Hand();
        when(player.getHand()).thenReturn(hand) ;
        when(player.getCity()).thenReturn(new City()) ;
        assertTrue(player.getHand().isEmpty(), "hand is empty at the beginning");
        strategy.pickACard(gameMaster) ;
        assertFalse(player.getHand().isEmpty(), "hand isin't empty now");
    }

    @Test
    void pickTwoCards(){
        hand = new Hand();
        when(player.getHand()).thenReturn(hand) ;
        when(player.getCity()).thenReturn(new City()) ;
        assertTrue(player.getHand().isEmpty(), "hand is empty at the beginning");
        strategy.pickTwoCards(gameMaster) ;
        assertEquals(2, player.getHand().size(), "hand has 2 districts now");
    }


    @Test
    void nbBadCards(){
        when(player.getCity()).thenReturn(new City(Arrays.asList(Districts.MANOR, Districts.CASTLE)));
        when(player.getHand()).thenReturn(new Hand(Arrays.asList(Districts.MANOR, Districts.CASTLE , Districts.TEMPLE, Districts.CITYHOTEL)));
        when(player.getCoins()).thenReturn(2);
        assertEquals(3,strategy.nbBadDistrict(), "MANOR,CASTLE bad: are already build / CITYHOTEl bad : not enough money");
    }

    @Test
    void playCoinsTest(){
        hand = new Hand(List.of(Districts.CHURCH)) ;
        city = new City(Arrays.asList(Districts.MANOR, Districts.CASTLE));
        Player player = new Player(1 ,hand,city, gameMaster);
        player.setCoins(0);
        StrategyBasic strategy = new StrategyBasic(player, gameMaster);
        strategy.playCoinsOrDistrict();
        assertEquals(2,player.getCoins());
        assertEquals(28,bank.getBankCoins());
    }

    @Test
    void bestCardTest(){
        when(player.getHand()).thenReturn(new Hand());
        when(player.getCity()).thenReturn(new City((Arrays.asList(Districts.TEMPLE, Districts.CASTLE, Districts.FORTRES, Districts.TAVERN))));
        when(player.getCoins()).thenReturn(2);
        assertEquals(Districts.TAVERN,strategy.bestCard(player));
    }

    @Test
    public void possibleCharacterForPlayerBeforeTest(){
        when(player.getIndex()).thenReturn(3);
        Player p = mock(Player.class);
        when(p.getIndex()).thenReturn(1);
//        strategy.setPossibleCharacterBefore(new ArrayList<>(Arrays.asList(new Assassin(gameMaster), new Architect(gameMaster), new Thief(gameMaster))));
//        assertTrue(strategy.getPossibleCharacterBefore().contains(strategy.possibleCharacterForPlayer(p)));
    }

    @Test
    public void possibleCharacterForPlayerAfterTest(){
        when(player.getIndex()).thenReturn(2);
        Player p = mock(Player.class);
        when(p.getIndex()).thenReturn(4);
//        strategy.setPossibleCharacterAfter(new ArrayList<>(Arrays.asList(new Assassin(gameMaster), new Architect(gameMaster), new Thief(gameMaster))));
//        assertTrue(strategy.getPossibleCharactersAfter().contains(strategy.possibleCharacterForPlayer(p)));
    }

    @Test
    public void UniversityDistrictToBuildTest(){
        hand = new Hand();
        hand.add(Districts.UNIVERSITY);
        city = new City();
        Player p = new Player(1, hand, city, gameMaster);
        p.setCoins(7);
        p.setScore(0);
        strategy.placeACard(Districts.UNIVERSITY,p,gameMaster);
        assertEquals(8,p.getScore());
        assertEquals(1,p.getCoins()) ;
        assertTrue(hand.isEmpty());
        assertEquals(p.getCity().get(0), Districts.UNIVERSITY);
    }

    @Test
    public void BibliothequeTest(){
        City city = new City();
        city.add(Districts.LIBRARY);
        Hand hand = new Hand();
        Player player = new Player(1, hand, city, gameMaster);
        player.setCharacter(new King(gameMaster));
        player.setCoins(0);
        strategy = new StrategyBasic(player,gameMaster);
        strategy.play();
        assertEquals(2,player.getHand().size());
    }

    @Test
    public void useLaboratoryColor(){
        Player p;
        City city = new City(new ArrayList<Districts>(Arrays.asList(Districts.CASTLE,Districts.LABORATORY))) ;
        hand =  new Hand(new ArrayList<Districts>(Arrays.asList(Districts.MANOR,Districts.CITYHOTEL,Districts.TEMPLE)));

        p = new BotColor(1,hand,city,gameMaster);
        Strategy strat = new StrategyColor(p,gameMaster);
        p.setStrategy(strat);
        p.setCoins(15);

        //le player doit vendre sa carte manor car il joue les couleurs et il a deja construit un Castle
        Districts bad = strat.giveBadDistrictForPower();
        strat.useLaboratory(bad);
        assertTrue(p.getCoins()==16);
    }

    @Test
    public void useLaboratoryBasic(){
        Player p;
        City city = new City(new ArrayList<Districts>(Arrays.asList(Districts.CASTLE,Districts.LABORATORY))) ;
        hand =  new Hand(new ArrayList<Districts>(Arrays.asList(Districts.CASTLE,Districts.CITYHOTEL,Districts.TEMPLE)));

        p = new BotColor(1,hand,city,gameMaster);
        Strategy strat = new StrategyBasic(p,gameMaster);
        p.setStrategy(strat);
        p.setCoins(15);

        //le player doit vendre sa carte castle car il l'a deja construite
        Districts bad = strat.giveBadDistrictForPower();
        strat.useLaboratory(bad);
        assertTrue(p.getCoins()==16);
    }
    @Test
    void getCharacterByNameTest(){
        List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());
        assertTrue(strategy.getCharacterByName(listOfCharacters, "Soldier").get() instanceof Soldier);
    }



}





