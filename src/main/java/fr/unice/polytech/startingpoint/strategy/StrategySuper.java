package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.character.*;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.cards.character.Soldier;
import fr.unice.polytech.startingpoint.cards.character.Thief;
import fr.unice.polytech.startingpoint.motor.*;

import java.util.List;
import java.util.Optional;


public class StrategySuper extends StrategyColor{

    Character charcterToKillAtThisTurn = null;
    Player playerToWizard = null ;
    Player playerToSoldier = null;
    Character playerToThief = null;

    public StrategySuper(Player player, GameMaster gameMaster) {
        super(player, gameMaster);
    }

    @Override
    public Player playerToWizard(){
        if(playerToWizard != null){
            Player tmp = playerToWizard ;
            playerToWizard = null ;
            return tmp ;
        }
        return super.playerToWizard();
    }

    @Override
    public Player playerToSoldier(){
        if(playerToSoldier != null){
            Player tmp = playerToSoldier ;
            playerToSoldier = null ;
            return tmp ;
        }
        return super.playerToSoldier();
    }

    @Override
    public Character characterToKill(){
        BoardPlayer board = gameMaster.getBoardPlayer();


        // si il avait choisie de tuer quelqu'un il le tue
        if (charcterToKillAtThisTurn != null){
            Character tmp = charcterToKillAtThisTurn;
            charcterToKillAtThisTurn = null;
            return tmp;
        }

            //tuer le voleur
        if(possibleCharacterForPlayer(board.getPlayer6Districts()) instanceof Thief // voleur pris par un joueur en passe de gagner
                || board.hasPlayerWithLotCoins(player))  //éviter qu’un joueur s’enrichisse honteusement
            return new Thief(gameMaster);

            //tuer le condotiere
        else if(player.equals(board.getPlayerWithHighestScore()) || // je suis en tete
                possibleCharacterForPlayer(board.getPlayer6Districts()) instanceof Soldier
                || board.isOnlyHasCards1PO(player))
            return new Soldier(gameMaster) ;


            //le cas du roi
        else return super.characterToKill();
    }

    public Character choiceArchitectOrAssassin(List<Character> characterDeck){
        Character c = choosebetween(characterDeck , "Architect" , "Assassin") ;
        if(c instanceof Assassin) charcterToKillAtThisTurn = new Architect(gameMaster);
        return c;
    }

    public Character choiceNearToWin(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        Character c = choosebetween(characterDeck , "King" , "Assassin", "Soldier", "Bishop", "Wizard" , "Thief") ;
        if(player.equals(board.getPlayer6Districts())) {
            if (c instanceof Assassin) charcterToKillAtThisTurn = new Soldier(gameMaster);
        }
        else {
            if (c instanceof Assassin) charcterToKillAtThisTurn = new King(gameMaster);
            else if (c instanceof Soldier) playerToSoldier = board.getPlayer6Districts();
            else if (c instanceof Thief) playerToThief = new King(gameMaster);
        }
        return c ;
    }

    public Character choiceFinalTour(List<Character> characterDeck){

        Character c = choosebetween(characterDeck , "Assassin" , "Soldier", "Bishop", "Wizard") ;
        if(c instanceof Assassin && characterDeck.contains(new Soldier(gameMaster))) charcterToKillAtThisTurn = new Soldier(gameMaster);
        else charcterToKillAtThisTurn = new Bishop(gameMaster);
        return c ;
    }

    @Override
    public Character bestCharacterToChoose(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        if(player.getIndex() == 1 && board.hasPlayerWhoWantsArchitect()) return choiceArchitectOrAssassin(characterDeck);
        if(board.getPlayer6Districts() != null) return choiceNearToWin(characterDeck);
        if(board.getPlayer7Districts() != null) return  choiceFinalTour(characterDeck);

        return choiceCharacter(characterDeck);
    }


    public Character choiceCharacter(List<Character> characterDeck){
        if(choiceThief(characterDeck).isPresent()) return choiceThief(characterDeck).get();
        if(choiceWizard(characterDeck).isPresent()) return choiceWizard(characterDeck).get();
        if(choiceMarchant(characterDeck).isPresent()) return choiceMarchant(characterDeck).get();
        if(choiceArchitect(characterDeck).isPresent()) return choiceArchitect(characterDeck).get();
        if(choiceSoldier(characterDeck).isPresent()) return choiceSoldier(characterDeck).get();

        return super.bestCharacterToChoose(characterDeck);
    }

    public Optional<Character> choiceSoldier(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        //choix de prendre le voleur si on a un joueur a presque gagne
        if(board.getPlayer6Districts() != null)
            return getCharacterByName(characterDeck, "Soldier");
        return Optional.empty();
    }

    public Optional<Character> choiceWizard(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        //choix du magicien si le joueur a x fois plus de carte en plus que la moyenne
        if((double)player.getNumberOfCards() > (board.averageCardPerPlayerHand()*1.2) && player.getNumberOfCards() >1) // si le joueur a x fois plus de carte en plus que le moyenne et qu'il a minimum 2 cartes
            return  getCharacterByName(characterDeck, "Wizard");
        return Optional.empty();
    }

    public Optional<Character> choiceMarchant(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        if( GameMaster.turn <= 3 && (double)player.getCoins() < board.averageGoldPerPlayer())
            return getCharacterByName(characterDeck, "Marchant");
        return Optional.empty() ;
    }

    public Optional<Character> choiceArchitect(List<Character> characterDeck){

        if( player.getCoins() >=4 && player.getNumberOfCards()>=1 && player.getCitySize() >=5 )
            return getCharacterByName(characterDeck, "Architect");
        return Optional.empty() ;
    }


    public Optional<Character> choiceThief(List<Character> characterDeck){
        BoardPlayer board = gameMaster.getBoardPlayer();

        //choix de prendre le voleur si on a x% de gold en plus que la moyenne des joueurs
        if(player.getCoins() > (board.averageGoldPerPlayer()*1.5) && player.getCoins() > 3)
            return getCharacterByName(characterDeck, "Thief");
        return Optional.empty();
    }



    Optional<Character> getCharacterByName(List<Character> characterDeck, String charactere){
        return  characterDeck.stream().filter(c-> c.toString().equals(charactere)).findAny();
    }


    public Character choosebetween(List<Character> characterDeck ,String... characters){

        for(String c : characters){
            Optional<Character> character = getCharacterByName(characterDeck, c);
            if(character.isPresent()) return character.get();
        }
        return super.bestCharacterToChoose(characterDeck);
    }



}