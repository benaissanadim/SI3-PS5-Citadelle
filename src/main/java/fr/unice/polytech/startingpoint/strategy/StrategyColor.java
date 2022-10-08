package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.GameMaster;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StrategyColor extends Strategy{

    public StrategyColor(Player player, GameMaster gameMaster) {
        super(player, gameMaster);
    }


    @Override
    public Character bestCharacterToChoose(List<Character> characterDeck){
        return maxBonusFromCharacterColor(characterDeck);
    }

    @Override
    public boolean chooseCoinsOrDistrict(){
        if (gameMaster.getTotalCards() <= 3) return true;
        return ( nbGoodDisrict() >0  )
                && (player.getHand().size() != 0 )
                && gameMaster.getBank().getBankCoins() >=2;
    }

    @Override
    public Districts chooseADistrict(List<Districts> districts){
        Optional<Districts> district = districts.stream().filter(d->!player.getCity().containColor(d))
                .max(Comparator.comparingInt(Districts::getCardPoint));
        if(district.isPresent()) return district.get();

        else district = districts.stream().filter(d->!player.getCity().contains(d))
                .max(Comparator.comparingInt(Districts::getCardPoint));
        return district.orElseGet(() -> districts.get(0));
    }

    @Override
    public Districts districtToBuild(){
        List<Districts> districts= player.getHand().stream()
                .filter(d -> !player.getCity().contains(d) && d.getCardCost() <= player.getCoins()).toList();
        if(districts.size() != 0) return chooseADistrict(districts) ;
        return Districts.NULL;
    }


    @Override // give the first district considered bad according to the strategy
    public Districts giveBadDistrictForPower(){
        for(Districts district : player.getCity()){
            if (player.getCity().contains(district) || player.getCity().containColor(district)) return district;
        }
        return null;
    }

    public boolean isBadDistrict(Districts district){
        return (district.getCardCost() > player.getCoins() + 2 || player.getCity().containColor(district))
        && player.getCity().contains(district) ;
    }

    public Player bestPlayer(Player p){
        return gameMaster.getBoardPlayer().gePlayerWithManyColors(player);
    }

    /**
     * the best card to destroy is which has the minimum value to not loose a lot money
     */
    public Districts bestCard(Player playerbest){
        if(playerbest != null){
            Districts d =  playerbest.getCity().districtUniqColor();
            if(d.getCardCost() -1 <= player.getCoins()) return d;
        }
        return Districts.NULL ;
    }

    public boolean buyCardDestroy(Districts district){
        return (player.getCoins() != 0
                && !player.getCity().contains(district)
                && !player.getCity().containColor(district));
    }

}
