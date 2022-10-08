package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.King;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameMaster {

    private final BoardPlayer boardPlayer;
    private final Bank bank;
    private Player playerFirst;
    private Character characterKilled;
    private Character characterThiefed ;
    private Player playerThiefer;
    private Player playerWhoGetCemetery = null;

    private Player playerKing = null;

    private int totalCards;

    public static int turn;

    public GameMaster(BoardPlayer boardPlayer, Bank bank) {
        this.boardPlayer = boardPlayer;
        this.bank = bank;
    }

    public BoardPlayer getBoardPlayer() {
        return (BoardPlayer) boardPlayer.clone();
    }

    public int getBankCoins(){
        return bank.getBankCoins();
    }

    /**
     * check if the player can place the card in the city
     * @param districts  card
     * @param player player in game
     * @return true if card can be placed
     */
    public boolean checkIfCardCanBePlaced(Districts districts, Player player) {
        return (player.getCoins() >= districts.getCardCost()
                && !player.getCity().contains(districts)
                && districts != Districts.NULL
        );
    }

    /**
     *  place a card in city
     * @param player player in game
     * @param districts card
     */
    public void playerPlaceACard(Player player, Districts districts){
        player.getCity().add(districts);
        player.getHand().remove(districts);
        bank.removeCoinsToPlayer(districts.getCardCost(), player);
        player.setScore(player.getScore() + districts.getCardPoint());
        ConsoleGuy.printPlayerPlayDistrict(player, districts);
    }

    /**
     * get random district
     * @return random disreict
     */
    public Districts getARandomDistrict(){
        return Districts.randomDistrict();
    }

    /**
     * change list of card in player hand with deck cards
     * @param p player
     */
    public void changeCardsWithDeck(Player p){
        Hand hand = p.getHand();
        List<Districts> DistrictsExchanged = new ArrayList<>() ;
        for (Iterator<Districts> it = hand.iterator(); it.hasNext(); ){
            Districts d = it.next() ;
            if(p.getStrategy().isBadDistrict(d)){
                Districts.addCard(d);
                it.remove();
                Districts pickedCard = getARandomDistrict() ;
                Districts.removeCard(pickedCard);
                DistrictsExchanged.add(pickedCard);
                ConsoleGuy.printExchangeCardWithDeck(p.getPlayerNumber(), d, pickedCard);
            }
        }
        hand.addAll(DistrictsExchanged);
    }


    /** add a district to player hand
     * @param districts card districts
     * @param player player
     * @param addMessage msg
     */
    public void addDistrictToPlayerHand(Districts districts, Player player, boolean addMessage) {
        player.getHand().add(districts);
        if (addMessage) {
            Districts.removeCard(districts);
            ConsoleGuy.printPlayerPickACard(player);
        }
    }

    /**
     * set for the player a character
     * @param character character
     * @param player player
     * @param characterDeck list of character
     */
    public void setCharacterToPlayer(Character character, Player player, List<Character> characterDeck) {
        characterDeck.remove(character);
        player.setCharacter(character);
        ConsoleGuy.printCharacter( player.getPlayerNumber(), character);
        if (character instanceof King) {
            setPlayerKing(player);
        }

    }

    /**
     * change a card in hand with a card from deck
     * @param player player
     * @param district district card
     */
    public void changeOneCardWithDeck(Player player,Districts district){
        player.getHand().remove(district);
        Districts.addCard(district);
    }

    /**
     *
     * @param player player
     * @param character character
     * @return true if player has the that character
     */
    public boolean checkIfPlayerIsCharacter(Player player, Character character) {
        return player.getCharacter().toString().equals(character.toString());
    }

    /**
     * add for the player score an amount
     * @param amount number related to score
     * @param player player
     */
    public void addScoreToPlayer(int amount, Player player) {
        player.setScore(player.getScore() + amount);
    }

    /**
     * Calcul the gold bonus with the color for the player
     */
    public int coinColorBonus(Player player, Character character) {
        int amount=0;
        if (player.getCity().contains(Districts.MAGICSCHOOL))
            amount+=1;
        for(Districts districts : player.getCity()){
            if(districts.getDistrictColor().equals(character.getCharacterColor()))
                amount++;
        }
        return amount;
    }


    /**
     * check the bonus for the player from a character
     * @param player player
     * @param character character
     */
    public void checkCoinColorBonus(Player player, Character character) {
        int bonus = coinColorBonus(player, character);
        if (bonus != 0)
            bank.addBonusCoinsToPlayer(bonus,player,"every district for same color as character");
    }

    /**
     *  check if a character is killed
     * @param character character
     * @return true if the character is killed
     */
    public boolean checkCharacterKilled(Character character){
        if(character.equals(characterKilled)){
            ConsoleGuy.print("This player is killed for this turn");
            return true ;
        }
        return false;
    }

    /**
     * check if character is thiefed and transfer coins to thiefer
     * @param character character
     * @param player player
     * @return true if character is thiefed
     */
    public boolean checkCharacterThiefed(Character character, Player player){
        if (character.equals(characterThiefed)) {
            bank.transfertCoinsFromTo(player, playerThiefer, player.getCoins() );
            return true;
        }
        return false;
    }

    /**
     * change hand form player1 to player 2
     * @param player player1
     * @param other player2
     */
    public void changePlayersHand(Player player, Player other){
        Hand tmp = new Hand(player.getHand());
        player.setHand(other.getHand());
        other.setHand(tmp);
        ConsoleGuy.print("---> player " + player.getPlayerNumber() + " change his hand with player " + other.getPlayerNumber());
    }

    /**
     * reset turn to beginning
     */
    public void resetTurnPower(){
        characterKilled = null;
        characterThiefed = null;
        playerThiefer = null;
    }

    public Player getPlayerFirst() {
        return playerFirst;
    }

    public void setPlayerFirst(Player playerFirst) {
        this.playerFirst = playerFirst;
    }

    public Character getCharacterKilled() {
        return characterKilled;
    }

    public void setCharacterKilled(Character characterKilled) {
        this.characterKilled = characterKilled;
        ConsoleGuy.printCharacterKilled(characterKilled);
    }

    /**
     * verif if king is in game to sort boardPlayer for character choice
     */
    public void verifKing(){
        if (playerKing != null) {
            boardPlayer.setIndexes(playerKing.getIndex());
            boardPlayer.sort(Player.ComparatorIndex);
        }
    }

    public void setCharacterThiefed(Character characterThiefed) {
        this.characterThiefed = characterThiefed;
        ConsoleGuy.printCharacterThiefed(characterThiefed);
    }

    public Character getCharacterThiefed() {
        return characterThiefed;
    }


    public Bank getBank(){
        return this.bank ;
    }

    public void setPlayerThiefer(Player player){
        this.playerThiefer = player ;
    }

    public Player getPlayerKing() {
        return playerKing;
    }

    public void setPlayerKing(Player playerKing) {
        this.playerKing = playerKing;
    }

    public Player getPlayerWhoGetCemetery() {
        return playerWhoGetCemetery;
    }

    public void setPlayerWhoGetCemetery(Player playerWhoGetCemetery) {
        this.playerWhoGetCemetery = playerWhoGetCemetery;
    }

    public int getTotalCards(){
        totalCards = 0;
        Districts.getDistrictDeck().forEach((k,v)->totalCards+=v);
        return totalCards;
    }


}
