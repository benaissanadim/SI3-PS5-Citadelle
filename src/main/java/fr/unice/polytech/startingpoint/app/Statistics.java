package fr.unice.polytech.startingpoint.app;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.motor.Game;

import java.util.*;

import fr.unice.polytech.startingpoint.guy.ConsoleGuy;

import org.apache.commons.math3.util.Precision;

public class Statistics {
    String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
    String statisticsResultFile = "save/results.csv";

    final int precisionRound = 2;
    final String[] endParagraph = {"^"}; // pour detecter la fin d'un paragraphe lors de la lecture

    private final OpenCSV CSV = new OpenCSV();

    public void gameStat(int nbGame, String ... players) throws Exception {

        Map<Integer, List<Integer>> winner = new HashMap<>();
        for(int i = 0 ; i < players.length ; i++)
            winner.put(i+1, Arrays.asList(0,0));

        for (int i = 0; i < nbGame; i++){

            Game game = new Game();
            game.initGame(players);
            game.playGame();
            game.endGame();

            int j = 0 ;

            for (Player player : game.getBoardPlayer()){
                int score = winner.get(player.getPlayerNumber()).get(0);
                int nbWin = winner.get(player.getPlayerNumber()).get(1);
                winner.replace(player.getPlayerNumber(),Arrays.asList(score+player.getScore() , (j++==0) ? nbWin+1: nbWin));
            }
        }

        StatParagraph stats = new StatParagraph();

        for (int i =1 ; i<=players.length ; i++) {
            int totalScore = winner.get(i).get(0);
            int totalWin = winner.get(i).get(1);
            int totalLoses = nbGame - totalWin;

            double averageScore = Precision.round(((double) totalScore / nbGame),precisionRound)  ;
            double losePercentage = Precision.round(((double)  totalLoses/nbGame * 100),precisionRound)  ;
            double winPercentage = Precision.round(((double)  totalWin/nbGame * 100),precisionRound)  ;

            ConsoleGuy.printInfo(fixedLengthString(players[i-1],20) + fixedLengthString("total Win: "+totalWin,20)+fixedLengthString("\ttotal Loses: "+totalLoses,20)+fixedLengthString("\tWin: "+winPercentage+"%",10)+fixedLengthString("\tLoses: "+losePercentage+"%",10)+fixedLengthString( "\tAverage: "+averageScore,10));

            String[] statLine = {players[i-1],""+nbGame,""+totalWin,""+totalLoses,""+winPercentage,""+losePercentage,""+averageScore};
            stats.add(statLine);



        }


        CSV.readAndUpdateCSV(stats);
    }

    public String fixedLengthString(String string, int length) { // permet de choisir la largeur min d'une chaine de caractère (pour l'alignement)
        return String.format("%-"+length+"s", string);
    }






}
