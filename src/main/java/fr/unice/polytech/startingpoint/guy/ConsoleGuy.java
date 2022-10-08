package fr.unice.polytech.startingpoint.guy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.motor.GameMaster;
import static fr.unice.polytech.startingpoint.app.Main.logger;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;


public class ConsoleGuy {


    public static void initLogger(){
        logger.setUseParentHandlers(false);
        final ConsoleHandler ch = new ConsoleHandler();

        try{
            logger.addHandler(ch);
            CustomRecordFormatter formatter = new CustomRecordFormatter();
            ch.setFormatter(formatter);
            ch.setLevel(Level.ALL);

            logger.info("Logger Initialised");
        }catch (Exception e){
            logger.log(Level.WARNING,"Exception ::",e);
        }
    }

    public static void printDebug(String message){
        logger.config(message);
    }


    public static void printTurn(int turn, int subTurn){
        logger.fine("\n********** Tour " + turn + "-" + subTurn + " **********\n");
    }

    public static void printGameState(GameMaster gameMaster){
        logger.fine("\n");
        gameMaster.getBoardPlayer().forEach(ConsoleGuy::printPlayerBoard);
        printBankCoins(gameMaster.getBankCoins());
    }

    public static void printBankCoins(int bankCoins){
        logger.fine(ANSI.ANSI_YELLOW + "$$$" + ANSI.ANSI_RESET + "---" + ANSI.ANSI_YELLOW  + " BANK COIN$ : " + bankCoins + ANSI.ANSI_RESET + " ---" + ANSI.ANSI_YELLOW + "$$$" + ANSI.ANSI_RESET);
    }

    /**
     * print all the districts in the board of the player and the total score
     */
    public static void printPlayerBoard(Player player){
        if(player.getCity().size() > 0) {
            logger.fine("Player " + player.getPlayerNumber() + " build : "+ ANSI.ANSI_BLUE + player.getCity() + ANSI.ANSI_RESET);
        }else{
            logger.fine("Player " + player.getPlayerNumber() + " build nothing yet");

        }
        logger.fine(" => " + ANSI.ANSI_RED + "Score:" + player.getScore()+ ANSI.ANSI_RESET + " | "  + ANSI.ANSI_YELLOW +  "Coins:" + player.getCoins()+ ANSI.ANSI_RESET +  " | Hand:"+ ANSI.ANSI_CYAN + player.getHand().toString() + ANSI.ANSI_RESET);
    }

    public static void printPlayerPlayDistrict(Player player, Districts districts){
        logger.fine("---> Player " + player.getPlayerNumber() + " play a " + ANSI.ANSI_BLUE + districts + ANSI.ANSI_RESET+" : "+ ANSI.ANSI_YELLOW +"coins -"+ districts.getCardCost() +  ANSI.ANSI_RESET +","+ ANSI.ANSI_RED +" score +"+ districts.getCardPoint() + ANSI.ANSI_RESET);
    }

    public static void printCoinsAddedToPlayer(int coinsAdded, int playerNumber){
        logger.fine("---> Player " + playerNumber + " added " + ANSI.ANSI_YELLOW +  coinsAdded + " coins " + ANSI.ANSI_RESET);
    }

    public static void printBonusCoinsAddedToPlayer(int amount, Player player, String bonus){
        logger.fine("---> Player "+ player.getPlayerNumber() +" "+ ANSI.ANSI_YELLOW +amount+" coins " + ANSI.ANSI_RESET + "bonus for  : " + bonus);
    }

    public static void printPlayerPickACard(Player player){
        logger.fine("---> Player " + player.getPlayerNumber() + " pick a card");
    }

    public static void print(String string){
        logger.fine(string);
    }

    public static void printInfo(String string){ //print with a level of INFO
        logger.info(string);
    }

    public static void printScoreBonus(int playerNumber, int score, String bonus){
        logger.fine("---> player " + playerNumber + " Get " + ANSI.ANSI_RED + "+" + score + ANSI.ANSI_RESET + " score bonus for " + bonus);
    }

    public static void printExchangeCardWithDeck(int playerNumber, Districts card, Districts card2){
        logger.fine("---> player " + playerNumber + " exchange a " +ANSI.ANSI_CYAN+ card +ANSI.ANSI_RESET+ " for a " +ANSI.ANSI_CYAN+ card2 +ANSI.ANSI_RESET);
    }

    public static void printCharacter(int playerNumber, Character character){
        logger.fine("---> Player " + playerNumber + " is " + ANSI.ANSI_PURPLE + character + ANSI.ANSI_RESET);
    }

    public static void printCoinsTransfered(Player player1, Player player2, int amount) {
        logger.fine("---> Transfert of "+ ANSI.ANSI_YELLOW +  amount + " coins " + ANSI.ANSI_RESET +" from player "+player1.getPlayerNumber()+" to player "+player2.getPlayerNumber());
    }


    public static void printPlayersBoardScore(List<Player> players) {
        logger.fine(ANSI.ANSI_BRIGHTYELLOW+"\n+++++++++++++ Player " + players.get(0).getPlayerNumber() + " wins with " + players.get(0).getScore() +" +++++++++++++"+ANSI.ANSI_RESET);
        for(int i = 1 ; i < players.size() ; i++)
        logger.fine("---> "+(i+1)+"rd "+ANSI.ANSI_WHITE+" player " + players.get(i).getPlayerNumber() + ANSI.ANSI_RESET + " : "+ ANSI.ANSI_RED + players.get(i).getScore() + ANSI.ANSI_RESET);

    }


    public static void printCharacterKilled(Character characterKilled) {
        logger.fine("---> The character "+ ANSI.ANSI_PURPLE + characterKilled + ANSI.ANSI_RESET +" is killed");
    }

    public static void printCharacterThiefed(Character characterThiefed) {
        logger.fine("---> The character "+ ANSI.ANSI_PURPLE + characterThiefed + ANSI.ANSI_RESET +" is thiefed");
    }

    public static void printCrown(GameMaster gameMaster) {
        if ((gameMaster.getPlayerKing() != null)) {
            logger.fine("--> Player " + gameMaster.getPlayerKing().getPlayerNumber() + " has crown");
        } else {
            logger.fine("--> There is no crown");
        }
    }

    public static void printTurnCharacter(int turn) {
        logger.fine("\n********** tour"+turn+" - players pick a character **********\n");
    }

    public static void printDestruction(Player player, Districts district, Player player1) {
        logger.fine("---> player "+player.getPlayerNumber()+" destroy the "+district+" of the player "+player1.getPlayerNumber());
    }

    public static void printBuyCard(Player player) {
        logger.fine("---> player "+player.getPlayerNumber()+" buy the card for 1 coin");
    }
}
