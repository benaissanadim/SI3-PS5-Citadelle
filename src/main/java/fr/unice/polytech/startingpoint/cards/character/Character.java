package fr.unice.polytech.startingpoint.cards.character;

import fr.unice.polytech.startingpoint.bot.Player;

import fr.unice.polytech.startingpoint.motor.GameMaster ;

import java.util.List;


public class Character {


    protected int id ;
    protected String color ;
    protected String name;
    public static GameMaster gameMaster ;

    public Character(GameMaster game, String name ,int id){
        this.gameMaster = game ;
        this.color = "none" ;
        this.name = name ;
        this.id = id ;
    }

    public static List<Character> characterDeck = List.of(
            new Assassin(gameMaster),
            new Thief(gameMaster),
            new Wizard(gameMaster),
            new King(gameMaster),
            new Bishop(gameMaster),
            new Marchant(gameMaster),
            new Architect(gameMaster),
            new Soldier(gameMaster)
    );


    public String getCharacterColor(){
        return color;
    }

    public static List<Character> getCharactersDeck(){
        return characterDeck;
    }


    /**
     * @return the player who have  the character who call the methode
     */
    public Player getPlayer(){
        for(Player p : gameMaster.getBoardPlayer()){
            if(p.getCharacter().equals(this))
                return p ;
        }
        return null ;
    }

    public String toString(){
        return this.name;
    }

    /**
     * use the special power of the character
     */
    public void usePower(){}

    public static void setGameMaster(GameMaster gameMaster) {
        Character.gameMaster = gameMaster;
    }

    public int getId() {
        return id;
    }

}