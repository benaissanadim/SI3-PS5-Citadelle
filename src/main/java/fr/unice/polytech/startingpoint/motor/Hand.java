package fr.unice.polytech.startingpoint.motor;

import fr.unice.polytech.startingpoint.cards.Districts;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contain a list of all the card of a player
 */
public class Hand extends ArrayList<Districts>{


    public Hand() {}

    public Hand(List<Districts> liste){
        super(liste);
    }

    /**
     * This methode return the highest district you
     * can build with a certain amount of coins
     *
     * @param coins the max cost of the district
     * @return the highest district you can build
     */
    public Districts highestDistrict(int coins){
        Districts highest = Districts.NULL ;
        for(Districts d : this) {
            if(highest.getCardPoint() < d.getCardPoint() && d.getCardCost() <= coins )
                highest = d ;
        }
        return highest ;
    }
}
