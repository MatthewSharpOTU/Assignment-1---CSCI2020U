package sample;

import java.io.*;
import java.util.*;

// WordCounter Class will count all words in a file - based off of lecture code
public class WordCounter{

    private Map<String, Integer> wordCountsHam; // Stores frequency of a specific word in Ham file
    private Map<String, Integer> wordCountsSpam; // Stores frequency of a specific word in Spam file
    private Map<String, Boolean> wordsAppeared; // Stores words already appeared in a file
    private Map<Integer, String> allWords; // Used to store all words
    public int fileCounts;
    public int hamCount;
    public int spamCount;
    public int count;

    // Creates variable to count word frequency
    public WordCounter() {
        wordCountsHam = new TreeMap<>();
        wordsAppeared = new TreeMap<>();
        allWords = new TreeMap<>();
        wordCountsSpam = new TreeMap<>();
        fileCounts = 0; // Used to store the count of all files
        hamCount = 0; // Used to store the count of ham files
        spamCount = 0; // used to store the count of spam files
        count = 0;
    }

    public int getFileCounts() { return fileCounts; }
    public int getHamCount() { return hamCount; }
    public int getSpamCount() { return spamCount; }
    public Map<String, Integer> getWordCountsHam() { return wordCountsHam; }
    public Map<String, Integer> getWordCountsSpam() { return wordCountsSpam; }
    public Map<String, Boolean> getWordsAppeared() { return wordsAppeared; }
    public Map<Integer, String> getAllWords() { return allWords; }

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
            if (file.getParentFile().getName().equals("spam")){
                System.out.println(file.getName());
                if (file.getName().equals("cmds")){
                }
                else {
                    spamCount++;
                    while (scanner.hasNext()) {
                        String token = scanner.next();
                        if (isValidWord(token)) {
                            countWordHam(token);
                        }
                    }
                }
            }
            else if (file.getParentFile().getName().equals("ham") || file.getParentFile().getName().equals("ham2")){
                System.out.println(file.getName());
                if (file.getName().equals("cmds")){
                }
                else {
                    hamCount++;
                    while (scanner.hasNext()) {
                        String token = scanner.next();
                        if (isValidWord(token)) {
                            countWordSpam(token);
                        }
                    }
                }
            }
            if (file.getName().equals("cmds")){
            }
            else{
                fileCounts++;
            }
            wordsAppeared = new TreeMap<>();
        }
    }

    // Boolean function to determine if the word is valid
    private boolean isValidWord(String word){
        if (wordsAppeared.containsKey(word)) {
            return false;
        }
        wordsAppeared.put(word, true);
        String allLetters = "^[a-zA-Z]+$";
        // Returns true if the word is composed by only letters otherwise returns false
        return word.matches(allLetters);
    }

    // Counts the frequency of the word, either adding to the current amount or creating a new entry
    private void countWordHam(String word){
        if (wordCountsHam.containsKey(word)){
            int previous = wordCountsHam.get(word);
            wordCountsHam.put(word, previous+1);
        } else {
            wordCountsHam.put(word,1);
            allWords.put(count, word);
            count++;
        }
    }

    private void countWordSpam(String word){
        if (wordCountsSpam.containsKey(word)){
            int previous = wordCountsSpam.get(word);
            wordCountsSpam.put(word, previous+1);
        } else {
            wordCountsSpam.put(word,1);
            allWords.put(count, word);
            count++;
        }
    }

    // Function which outputs all the wordCounts entries into a file
    public void outputWordCount(int minCount, File output) throws IOException {
        System.out.println("Saving word counts to file:" + output.getAbsolutePath());
        System.out.println("Total Words: " + wordCountsHam.keySet().size());
        if (!output.exists()) {
            output.createNewFile();
            if (output.canWrite()) {
                PrintWriter fileOutput = new PrintWriter(output);

                Set<String> keys = wordCountsHam.keySet();
                Iterator<String> keyIterator = keys.iterator();

                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    int count = wordCountsHam.get(key);
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