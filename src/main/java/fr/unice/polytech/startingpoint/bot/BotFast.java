package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.StrategyFast;

public class BotFast extends Player {

    public BotFast(int playerNumber, Hand hand, City city, GameMaster gameMaster) {
        super(playerNumber, hand, city, gameMaster);
        this.strategy = new StrategyFast(this, gameMaster);
    }

}
