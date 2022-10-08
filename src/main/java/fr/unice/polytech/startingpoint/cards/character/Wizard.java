package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;


public class Wizard extends Character {
    public Wizard(GameMaster game) {
        super(game, "Wizard" ,3);
    }

    @Override
    public void usePower(){
        Strategy strategy = getPlayer().getStrategy() ;
        Player p = strategy.playerToWizard();

        if( strategy.nbBadDistrict() > getPlayer().getNumberOfCards() /2
                && p.getNumberOfCards() >= getPlayer().getNumberOfCards() )
            gameMaster.changePlayersHand(getPlayer(),p);

        else if(getPlayer().getStrategy().nbBadDistrict() >0){
            gameMaster.changeCardsWithDeck(getPlayer());
        }
    }




}