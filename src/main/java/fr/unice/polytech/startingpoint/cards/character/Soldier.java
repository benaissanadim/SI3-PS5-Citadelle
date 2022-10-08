package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;

public class Soldier extends Character {

    public Soldier(GameMaster game) {
        super(game, "Soldier" ,8);
        this.color = "Red";
    }

    @Override
    public void usePower() {

        Strategy strategy = getPlayer().getStrategy() ;

        Player player = strategy.bestPlayer(getPlayer());
        Districts district = strategy.bestCard(player);

        if (!district.equals(Districts.NULL) && !(player.getCharacter() instanceof Bishop)
                && !district.equals(Districts.DUNGEON)) {

            gameMaster.getBank().removeCoinsToPlayer(district.getCardCost()-1 , getPlayer());
            ConsoleGuy.printDestruction(getPlayer(),district,player);
            player.getCity().remove(district);

            if( player == gameMaster.getPlayerWhoGetCemetery() && player.getStrategy().buyCardDestroy(district)) {
                ConsoleGuy.print("player "+player.getPlayerNumber()+ "use Cemetery Power");
                gameMaster.getBank().removeCoinsToPlayer(1, player);
                gameMaster.addDistrictToPlayerHand(district, player, false);
                ConsoleGuy.printBuyCard(player);
            }
            else player.setScore(player.getScore() - district.getCardPoint());
        }
    }

}
