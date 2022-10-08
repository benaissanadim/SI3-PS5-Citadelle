package fr.unice.polytech.startingpoint.bot;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {

    private final int playerNumber;
    public int index ;
    private Hand hand;
    private City city;

    protected final GameMaster gameMaster;
    public ArrayList<Character> possibleCharactersBefore = new ArrayList<>();
    public ArrayList<Character> possibleCharactersAfter = new ArrayList<>();

    protected Character character;

    private int score;
    private int coins;

    protected Strategy strategy;

    public Player(int playerNumber, Hand hand, City city, GameMaster gameMaster) {
        this.playerNumber = playerNumber;
        index = playerNumber ;
        this.hand = hand;
        this.city = city;
        this.gameMaster = gameMaster;
        this.coins = 0;
        this.score = 0;
    }

    public static Comparator<Player> ComparatorIndex = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p1.index - p2.index;
        }
    };

    public static Comparator<Player> ComparatorScore = new Comparator<Player>() {
        @Override
            public int compare(Player p1, Player p2) {
            return (p1.score == p2.score)
                    ? p2.getCharacter().getId() - p1.getCharacter().getId()
                    : p2.score - p1.score ;
        }
    };

    /**
     * @return the number of the differente color in the city
     */
    int checkColorToBuild(){
        return getCity().setOfColors().size();
    }


    /**
     * Play the strategi of the player
     */
    public void play() {
        strategy.play();
    }

    /**
     * Choose a character with the strategie of the player
     * @param list of character
     */
    public void pickCharacter(List<Character> list){
        strategy.pickCharacter(list);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index){
        this.index = index ;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand h){
        this.hand = h;
    }

    public City getCity() {
        return city;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getNumberOfCards(){return hand.size();}

    public int getCitySize(){return city.size();}


    public void setCity(City city) {
        this.city = city ;
    }

    public boolean cityHasFiveColors() {
        return city.checkFiveColor() ;
    }

    public int cityNbColors(){
        return city.nbColors() ;
    }

    public void setPossibleCharacterBefore(ArrayList<Character> characters){
        possibleCharactersBefore = characters ;
    }

    public  void setPossibleCharacterAfter(ArrayList<Character> characters){
        possibleCharactersAfter = characters ;
    }

    public ArrayList<Character> getPossibleCharactersBefore(){
        return possibleCharactersBefore;
    }

    public ArrayList<Character> getPossibleCharactersAfter(){
        return possibleCharactersAfter;
    }

    public boolean hasCardWith1PO(){
        return getCity().hasCardWith1PO();
    }
}
