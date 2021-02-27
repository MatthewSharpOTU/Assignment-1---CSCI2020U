package sample;

import java.io.*;
import java.util.*;

// WordCounter Class will count all words in a file - based off of lecture code
public class WordCounter{

    private Map<String, Integer> wordCounts; // Stores frequency of a specific word

    // Creates variable to count word frequency
    public WordCounter() {
        wordCounts = new TreeMap<>();
    }

    public void parseFile(File file) throws IOException{
        System.out.println("Starting parsing of the file:" + file.getAbsolutePath());

        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for (File current: content){
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            //Scans token by token
            while (scanner.hasNext()) {
                String token = scanner.next();
                if (isValidWord(token)){
                    countWord(token);
                }
            }
        }
    }

    // Boolean function to determine if the word is valid
    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // Returns true if the word is composed by only letters otherwise returns false
        return word.matches(allLetters);
    }

    // Counts the frequency of the word, either adding to the current amount or creating a new entry
    private void countWord(String word){
        if (wordCounts.containsKey(word)){
            int previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        } else {
            wordCounts.put(word,1);
        }
    }

    // Function which outputs all the wordCounts entries into a file
    public void outputWordCount(int minCount, File output) throws IOException {
        System.out.println("Saving word counts to file:" + output.getAbsolutePath());
        System.out.println("Total Words: " + wordCounts.keySet().size());
        if (!output.exists()) {
            output.createNewFile();
            if (output.canWrite()) {
                PrintWriter fileOutput = new PrintWriter(output);

                Set<String> keys = wordCounts.keySet();
                Iterator<String> keyIterator = keys.iterator();

                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    int count = wordCounts.get(key);
                    if (count >= minCount) {
                        fileOutput.println(key + ": " + count);
                    }
                }

                fileOutput.close();
            }
        } else {
            System.out.println("Error: the output file found already exists: " + output.getAbsolutePath());

        }

    }
}