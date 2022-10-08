package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.guy.ANSI;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bank {

    private int bankCoins;

    public Bank(){
        bankCoins = 30;
    }

    /**
     * This methode add coins to a player
     * @param nb coins you want to add
     * @param player who gain coins
     */
    public void addCoinsToPlayer(int nb, Player player) {
        int amount = min(nb, getBankCoins());
        if (amount > 0) {
            player.setCoins(player.getCoins() + amount);
            bankCoins -= amount;
            ConsoleGuy.printCoinsAddedToPlayer(amount, player.getPlayerNumber());
        }
    }

    /**
     * This methode add coins to a player with a specific message
     * @param nb coins you want to add
     * @param player who gain coins
     * @param bonus message you want to display
     */
    public void addBonusCoinsToPlayer(int nb, Player player, String bonus) {
        int amount = min(nb, getBankCoins());
        if (amount > 0) {
            player.setCoins(player.getCoins() + amount);
            bankCoins -= amount;
            ConsoleGuy.printBonusCoinsAddedToPlayer(amount, player, bonus);
        }
    }

    /**
     * This methode remove coins to a player
     * @param amount coins you want to add
     * @param player who gain coins
     */
    public void removeCoinsToPlayer(int amount, Player player){
        if (player.getCoins() - amount < 0)
            ConsoleGuy.print("error : player try to remove "+ ANSI.ANSI_YELLOW  + amount +" coins"+ ANSI.ANSI_RESET +" but he only have "+ ANSI.ANSI_YELLOW + player.getCoins() + "coins"+ ANSI.ANSI_RESET);
        else {
            player.setCoins(player.getCoins() - amount);
            bankCoins += amount;
        }
    }

    /**
     * This methode transfer coins from a player to another
     * @param player1 1st player
     * @param player2 2nd player
     * @param nb coins you want to transfer
     */
    public void transfertCoinsFromTo(Player player1, Player player2, int nb){
        int amount = min(nb, player1.getCoins());
        player1.setCoins(player1.getCoins() - amount);
        player2.setCoins(player2.getCoins() + amount);
        ConsoleGuy.printCoinsTransfered(player1, player2, amount);
    }

    public int getBankCoins(){
        return bankCoins;
    }

}
