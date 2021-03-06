package sample;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

// WordCounter Class will count all words in a file - based off of lecture code
public class WordCounter{

    private Map<String, Double> wordCountsHam; // Stores frequency of a specific word in Ham file
    private Map<String, Double> wordCountsSpam; // Stores frequency of a specific word in Spam file
    private Map<String, Boolean> wordsAppeared; // Stores words already appeared in a file
    private Map<Integer, String> allWords; // Used to store all words
    public ArrayList<TestFile> totalPercentages;
    public ArrayList<Double> hamPercentages;
    public TestFile[] spamPercentages;
    public int fileCounts;
    public double hamCount;
    public double spamCount;
    public int count;

    // Creates variable to count word frequency
    public WordCounter() {
        wordCountsHam = new TreeMap<>();
        wordsAppeared = new TreeMap<>();
        allWords = new TreeMap<>();
        wordCountsSpam = new TreeMap<>();
        fileCounts = 0; // Used to store the count of all files
        hamCount = 0.0; // Used to store the count of ham files
        spamCount = 0.0; // used to store the count of spam files
        count = 0;
        totalPercentages = new ArrayList<TestFile>(5000);
        hamPercentages = new ArrayList<Double>(5000);
        spamPercentages = new TestFile[5000];
    }

    public int getFileCounts() { return fileCounts; }
    public double getHamCount() { return hamCount; }
    public double getSpamCount() { return spamCount; }
    public Map<String, Double> getWordCountsHam() { return wordCountsHam; }
    public Map<String, Double> getWordCountsSpam() { return wordCountsSpam; }
    public Map<String, Boolean> getWordsAppeared() { return wordsAppeared; }
    public Map<Integer, String> getAllWords() { return allWords; }

    public void parseTestFile(File test) throws FileNotFoundException {
        System.out.println("Starting parsing of the file:" + test.getAbsolutePath());

        if(test.isDirectory()){
            //parse each file inside the directory
            File[] content = test.listFiles();
            for (File current: content){
                parseTestFile(current);
            }
        }else{
            Scanner scanner = new Scanner(test);
            //Scans token by token
            if (test.getParentFile().getName().equals("spam")){
                double total = 0.0;
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {
                        total += calculatePercentage(token);
                    }
                    wordsAppeared = new TreeMap<>();
                }
                double spamChance = 1/(1+Math.pow(Math.E, total));
                System.out.println(spamChance);
                totalPercentages.add(new TestFile(test.getName(), spamChance, test.getParentFile().getName()));
            }
            else if (test.getParentFile().getName().equals("ham") || test.getParentFile().getName().equals("ham2")){
                double total = 0.0;
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {
                        total += calculatePercentage(token);
                    }
                    wordsAppeared = new TreeMap<>();
                }
                double spamChance = 1/(1+Math.pow(Math.E, total));
                System.out.println(spamChance);
                totalPercentages.add(new TestFile(test.getName(), spamChance, test.getParentFile().getName()));
            }
        }
    }

    public double calculatePercentage(String word){
        double hamPercent = 0.00000;
        double spamPercent = 0.00000;
        if (wordCountsHam.containsKey(word)){
            hamPercent = wordCountsHam.get(word)/hamCount;
        } else {
            hamPercent = 1/hamCount;
        }
        if (wordCountsSpam.containsKey(word)){
            spamPercent = wordCountsSpam.get(word)/spamCount;
        }
        else {
            spamPercent = 1/spamCount;
        }
        double spamEquation = spamPercent/(spamPercent + hamPercent);
        return Math.log(1-spamEquation)-Math.log(spamEquation);
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
            if (file.getParentFile().getName().equals("spam")){
                //System.out.println(file.getName());
                spamCount++;
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {
                        countWordSpam(token);
                    }
                }
            }
            else if (file.getParentFile().getName().equals("ham") || file.getParentFile().getName().equals("ham2")){
                //System.out.println(file.getName());

                hamCount++;
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {
                        countWordHam(token);
                    }
                }
            }
            fileCounts++;
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
            double previous = wordCountsHam.get(word);
            wordCountsHam.put(word, previous+1);
        } else {
            wordCountsHam.put(word,1.0);
            allWords.put(count, word);
            count++;
        }
    }

    private void countWordSpam(String word){
        if (wordCountsSpam.containsKey(word)){
            double previous = wordCountsSpam.get(word);
            wordCountsSpam.put(word, previous+1);
        } else {
            wordCountsSpam.put(word,1.0);
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
                    double count = wordCountsHam.get(key);
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