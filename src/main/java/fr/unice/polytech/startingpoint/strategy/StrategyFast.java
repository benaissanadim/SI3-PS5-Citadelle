package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.GameMaster;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class StrategyFast extends Strategy {

    public StrategyFast(Player player, GameMaster gameMaster) {
        super(player, gameMaster);
    }

    @Override
    public Districts districtToBuild(){
        Districts min = Districts.NULL ;
        for (Districts d : player.getHand()) {
            if (!player.getCity().contains(d) && d.getCardCost() <= player.getCoins() )
                if(min.getCardCost() ==0 || min.getCardPoint() > d.getCardPoint()) min = d ;
        }
        return min ;
    }

    @Override
    public boolean chooseCoinsOrDistrict(){
        if (gameMaster.getTotalCards() <= 3) return true;
        return (nbGoodDisrict() >0 && player.getCoins() <=3 &&
                player.getHand().size() != 0 && gameMaster.getBank().getBankCoins() >=2);
    }

    @Override
    public Districts chooseADistrict(List<Districts> districts) {
        Optional<Districts> district = districts.stream().filter(d -> !player.getCity().contains(d))
                .min(Comparator.comparingInt(Districts::getCardPoint));
        return district.orElseGet(() -> districts.get(0));
    }

    @Override
    public Districts giveBadDistrictForPower(){
        for(Districts district : player.getHand()){
            if (player.getCity().contains(district)) return district;
        }
        return null;
    }

    @Override
    public boolean isBadDistrict(Districts district){
        return player.getCity().contains(district) || district.getCardCost() > 3;
    }

    @Override
    public Player bestPlayer(Player p){
        return gameMaster.getBoardPlayer().getPlayerWithLargestCity(p) ;
    }

    @Override
    public Districts bestCard(Player playerbest){
        if(playerbest != null) {
            Districts d = playerbest.getCity().minDistrict();
            if (d!= null && d.getCardCost() - 1 <= player.getCoins()) return d;
        }
        return Districts.NULL ;
    }

    public boolean buyCardDestroy(Districts district){
        return (player.getCoins() != 0
                && !player.getCity().contains(district)
                && district.getCardCost()<3);
    }


}
