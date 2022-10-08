package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

public class BoardPlayer extends ArrayList<Player> {

    public BoardPlayer(){
    }


    /**
     * @param player the player who call this methode
     * @return the player with most card
     */
    public Player getPlayerWithMostCards(Player player){
        return stream().filter(p->p!=player)
                .max(Comparator.comparingInt(Player::getNumberOfCards)).get();
    }

    /**
     * @return the player with the highest score
     */
    public Player getPlayerWithHighestScore(){
        int max = 0 ;
        Player playerScore = null ;
        for (Player p : this){
            if(p.getScore() >=max) {
                max = p.getScore() ;
                playerScore = p ;
            }
        }
        return playerScore ;
    }

    /**
     *
     * @return the average amount of coins from each player
     */
    public double averageGoldPerPlayer(){
        double goldAmount = 0;
        for(Player p : this){
            goldAmount += p.getCoins();
        }
        if(!this.isEmpty()){
            return  goldAmount/this.size();
        }
        return 0;

    }

    /**
     *
     * @return the average amount of cards in each player's hand
     */
    public double averageCardPerPlayerHand(){
        double cardAmount = 0;
        for(Player p : this){
            cardAmount += p.getNumberOfCards();
        }
        if(!this.isEmpty()){
            return cardAmount/this.size();
        }
        return 0;
    }

    /**
     * @param player the player who call this methode
     * @return the player who have the most color in his city
     */
    public Player gePlayerWithManyColors(Player player){
        return   stream().filter(p->p!=player)
                .max(Comparator.comparingInt(Player::cityNbColors)).get()  ;
    }

    /**
     * @param player the player who call this methode
     * @return the player who have the bigger city
     */
    public Player getPlayerWithLargestCity(Player player){
        return stream().filter(p->p!=player)
                .max(Comparator.comparingInt(Player::getCitySize)).get();
    }


    /**
     * @return  a random player
     */
    public Player getPlayerRandom() {
        return get(new Random().nextInt(size())) ;
    }

    /**
     * @param player the player who call this methode
     * @return the richest player
     */
    public Player getRichestPlayer(Player player){
        return stream().filter(p->p!=player)
                .max(Comparator.comparingInt(Player::getCoins)).get();
    }

    /**
     * @return the player with 6 districts
     */
    public Player getPlayer6Districts() {
        for(Player p : this)
            if (p.getCitySize() == 6 ) return p ;
        return null;
    }

    /**
     * @return the player with 7 districts
     */
    public Player getPlayer7Districts() {
        for(Player p : this)
            if (p.getCitySize() == 7 ) return p ;
        return null;
    }

    public boolean hasPlayerWithLotCoins(Player player){
        return stream().anyMatch(p -> p != player && p.getCoins() >6 );
    }

    public boolean isOnlyHasCards1PO(Player player){
        if(!player.hasCardWith1PO()) return false;
        else{
            return stream().noneMatch(p -> p != player && p.hasCardWith1PO());
        }
    }

    /**
     * this methode give an index to all player
     * @param number of the first player
     */
    public void setIndexes(int number){
        int index=1 ;
        for(int i = number-1 ; i < size() ; i++ )
            get(i).setIndex(index++);
        for(int i = 0 ; i < number -1 ; i++ )
            get(i).setIndex(index++);
    }

    public boolean hasPlayerWhoWantsArchitect() {
        return stream().anyMatch(p -> p.getCoins() >=4 && p.getNumberOfCards()>=1 && p.getCitySize() >=5 );
    }
}
