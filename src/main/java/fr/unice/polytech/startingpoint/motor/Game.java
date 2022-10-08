package fr.unice.polytech.startingpoint.motor;
import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.cards.character.King;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;

import java.util.*;

public class Game {

    private final Bank bank = new Bank();
    private final BoardPlayer boardPlayer = new BoardPlayer();
    private final List<Character> listOfCharacters = new ArrayList<>(Character.getCharactersDeck());
    private final GameMaster gameMaster = new GameMaster(boardPlayer, bank);

    public final Score score = new Score(gameMaster);

    /**
     * This methode initialise the game
     * create 4 players
     * give 2 coins
     * add 2 card to there hands
     */
    public void initGame(String ... players){
        Districts.initDistrictDeck();
        for (int i = 1 ; i <= players.length ; i++){
            switch(players[i-1]){
                case "BotColorFast" ->boardPlayer.add(new BotColorFast(i, new Hand(), new City(), gameMaster));
                case "BotColorBasic" ->boardPlayer.add(new BotColorBasic(i, new Hand(), new City(), gameMaster));
                case "BotFast" ->boardPlayer.add(new BotFast(i, new Hand(), new City(), gameMaster));
                case "BotRandom" ->boardPlayer.add(new BotRandom(i, new Hand(), new City(), gameMaster));
                case "BotSuper" ->boardPlayer.add(new BotSuper(i, new Hand(), new City(), gameMaster));
                default ->boardPlayer.add(new BotBasic(i, new Hand(), new City(), gameMaster));
            }
        }

        for (Player player : boardPlayer) {
            bank.addCoinsToPlayer(2, player);
            for(int i = 0 ; i < 4 ; i++)
                gameMaster.addDistrictToPlayerHand(gameMaster.getARandomDistrict(), player, true);
        }
    }

    public void playGame(){

        boolean gameOver=false ;
        GameMaster.turn =0 ;
        while(!gameOver) {
            int subTurn = 1;
            GameMaster.turn++ ;
            List<Character> characterDeck = new ArrayList<>(listOfCharacters); // la pioche des personnage
            ConsoleGuy.printTurnCharacter(GameMaster.turn);

            ConsoleGuy.printCrown(gameMaster);
            List<Character> visible = new ArrayList<>();

            initCharacters(characterDeck, visible);
            chooseCharacters(characterDeck, visible);

            for(Character character : listOfCharacters){
                for(Player player : boardPlayer){
                    if (gameMaster.checkIfPlayerIsCharacter(player, character)) {
                        ConsoleGuy.printTurn(GameMaster.turn, subTurn++);
                        ConsoleGuy.printCharacter(player.getPlayerNumber(), player.getCharacter());
                        if (!gameMaster.checkCharacterKilled(character)){
                            gameMaster.checkCharacterThiefed(character,player);
                            player.play();
                            ConsoleGuy.printGameState(gameMaster);
                        }
                        if (player.getCity().isBuild() && !gameOver) {
                            gameOver = true;
                            gameMaster.setPlayerFirst(player);
                        }
                    }}
            }
            gameMaster.resetTurnPower();
            gameMaster.verifKing();

        }
    }

    /**
     * Ended the game and announces the winner
     */
    public void endGame() {
        ConsoleGuy.print("\n\n\n+++++++++++++ End of the game +++++++++++++");
        ConsoleGuy.print("\n++++++++++++ Calculating score ++++++++++++");

        for (Player player : boardPlayer)
            gameMaster.addScoreToPlayer(score.calculBonus(player), player);
        boardPlayer.sort(Player.ComparatorScore);
        ConsoleGuy.printPlayersBoardScore(boardPlayer);
    }


    void  chooseCharacters(List<Character> characterInGame, List<Character> visible) {
        for (Player player : boardPlayer) {

            ArrayList<Character> possibleBefore = new ArrayList<>(listOfCharacters);
            possibleBefore.removeAll(visible);
            possibleBefore.removeAll(characterInGame);
            player.pickCharacter(characterInGame);
            ArrayList<Character> possibleAfter = new ArrayList<>(characterInGame);

            player.setPossibleCharacterAfter(possibleAfter);
            player.setPossibleCharacterBefore(possibleBefore);

        }
    }

    void initCharacters(List<Character> characterInGame, List<Character> visible) {
        Random random = new Random();

        int j = 0 ;
        do {
                Character character;
                do {
                    character = characterInGame.get(random.nextInt(characterInGame.size()));
                } while (character instanceof King);

                ConsoleGuy.print("The " + character + " is set aside");
                if (j < 6-boardPlayer.size()) visible.add(character);
                characterInGame.remove(character);
            j++ ;
        }while(j <= 6- boardPlayer.size() ) ;
        ConsoleGuy.print("!!! Last character set aside is invisible for players\n");

    }


    public BoardPlayer getBoardPlayer() {
        return boardPlayer;
    }

    public List<Character> getListOfCharacters() {
        return listOfCharacters;
    }
}
