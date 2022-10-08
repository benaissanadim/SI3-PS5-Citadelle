package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.GameMaster;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * this is the basic strategy for a player
 * he take his coin bonus at the begining of his turn
 * he play the first district he can
 *
 */
public class StrategyBasic extends Strategy {

    public StrategyBasic(Player player, GameMaster gameMaster) {
        super(player, gameMaster);
    }

    @Override // give the first district considered bad according to the strategy
    public Districts giveBadDistrictForPower(){
        for(Districts district : player.getCity()){
            if (player.getCity().contains(district)) return district;
        }
        return null;
    }

    @Override  //done
    public boolean chooseCoinsOrDistrict(){
        if (gameMaster.getTotalCards() <= 3) return true;
        return nbGoodDisrict() >0 && player.getHand().size() != 0
                && gameMaster.getBank().getBankCoins() >=2;
    }

    @Override // done
    public Districts chooseADistrict(List<Districts> districts){
        Optional<Districts> district = districts.stream().filter(d->!player.getCity().contains(d))
                .max(Comparator.comparingInt(Districts::getCardPoint));
        return district.orElseGet(() -> districts.get(0));
    }

    @Override //done
    public Districts districtToBuild(){
        Districts max = Districts.NULL ;
        for (Districts d : player.getHand()) {
            if (!player.getCity().contains(d) && d.getCardCost() <= player.getCoins() && max.getCardPoint() < d.getCardPoint())
                max = d;
        }
        return max ;
    }

    @Override //done
    public boolean isBadDistrict(Districts district){
        return district.getCardCost() > player.getCoins() + 2 || player.getCity().contains(district);
    }

    @Override
    public Player bestPlayer(Player p){
        return gameMaster.getBoardPlayer().getPlayerWithLargestCity(p);
    }



    @Override
    public Districts bestCard(Player playerbest){
        if(playerbest != null) {
            Districts d = playerbest.getCity().minDistrict();
            if (d != null && d.getCardCost() - 1 <= player.getCoins()) return d;
        }
        return Districts.NULL ;
    }

    public boolean buyCardDestroy(Districts district){
        return player.getCoins() != 0 && !player.getCity().contains(district);
    }

}