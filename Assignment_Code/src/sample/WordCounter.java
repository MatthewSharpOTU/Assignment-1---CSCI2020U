package sample;

import java.io.*;
import java.util.*;

// WordCounter Class will count all words in a file - based off of lecture code
public class WordCounter{

    private Map<String, Double> wordCountsHam; // Stores frequency of a specific word in Ham file
    private Map<String, Double> wordCountsSpam; // Stores frequency of a specific word in Spam file
    private Map<String, Boolean> wordsAppeared; // Stores words already appeared in a file
    private Map<Integer, String> allWords; // Used to store all words in the train file
    public ArrayList<TestFile> totalPercentages; // Used to store all the files and their percentages
    public double fileCounts; // Stores the count of all files in the test directory
    public double hamCount; // Stores the count of all ham files in the train directory
    public double spamCount; // Stores the count of all spam files in the train directory
    public int count; // Stores the count of all files in the train directory

    // Creates variable to count word frequency
    public WordCounter() {
        wordCountsHam = new TreeMap<>();
        wordsAppeared = new TreeMap<>();
        allWords = new TreeMap<>();
        wordCountsSpam = new TreeMap<>();
        fileCounts = 0.0; // Used to store the count of all files in the test directory
        hamCount = 0.0; // Used to store the count of ham files in the train directory
        spamCount = 0.0; // Used to store the count of spam files in the train directory
        count = 0; // Used to store the count of files in the train directory
        totalPercentages = new ArrayList<TestFile>(5000);
    }

    // Member Functions
    public double getFileCounts() { return fileCounts; }
    public double getHamCount() { return hamCount; }
    public double getSpamCount() { return spamCount; }
    public Map<String, Double> getWordCountsHam() { return wordCountsHam; }
    public Map<String, Double> getWordCountsSpam() { return wordCountsSpam; }
    public Map<String, Boolean> getWordsAppeared() { return wordsAppeared; }
    public Map<Integer, String> getAllWords() { return allWords; }
    public ArrayList<TestFile> getTotalPercentages() { return totalPercentages; }

    /**
     * Function parses through test directory and determines their spam probabilities
     *
     * @param test - gets the test directory
     * @throws FileNotFoundException - If the user inputted directory does not exist
     */
    public void parseTestFile(File test) throws FileNotFoundException {
        if(test.isDirectory()){ // Conditional if the test file variable is a directory
            //parse each file inside the directory
            File[] content = test.listFiles();
            for (File current: content){ // for loop that parses for every file in the directory
                parseTestFile(current);
            }
        }else{
            Scanner scanner = new Scanner(test); // Sets scanner for the file
            //Scans token by token
            double total = 0.0; // Used to the summation in the spam calculation
            while (scanner.hasNext()) { // Iterates for every entity in the file
                String token = scanner.next();
                if (isValidWord(token)) { // Conditional to determine if the word is valid
                    total += calculatePercentage(token); // Calls the calculatePercentage function to find the percent
                }
                wordsAppeared = new TreeMap<>(); // Used to reset the wordsAppeared variable (primary use is in the parseFile function)
            }
            double spamChance = 1/(1+Math.pow(Math.E, total)); // Calculates for the spam percentage of a file
            totalPercentages.add(new TestFile(test.getName(), spamChance, test.getParentFile().getName())); // Stores the file name, spam probability and class as a TestFile object in the arrayList
            fileCounts++; // Increments test file count
        }
    }

    /**
     *
     * @param word - The valid word in the file
     * @return - double variable which represents the probability of a word being spam through another equation
     */
    public double calculatePercentage(String word){
        double hamPercent = 0.00000; // used to store the percent of a word in a ham file
        double spamPercent = 0.00000; // used to store the percent of a word in a spam file
        if (wordCountsHam.containsKey(word)){ // Conditional if a word is in the list of words found in the train-ham directory
            hamPercent = wordCountsHam.get(word)/hamCount; // Calculates the percent of a word in a ham file
        } else { // Conditional if the word is not present in the list of words found in the train-ham directory
            hamPercent = 1/hamCount;
        }
        if (wordCountsSpam.containsKey(word)){ // Conditional if a word is in the list of words found in the train-spam directory
            spamPercent = wordCountsSpam.get(word)/spamCount; // Calculates the percent of a word in a spam file
        }
        else { // Conditional if the word is not present in the list of words found in the train-spam directory
            spamPercent = 1/spamCount;
        }
        double spamEquation = spamPercent/(spamPercent + hamPercent); // used to calculate the chance of a word being spam
        return Math.log(1-spamEquation)-Math.log(spamEquation); // Returns the value required to calculate the chance of a file being spam
    }

    /**
     * This function parses through train directory
     *
     * @param file - the train directory
     * @throws FileNotFoundException - if the train directory path does not exist
     */
    public void parseFile(File file) throws FileNotFoundException{
        if(file.isDirectory()){ // Conditional if the test file variable is a directory
            //parse each file inside the directory
            File[] content = file.listFiles();
            for (File current: content){ // For loop for each file in the train directory
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            //Scans token by token
            if (file.getParentFile().getName().equals("spam")){ // Conditional if a file is in the spam directory
                spamCount++; // Increment spam file counter
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) { // conditional if a word is valid
                        countWordSpam(token); // Adds word to the spamWord map
                    }
                }
            }
            else if (file.getParentFile().getName().equals("ham") || file.getParentFile().getName().equals("ham2")){ // Conditional if a file is in the ham or ham2 directory
                hamCount++; // Increment ham file counter
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) { // Conditional if a word is valid
                        countWordHam(token); // Adds word to the hamWord map
                    }
                }
            }
            wordsAppeared = new TreeMap<>(); // resets the wordsAppeared map for each file
        }
    }

    /**
     *Boolean function to determine if the word is valid
     *
     * @param word -  The word to determine if valid
     * @return true - if the word matches the regex template
     */
    private boolean isValidWord(String word){
        if (wordsAppeared.containsKey(word)) { // Conditional to see if the word already appeared in the file
            return false;
        }
        wordsAppeared.put(word, true); // if word has not appeared in the file yet, add it to wordsAppeared map
        String allLetters = "^[a-zA-Z]+$";
        // Returns true if the word is composed by only letters otherwise returns false
        return word.matches(allLetters);
    }


    /**
     * Counts the frequency of the word (ham), either adding to the current amount or creating a new entry
     *
     * @param word - word to add to the the hamWords map
     */
    private void countWordHam(String word){
        if (wordCountsHam.containsKey(word)){ // If the word already exists - increment current count
            double previous = wordCountsHam.get(word);
            wordCountsHam.put(word, previous+1);
        } else { // If the word does not exist then create a new entry in the map
            wordCountsHam.put(word,1.0);
            allWords.put(count, word);
            count++; // Increment count of the words in train files
        }
    }

    /**
     *Counts the frequency of the word (spam), either adding to the current amount or creating a new entry
     *
     * @param word - word to add to the spamWords map
     */
    private void countWordSpam(String word){
        if (wordCountsSpam.containsKey(word)){ // Conditional if the word already exists - increment current count
            double previous = wordCountsSpam.get(word);
            wordCountsSpam.put(word, previous+1);
        } else { // If the word does not exists in the map, then create a new entry
            wordCountsSpam.put(word,1.0);
            allWords.put(count, word);
            count++; // Increment count of the words in train files
        }
    }
}