package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;



public class Score {
    private final GameMaster gameMaster;

    Score(GameMaster gameMaster){
        this.gameMaster = gameMaster;
    }

    /**
     * calcul bonus for player
     * @param player player
     * @return int bonus
     */
    public int calculBonus(Player player){
        int amount = 0;
        if (player.equals(gameMaster.getPlayerFirst())){
            amount += 4;
            ConsoleGuy.printScoreBonus(player.getPlayerNumber(), 4, "finishing first ");
        }
        else if (player.getCity().isBuild()){
            amount += 2;
            ConsoleGuy.printScoreBonus(player.getPlayerNumber(), 2, "building his city ");
        }
        if (checkFiveColor(player)) {
            amount += 3;
            ConsoleGuy.printScoreBonus(player.getPlayerNumber(), 3, "5 differents colors");
        }
        return amount;
    }


    /**
     * @param player you want to add a score
     * @param amount the amount of score you want to add
     */
    void addScore(Player player, int amount){
        gameMaster.addScoreToPlayer(amount, player);
    }

    /**
     * @param player you want to check
     * @return true if the player have all the color in his city
     */
    boolean checkFiveColor(Player player){
        return player.cityHasFiveColors();
    }
}
