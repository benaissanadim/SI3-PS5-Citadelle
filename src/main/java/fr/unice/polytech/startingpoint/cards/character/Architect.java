package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.motor.GameMaster;

public class Architect extends Character {

    public Architect(GameMaster game) {
        super(game, "Architect" ,7);
    }

    @Override
    public void usePower() {
        if(gameMaster.getTotalCards()>=2){
            for (int i = 0; i < 2; i++){
                getPlayer().getStrategy().pickACard(gameMaster);
            }
        }
        getPlayer().getStrategy().setNbToBuild(3);
    }
}
