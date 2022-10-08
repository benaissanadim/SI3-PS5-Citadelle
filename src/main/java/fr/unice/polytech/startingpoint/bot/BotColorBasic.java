package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.motor.City;
import fr.unice.polytech.startingpoint.motor.Hand;
import fr.unice.polytech.startingpoint.guy.ANSI;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import fr.unice.polytech.startingpoint.strategy.StrategyBasic;
import fr.unice.polytech.startingpoint.strategy.StrategyColor;

public class BotColorBasic extends Player{

    public BotColorBasic(int playerNumber, Hand hand, City city, GameMaster gameMaster) {
        super(playerNumber, hand, city, gameMaster);
        this.strategy = new StrategyColor(this, gameMaster);
    }

    @Override
    public void play(){
        if(checkColorToBuild() >= 5){
            setStrategy(new StrategyBasic(this,gameMaster));
            ConsoleGuy.print(ANSI.ANSI_PURPLE  + " player :" + getPlayerNumber() + "Change is strategy to strategy Basic" + ANSI.ANSI_RESET);
        }
        strategy.play();
    }
}
