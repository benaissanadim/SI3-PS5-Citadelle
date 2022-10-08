package fr.unice.polytech.startingpoint.app;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.startingpoint.guy.ConsoleGuy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenCSV {

    String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
    String statisticsResultFile = "save/results.csv";

    final int precisionRound = 2;
    final String[] endParagraph = {"^"}; // pour detecter la fin d'un paragraphe lors de la lecture

    private void writeCSV(StatParagraph message, boolean putEndParagraph) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(resourcesPath+statisticsResultFile),',', CSVWriter.NO_QUOTE_CHARACTER);

        writer.writeAll(message);

        if(putEndParagraph)writer.writeNext(endParagraph);
        writer.close();
    }

    private void writeAllCSV(List<StatParagraph> statParagraphList) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(resourcesPath+statisticsResultFile),',', CSVWriter.NO_QUOTE_CHARACTER);

        for (StatParagraph paragraph : statParagraphList){
            writer.writeAll(paragraph);
            writer.writeNext(endParagraph);
        }
        writer.close();
    }


    void readAndUpdateCSV(StatParagraph stats) throws IOException { //il manque un appel à une fonction de vérification, si le fichier existe mais el format n'est pas valide on réécrit par dessus
        try {
            CSVReader reader = new CSVReader(new FileReader(resourcesPath+statisticsResultFile)); // si le fichier existe deja on update
            updateCSV(stats,reader);
            return;
        }catch (FileNotFoundException e){
            ConsoleGuy.printInfo("Creating CSV file:");
        }
        writeCSV(stats,true); // si le fichier n'éxistait pas on le crée et on écrit les stats actuelle
        ConsoleGuy.printInfo("done");
    }

    private void updateCSV(StatParagraph newStatsParagraph, CSVReader reader) throws IOException {
        int paragraphCount = 0;
        boolean update = false;


        List<StatParagraph> finalStatsParagraphList = new ArrayList<>();
        StatParagraph statsParagraph ;

        while (true) {
            try {
                statsParagraph = readParagraph(reader);
                paragraphCount++;
            } catch (Exception e) {
                if (paragraphCount == 0) {
                    writeCSV(newStatsParagraph, true);
                    return;
                }
                else if(!update){
                    finalStatsParagraphList.add(newStatsParagraph);

                }
                writeAllCSV(finalStatsParagraphList);
                return;
            }


            if (checkSameParagraph(statsParagraph, newStatsParagraph)) {

                statsParagraph = statsParagraph.update(newStatsParagraph);
                finalStatsParagraphList.add(statsParagraph);
                update = true;

            }else {
                finalStatsParagraphList.add(statsParagraph);
            }

        }
    }



    private boolean checkSameParagraph(StatParagraph paragraph , StatParagraph paragraphToAdd){
        if(paragraph.size() == paragraphToAdd.size()){
            for (int i = 0; i < paragraph.size();i++){
                if(!paragraph.get(i)[0].equals(paragraphToAdd.get(i)[0])) return false;
            }
        }else{
            return false;
        }
        return  true;
    }

    public StatParagraph readParagraph(CSVReader reader) throws IOException {  // renvoie le prochain paragraphe du fichier
        StatParagraph paragraph = new StatParagraph();

        String [] r;
        while(!(r = reader.readNext())[0].equals(endParagraph[0])){
            paragraph.add(r);
        }
        return paragraph;
    }




}

