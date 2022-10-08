package fr.unice.polytech.startingpoint.app;


import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;

public class StatParagraph extends ArrayList<String[]> {



    StatParagraph(){
    }



    public StatParagraph update(StatParagraph newStat){
        for(int i = 1; i < this.size();i++){
            Double nbGame = stringToDouble(this.get(i)[1]);
            Double newNbGame = stringToDouble(newStat.get(i)[1]);
            for(int j = 4; j <=6; j++)
                this.get(i)[j] = Double.toString(Precision.round(    ((stringToDouble(this.get(i)[j]) * nbGame)   +  (stringToDouble(newStat.get(i)[j]) * newNbGame) )  /  (nbGame+newNbGame), 2));
            }

        for(int i = 0; i < this.size();i++){
            for(int j = 1; j <=3; j++)
            this.get(i)[j] = Integer.toString(  stringToInt(this.get(i)[j]) + stringToInt(newStat.get(i)[j])   ) ;
        }

        return this;
    }


    private Double stringToDouble(String i){
        return Double.parseDouble(i);
    }
    private int stringToInt(String i){
        return Integer.parseInt(i);
    }



}
