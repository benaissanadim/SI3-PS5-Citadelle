package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.guy.ANSI;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.StrategyColor;
import fr.unice.polytech.startingpoint.strategy.StrategyFast;

public class BotColorFast extends Player{

    public BotColorFast(int playerNumber, Hand hand, City city, GameMaster gameMaster) {
        super(playerNumber, hand, city, gameMaster);
        this.strategy = new StrategyColor(this, gameMaster);
    }

    @Override
    public void play(){
        if(checkColorToBuild() >= 5){
            setStrategy(new StrategyFast(this,gameMaster));
            ConsoleGuy.print(ANSI.ANSI_PURPLE  + " player :" + getPlayerNumber() + " Change his strategy to strategy Fast" + ANSI.ANSI_RESET);
        }
        strategy.play();
    }
}