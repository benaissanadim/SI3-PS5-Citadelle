package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.Strategy;

public class Thief extends Character {

    public Thief(GameMaster game) {
        super(game, "Thief" ,2);
    }

    @Override
    public void usePower(){
        Strategy strategy = getPlayer().getStrategy() ;
        Character characterToThief ;

        do{
            characterToThief = strategy.characterToThief();
        } while(characterToThief instanceof Assassin);

        gameMaster.setCharacterThiefed(characterToThief);
        gameMaster.setPlayerThiefer(getPlayer());
    }
}
