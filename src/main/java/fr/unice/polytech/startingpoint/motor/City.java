package fr.unice.polytech.startingpoint.motor;


import fr.unice.polytech.startingpoint.cards.Districts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contain a list of all the districts that player has built
 */
public class City extends ArrayList<Districts>{

    public City() {}

    public City(List<Districts> liste){
        super(liste);
    }

    /**
     * This methode check if your city is build
     * ie the size of your city is more than 8
     *
     * @return true if you city is build
     */
    public boolean isBuild(){
        return size() >= 8 ;
    }

    /**
     * This methode return a set of all the color in the city
     *
     * @return a set of different colors in city
     */
    public Set<String> setOfColors(){
        Set<String> colors = new HashSet<>() ;
        this.forEach(district -> colors.add(district.getDistrictColor()));
        return colors ;
    }

    /**
     * This methode check if you have all the color in it
     *
     * @return true if the city have all the color
     */
    public boolean checkFiveColor(){
        switch (setOfColors().size()){
            case 5: return true;
            case 4: if(this.contains(Districts.COURSEOFMIRACLES)
                    && nbColorDifferent(Districts.COURSEOFMIRACLES)>=2)
                return true;
            default: return false;
        }
    }

    public int nbColors(){
        return setOfColors().size();
    }

    /**
     * this methode check if your city already
     * have the color of a district in it
     *
     * @param districts you want to test
     * @return true if the city already have the color
     */
    public boolean containColor(Districts districts){
        return setOfColors().contains(districts.getDistrictColor());

    }

    /**
     * this methode return the District
     * with the lower cost in the city
     *
     * @return a District
     */
    public Districts minDistrict() {
        if(this.size() !=0){
            Districts min = get(0);
            for(Districts d : this){
                if(min.getCardCost() >=d.getCardCost() ) min = d ;
            }
            return min;
        }
        return null;
    }

    /**
     * this methode return the number of district
     * with the same color as the district in parameter
     *
     * @param districts you want to check the color
     * @return the occurrence of the color
     */
    public int nbColorDifferent(Districts districts){
        int nb = 0;
        for(Districts d : this){
            if(d.getDistrictColor().equals(districts.getDistrictColor())) nb ++;
        }
        return nb ;
    }


    /**
     * @return a district who have only one occurrence of his color in the city
     */
    public Districts districtUniqColor(){
        Districts districts = Districts.NULL ;
        for(Districts d : this){
            if(nbColorDifferent(d) == 1)  districts = d;
        }
        return districts;
    }

    public boolean hasCardWith1PO() {
       return stream().anyMatch(d -> d.getCardCost() ==1 ) ;
    }
}