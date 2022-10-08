package fr.unice.polytech.startingpoint.strategy;

import fr.unice.polytech.startingpoint.bot.Player;
import fr.unice.polytech.startingpoint.cards.Districts;
import fr.unice.polytech.startingpoint.cards.character.Character;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;
import fr.unice.polytech.startingpoint.motor.GameMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class Strategy {

    protected Player player;
    protected GameMaster gameMaster;
    private int nbToBuild =1;
    protected ArrayList<Character> possibleCharactersBefore;
    protected ArrayList<Character> possibleCharactersAfter;



    public Strategy(Player player, GameMaster gameMaster){
        this.player = player;
        this.gameMaster = gameMaster;
    }


    /**
     * play a player turn
     *
     *  the player play his turn
     */
    public void play(){
        if(hasLaboratory())useLaboratory(giveBadDistrictForPower());
        if(gameMaster.getTotalCards() >= 3 && hasManufactory()) useManufactry() ;
        gameMaster.checkCoinColorBonus(player, player.getCharacter());
        player.getCharacter().usePower();
        playCoinsOrDistrict();
        for(int i=0 ; i< nbToBuild ; i++){
            Districts districts = districtToBuild();
            placeACard(districts, player, gameMaster);
        }
        nbToBuild = 1 ;
    }

    /**
     * set possible character for other players
     */
    private void setPossibleCharacter(){
        possibleCharactersAfter = player.getPossibleCharactersAfter();
        possibleCharactersBefore = player.getPossibleCharactersBefore();
    }

    /**
     *place a card in his city when he has enough money
     */
    public void placeACard(Districts districts, Player player, GameMaster gameMaster) {
        if (gameMaster.checkIfCardCanBePlaced(districts, player))
            gameMaster.playerPlaceACard(player, districts);
    }

    /**
     * use the labo power
     * @param districts district card
     */
    public void useLaboratory(Districts districts){
        if(districts != null){
            ConsoleGuy.print("player: " + player.getPlayerNumber() + " use the laboratory to exchange a " + districts + " for 1 gold");
            gameMaster.changeOneCardWithDeck(player,districts);
            gameMaster.getBank().addCoinsToPlayer(1,player);
        }
    }

    /**
     * use the manufactory power
     */
    private void useManufactry(){
        if(player.getCoins() >=3 && (player.getNumberOfCards() ==0 || nbBadDistrict() == player.getNumberOfCards() )) {
            ConsoleGuy.print("player " + player.getPlayerNumber() + " use Manufactore Power");
            for (int i = 0; i < 2; i++)
                pickACard(gameMaster);
            gameMaster.getBank().removeCoinsToPlayer(3,player);
        }

    }


    public abstract Districts giveBadDistrictForPower(); // give the first district considered bad according to the strategy

    /**
     * @param characterDeck the list of the character
     */
    public void pickCharacter(List<Character> characterDeck){ // give the player a random character
        Character character = bestCharacterToChoose(characterDeck);
        player.setCharacter(character);
        Character.setGameMaster(gameMaster);
        characterDeck.remove(character);
        gameMaster.setCharacterToPlayer(character, player, characterDeck);
    }

    public Character bestCharacterToChoose(List<Character> characterDeck){

        Optional<Character> character = getCharacterByName(characterDeck, "Architect");
        if(character.isPresent() && nbGoodDisrict() >1 && player.getCoins() > 4) return character.get() ;

        character = getCharacterByName(characterDeck, "Wizard");
        if(character.isPresent() && nbBadDistrict() > 0 ) return character.get() ;

        character = getCharacterByName(characterDeck, "Thief");
        if(character.isPresent() && player.getCoins() < 2 ) return character.get() ;

        character = getCharacterByName(characterDeck, "Assassin");
        if(character.isPresent() && gameMaster.getBoardPlayer().getPlayer6Districts() != null ) return character.get() ;

        return maxBonusFromCharacterColor(characterDeck);

    }


    /**
     * @param characterDeck a list of charactere
     * @return the character who give the most bonus gold
     */
    public Character maxBonusFromCharacterColor(List<Character> characterDeck){
        Character best = characterDeck.get(0);
        for (Character character : characterDeck) {
            if (gameMaster.coinColorBonus(player, best) < gameMaster.coinColorBonus(player, character)) best = character;
        }
        return best;
    }

    /**
     * Chose between earn coins or pick a card
     */
    public void playCoinsOrDistrict(){
        if( chooseCoinsOrDistrict() ) {
            gameMaster.getBank().addCoinsToPlayer(2, player);
        }
        else{
            if (hasLibrary() && gameMaster.getTotalCards() >= 2) {
                pickTwoCards(gameMaster);
            }
            else pickACard(gameMaster);
        }
    }


    /**
     * Add a random district to the player hand
     */
    public void pickACard(GameMaster gameMaster) {
        List<Districts> d = new ArrayList<>() ;
        d.add(gameMaster.getARandomDistrict());
        d.add(gameMaster.getARandomDistrict());
        if(hasObservatory()) d.add(gameMaster.getARandomDistrict());
        Districts districts = chooseADistrict(d);
        if (districts == Districts.CEMETERY) gameMaster.setPlayerWhoGetCemetery(player);
        gameMaster.addDistrictToPlayerHand(districts, player, true);
    }


    /**
     * Add two random district to the player hand
     */
    public void pickTwoCards(GameMaster gameMaster) {
        Districts districts;
        for (int i = 0; i < 2; i++) {
            districts = gameMaster.getARandomDistrict();
            gameMaster.addDistrictToPlayerHand(districts, player, true);
        }
    }

    /**
     * try to find the possible character for a player
     * @param p player
     * @return Charcter
     */
    public Character possibleCharacterForPlayer(Player p){
        setPossibleCharacter();
        Random rand = new Random();
        if(p == null) return null ;
        if(p.getIndex() > player.getIndex()  )
            return possibleCharactersAfter.get(rand.nextInt(possibleCharactersAfter.size())) ;
        return possibleCharactersBefore.get(rand.nextInt(possibleCharactersBefore.size())) ;
    }

    /**
     * @return int number of bad district in hand
     */
    public int nbBadDistrict(){
        int nb = 0 ;
        for(Districts d : player.getHand()) {
            if(isBadDistrict(d) ) nb ++;
        }
        return nb;
    }

    public int nbGoodDisrict(){
        return player.getHand().size() - nbBadDistrict() ;
    }

    public void setNbToBuild(int x){
        nbToBuild = x ;
    }

    public int getNbToBuild(){
        return nbToBuild ;
    }


    /**
     * @param districts list of district
     * @return the district who will be added to the hand
     */
    public abstract Districts chooseADistrict(List<Districts> districts);

    /**
     * @return the max district that he can build
     */
    public abstract Districts districtToBuild();

    /**
     * @return true if choose add coins and false for pick a card
     */
    public abstract boolean chooseCoinsOrDistrict();

    public abstract boolean isBadDistrict(Districts district);

    public abstract Player bestPlayer(Player player);

    public Character characterToKill(){
        return possibleCharacterForPlayer(bestPlayer(player));
    }

    public Character characterToThief(){
        return possibleCharacterForPlayer(gameMaster.getBoardPlayer().getRichestPlayer(player));
    }

    public Player playerToSoldier(){
        return bestPlayer(player);
    }

    public Player playerToWizard(){
        return gameMaster.getBoardPlayer().getPlayerWithMostCards(player);
    }

    Optional<Character> getCharacterByName(List<Character> characterDeck, String charactere){
        return  characterDeck.stream().filter(c-> c.toString().equals(charactere)).findAny();
    }


    public abstract Districts bestCard(Player player);

    public boolean hasLibrary(){
        return player.getCity().contains(Districts.LIBRARY) ;
    }

    public  boolean hasObservatory(){
        return player.getCity().contains(Districts.OBSERVATORY) ;
    }

    public  boolean hasManufactory(){
        return player.getCity().contains(Districts.MANUFACTURE) ;
    }

    public boolean hasLaboratory(){
        return player.getCity().contains(Districts.LABORATORY);
    }

    public abstract boolean buyCardDestroy(Districts district);
}
