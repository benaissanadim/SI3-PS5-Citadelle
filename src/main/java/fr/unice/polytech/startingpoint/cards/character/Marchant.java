package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.motor.GameMaster;

public class Marchant extends Character {

    public Marchant(GameMaster game) {
        super(game, "Marchant" ,6);
        this.color = "Green";
    }


    @Override
    public void usePower(){
        gameMaster.getBank().addBonusCoinsToPlayer(1,getPlayer(),"his power");
    }
}
