package fr.unice.polytech.startingpoint.cards;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class contain all the different District cards
 */
public enum Districts {
    NULL( 0, 0,"null", 3),

    TEMPLE( 1, 1,"Blue", 3),
    CHURCH(2, 2,"Blue", 4),
    MONASTERY(3, 3,"Blue", 3),
    CATHEDRAL(5, 5,"Blue", 2),

    MANOR(3, 3,"Yellow", 5),
    CASTLE(4, 4,"Yellow", 4),
    PALACE(5, 5,"Yellow", 2),

    TAVERN(1, 1,"Green", 5),
    STALL(2, 2,"Green", 3),
    MARKET(2, 2,"Green", 4),
    COUNTER(3, 3,"Green", 3),
    PORT(4, 4,"Green", 3),
    CITYHOTEL(5, 5,"Green", 2),

    WATCHOWER(1, 1,"Red", 3),
    JAIL(2, 2,"Red", 3),
    CASERNE(3, 3,"Red", 3),
    FORTRES(5, 5,"Red", 2),

    COURSEOFMIRACLES(2, 2,"Violet",1),
    DUNGEON(3, 3, "Violet", 2),
    LABORATORY(5, 5, "Violet", 1),
    MANUFACTURE(5, 5, "Violet", 1),
    OBSERVATORY(5, 5, "Violet", 1),
    CEMETERY(5, 5,"Violet", 1),
    LIBRARY(6, 6, "Violet", 1),
    MAGICSCHOOL(6, 6, "Violet", 1),
    UNIVERSITY(6, 8, "Violet", 1),
    DRACOPORT(6, 8, "Violet", 1);



    private final int cardCost;
    private final int numberOfCards;
    private final String color;
    private final int cardPoint;

    public static final List<Districts> VALUES = List.of(values());
    public static final Random RANDOM = new Random();

    private static final  HashMap<Districts, Integer> districtDeck = new HashMap<>();



    Districts(int cardCost, int cardPoint, String color, int numberOfCards) {
        this.cardCost = cardCost;
        this.cardPoint = cardPoint;
        this.color = color;
        this.numberOfCards = numberOfCards;
    }

    /**
     * This methode initialise the deck of district
     */
    public static void initDistrictDeck() {
        VALUES.forEach(d -> {
            if (!d.equals(Districts.NULL))
                districtDeck.put(d, d.getNumberOfCards());
        });
    }

    /**
     * This methode Remove a district from the district deck
     *
     * @param districts you want to remove
     */
    public static void removeCard(Districts districts) {
        districtDeck.replace(districts, districtDeck.get(districts)-1);
    }

    /**
     * This methode Add a district to the district deck
     * @param districts
     */
    public static void addCard(Districts districts){
        districtDeck.replace(districts,districtDeck.get(districts)+1);
    }

    /**
     * This methode return a random district from the district deck
     *
     * @return a random District from the deck
     */
    public static Districts randomDistrict(){
        Districts districts;
        List<Districts> keys = Districts.VALUES;
        while (true){
            districts = keys.get( RANDOM.nextInt(keys.size()) );
            if (!districts.equals(Districts.NULL) && districtDeck.get(districts)>0) break;
        }
        return districts;
    }

    /**
     * This methode return the district deck
     *
     * @return the district deck
     */
    public static HashMap<Districts, Integer> getDistrictDeck() {
        return districtDeck;
    }

    /**
     * This methode return the district with the
     * highest card point between two district
     *
     * @param districts1 the first district
     * @param districts2 the second district
     * @return the max district
     */
    public static Districts maxDistrict(Districts districts1, Districts districts2){
        if (districts1.cardCost > districts2.getCardCost()) return districts1;
        else return districts2;
    }

    /**
     * This methode return the district with the
     * lowest card point between two district
     *
     * @param districts1 the first district
     * @param districts2 the second district
     * @return the min district
     */
    public static Districts minDistrict(Districts districts1, Districts districts2) {
        if(districts1.getCardCost() < districts2.getCardCost()) return districts1;
        else return districts2;
    }

    public int getCardCost() {
        return cardCost;
    }

    public int getCardPoint() {
        return cardPoint;
    }

    public String getDistrictColor(){
        return color;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    @Override
    public String toString() {
        return this.name() + "(" + cardCost + ","+getDistrictColor().charAt(0)+")" ;
    }


}
