package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;

public class Assassin extends Character {

    public Assassin(GameMaster game) {
        super(game, "Assassin" ,1);
    }

    @Override
    public void usePower(){
        Strategy strategy = getPlayer().getStrategy() ;
        gameMaster.setCharacterKilled(strategy.characterToKill());
    }
}


