package fr.unice.polytech.startingpoint.app;


import fr.unice.polytech.startingpoint.guy.ConsoleGuy;
import fr.unice.polytech.startingpoint.guy.CustomRecordFormatter;
import fr.unice.polytech.startingpoint.motor.Game;

import java.util.logging.*;


public class Main {

    public static Logger logger = Logger.getLogger("MyLog");


    public static void main(String[] args) throws Exception {

        ConsoleGuy.initLogger();
        
        logger.setLevel(Level.FINE);

        Game game = new Game();
        game.initGame("BotColorFast", "BotColorBasic", "BotFast", "BotBasic", "BotSuper");
        game.playGame();
        game.endGame();

        Statistics stat = new Statistics();
        logger.setLevel(Level.INFO);
        ConsoleGuy.printInfo("");
        stat.gameStat(1000, "BotColorFast", "BotColorBasic", "BotFast", "BotBasic","BotRandom", "BotSuper");
        ConsoleGuy.printInfo("");
        stat.gameStat(1000, "BotColorFast", "BotColorFast", "BotColorFast", "BotColorFast");
    }
}

