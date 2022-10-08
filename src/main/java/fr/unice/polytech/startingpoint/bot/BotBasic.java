package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.StrategyBasic;

public class BotBasic extends Player{

    public BotBasic(int playerNumber, Hand hand, City city, GameMaster gameMaster) {
        super(playerNumber, hand, city, gameMaster);
        this.strategy = new StrategyBasic(this, gameMaster);
    }

}
