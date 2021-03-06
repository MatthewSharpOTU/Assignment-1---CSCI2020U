package sample;

import java.io.File;
import java.util.*;
public class percentageCalculator {
    private Map<String, Map<String, Integer>> files;
    private Map<String, Double> percentages;

    public percentageCalculator() {
        files = new TreeMap<>();
        percentages = new TreeMap<>();
    }


    public void setPercentages(WordCounter trainCounter, WordCounter testCounter) {
        System.out.println("File Count " + trainCounter.getFileCounts());
        int count = 0;
        System.out.println(trainCounter.getWordCountsHam().size());
        System.out.println(trainCounter.getAllWords().size());
        System.out.println(trainCounter.getHamCount());
        System.out.println((trainCounter.getSpamCount()));
        /*
        for (int i=0; i<trainCounter.getWordCountsHam().size(); i++){
            String currentWord = trainCounter.getAllWords().get(i);
            percentages.put(currentWord, (double)trainCounter.getWordCountsHam().get(currentWord)/trainCounter.getFileCounts());
            //System.out.println(percentages.get(currentWord));

            if (trainCounter.getWordsAppeared().get(currentWord)){
                percentages.put(currentWord, (double)trainCounter.getWordCounts().get(currentWord)/trainCounter.getFileCounts());
            }
            else {
                percentages.put(currentWord, 1.0 / trainCounter.getFileCounts());
            }
        }
         */
    }
}
