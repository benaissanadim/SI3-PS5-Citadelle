package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.*;

import fr.unice.polytech.startingpoint.cards.character.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    Game game ;

    @BeforeEach
    void init(){

        game = new Game() ;
    }

    @Test
    void initGame4Players(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic" );
        assertTrue(game.getBoardPlayer().get(0) instanceof BotColorFast);
        assertTrue(game.getBoardPlayer().get(1) instanceof BotColorBasic);
        assertTrue(game.getBoardPlayer().get(2) instanceof BotFast);
        assertTrue(game.getBoardPlayer().get(3) instanceof BotBasic);

        assertEquals(4, game.getBoardPlayer().size());
        for(Player p : game.getBoardPlayer()){
            assertEquals(2,p.getCoins());
            assertEquals(4,p.getNumberOfCards());
        }
    }

    @Test
    void initCharacters4Players(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic" );

        List<Character> characterDeck = new ArrayList<>(game.getListOfCharacters()); // la pioche des personnage
        List<Character> visible = new ArrayList<>() ;
        game.initCharacters(characterDeck,visible);
        assertEquals(2,visible.size()) ;
        assertEquals(5, characterDeck.size());
        for(Character v : visible ){
            assertFalse(characterDeck.contains(v));
        }
    }
    @Test
    void initCharacters5Players(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic", "BotRandom" );

        List<Character> characterDeck = new ArrayList<>(game.getListOfCharacters()); // la pioche des personnage
        List<Character> visible = new ArrayList<>() ;
        game.initCharacters(characterDeck,visible);
        assertEquals(1,visible.size()) ;
        assertEquals(6, characterDeck.size());
        for(Character v : visible ){
            assertFalse(characterDeck.contains(v));
        }
    }
    @Test
    void initCharacters6Players(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic", "BotRandom", "BotRandom" );

        List<Character> characterDeck = new ArrayList<>(game.getListOfCharacters()); // la pioche des personnage
        List<Character> visible = new ArrayList<>() ;
        game.initCharacters(characterDeck,visible);
        assertEquals(0,visible.size()) ;
        assertEquals(7, characterDeck.size());
        for(Character v : visible ){
            assertFalse(characterDeck.contains(v));
        }
    }

    @Test
    void initCharacters7Players(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic", "BotRandom", "BotRandom", "BotRandom" );

        List<Character> characterDeck = new ArrayList<>(game.getListOfCharacters()); // la pioche des personnage
        List<Character> visible = new ArrayList<>() ;
        game.initCharacters(characterDeck,visible);
        assertEquals(0,visible.size()) ;
        assertEquals(7, characterDeck.size());
        for(Character v : visible ){
            assertFalse(characterDeck.contains(v));
        }
    }

    @Test
    void chooseCharacters4Players(){
        List<Character> characterDeck = new ArrayList<>(game.getListOfCharacters()); // la pioche des personnage
        List<Character> visible = new ArrayList<>() ;

        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic" );

        game.initCharacters(characterDeck,visible);
        game.chooseCharacters(characterDeck,visible);
        assertEquals(1, characterDeck.size());
        for( Player p : game.getBoardPlayer()){
            assertNotNull(p.getCharacter());
            assertNotNull(p.possibleCharactersAfter);
            assertNotNull(p.possibleCharactersBefore);
        }
    }



    @Test
    void playGame(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic" );
        game.playGame();
        for( Player p : game.getBoardPlayer()){
            assertNotEquals(0,p.getScore());
            assertNotEquals(0,p.getCitySize());
        }
    }

    @Test
    void endGame(){
        game.initGame("BotColorFast" , "BotColorBasic", "BotFast" , "BotBasic" );
        game.playGame();
        game.endGame();
        for( int i=0 ; i< game.getBoardPlayer().size() -1 ; i++){
            assertTrue(game.getBoardPlayer().get(i).getScore() >= game.getBoardPlayer().get(i).getScore());
        }
    }


}
