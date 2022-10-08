package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.motor.GameMaster;

import java.util.List;
import java.util.Random;

public class StrategyRandom extends Strategy {
    Random rand = new Random();

    public StrategyRandom(Player player, GameMaster gameMaster) {
        super(player, gameMaster);
    }

    @Override

    public Districts chooseADistrict(List<Districts> districts){
        return districts.get(rand.nextInt(districts.size()));
    }

    @Override
    public Districts districtToBuild(){
        List<Districts> list = player.getHand().stream()
                .filter(d -> !player.getCity().contains(d) && d.getCardCost() <= player.getCoins()).toList();
        if(list.size() !=0){
            return list.get(rand.nextInt(list.size())) ;
        }
        return Districts.NULL ;
    }

    @Override
    public boolean chooseCoinsOrDistrict(){
        if (gameMaster.getTotalCards() == 0 )
            return true;
        return rand.nextBoolean();
    }

    public Districts giveBadDistrictForPower(){
        if(player.getCity().size() > 0){
            return player.getCity().get(rand.nextInt(player.getCity().size()));
        }
        return null;
    }

    @Override
    public boolean isBadDistrict(Districts district){
        return rand.nextBoolean();
    }

    @Override
    public Player bestPlayer(Player p){
        return gameMaster.getBoardPlayer().getPlayerRandom() ;
    }

    @Override
    public Districts bestCard(Player playerbest){
        Random rand = new Random();
        if(playerbest != null) {
            if (playerbest.getCity().size() == 0) return Districts.NULL;
            Districts d = playerbest.getCity().get(rand.nextInt(playerbest.getCity().size()));
            if (d.getCardCost() - 1 <= player.getCoins()) return d;
        }
        return Districts.NULL ;
    }

    public Character bestCharacterToChoose(List<Character> characterDeck){
        Random rand = new Random();
        return characterDeck.get(rand.nextInt(characterDeck.size()));
    }

    public boolean buyCardDestroy(Districts district){
        if (player.getCoins() == 0) return false;
        return rand.nextBoolean();
    }


}
