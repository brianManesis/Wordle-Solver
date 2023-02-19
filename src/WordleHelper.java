package src;

import java.util.ArrayList;

/**
 * This Class has two methods,
 * One to suggest a good guess for the wordle game,
 * and another to update the possible list of words
 * based on the last guess
 * 
 * @author Brian Manesis
 * @version 1.0
 * @since 18 Feb 2022
 */
public class WordleHelper {

    private ArrayList<String> possible;
    private ArrayList<Character> in;
    String[] possibleCharactersPerIndex;
    private Dictionary wordleWords;
    private String guess;
    private String suggestion;
    private char[] build;
    private char[] feedback;

    /**
     * This constructor takes in the file containing the possible wordle words
     * and initializes the attributes above
     */
    public WordleHelper() {

        possible = new ArrayList<String>();
        in = new ArrayList<Character>();
        wordleWords = new Dictionary("C://wordlewords.txt");
        for (int i = 0; i < wordleWords.getSize(); i++) {
            possible.add(wordleWords.getWord(i));
        }
        this.feedback = new char[5];
        build = new char[5];
        possibleCharactersPerIndex = new String[5];
        for (int i = 0; i < 5; i++) {
            this.feedback[i] = feedback[i];
            possibleCharactersPerIndex[i] = "abcdefghijklmnopqrstuvwxyz";
        }
    }

    /**
     * This method finds a good possible guess via
     * a frequency approach
     * 
     * @return guess suggestion
     */
    public String suggestionFromPossible() {
        int[] frequencies = new int[26];
        int[] scores = new int[possible.size()];

        for (int i = 0; i < possible.size(); i++) {
            String currentWord = possible.get(i);
            for (int j = 0; j < 5; j++) {
                char visitedLetter = currentWord.charAt(j);
                if (currentWord.indexOf(visitedLetter) != -1 && !in.contains(visitedLetter)) {
                    frequencies[(int) visitedLetter - 97]++;
                }
            }
        }
        for (int i = 0; i < possible.size(); i++) {
            String currentWord = possible.get(i);
            for (int j = 0; j < 5; j++) {
                int frequencyIndex = (int) currentWord.charAt(j) - 97;
                int score = frequencyIndex;
                scores[i] = scores[i] + score;
            }
        }
        int maxScore = scores[0];
        int maxScoreIndex = 0;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                maxScoreIndex = i;
            }
        }
        suggestion = possible.get(maxScoreIndex);
        return suggestion;
    }

    /**
     * This method updates the possible words left
     * based on the last guess
     * 
     * @param newGuess
     * @param feedback
     */
    public void updatePossible(String newGuess, char[] feedback) {
        guess = newGuess;
        for (int i = 0; i < 5; i++) {
            if (feedback[i] == '!' || feedback[i] == '?') {
                in.add(guess.charAt(i));
            }
        }
        for (int j = 0; j < 5; j++) {
            if (feedback[j] == 'X' && in.contains(guess.charAt(j))) {
                String s = possibleCharactersPerIndex[j];
                possibleCharactersPerIndex[j] = s.replace(guess.charAt(j) + "", "");
            } else if (feedback[j] == 'X' && !in.contains(guess.charAt(j))) {
                for (int k = 0; k < 5; k++) {
                    String s = possibleCharactersPerIndex[k];
                    possibleCharactersPerIndex[k] = s.replace(guess.charAt(j) + "", "");
                }

            }
            if (feedback[j] == '?') {
                String s = possibleCharactersPerIndex[j];
                possibleCharactersPerIndex[j] = s.replace(guess.charAt(j) + "", "");
            }
            if (feedback[j] == '!') {
                String s = possibleCharactersPerIndex[j];
                char fixed = guess.charAt(j);
                possibleCharactersPerIndex[j] = fixed + "";
            }
        }
        for (int i = 0; i < wordleWords.getSize(); i++) {
            boolean remove = false;
            int count = in.size();
            String currentWord = wordleWords.getWord(i);
            for (int j = 0; j < 5; j++) {
                String possibleLetters = possibleCharactersPerIndex[j];

                if (possibleLetters.indexOf(currentWord.charAt(j)) == -1) {
                    remove = true;
                }
            }
            for (int j = 0; j < in.size(); j++) {
                if (currentWord.indexOf(in.get(j)) == -1) {
                    remove = true;
                } else {
                    count--;
                }
            }
            if (remove) {
                possible.remove(currentWord);
            } else if (count != 0) {
                possible.remove(currentWord);
            }
        }
    }
}