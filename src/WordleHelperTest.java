package src;

import java.util.Random;
import java.util.Scanner;

/**
 * This class simulates a working Wordle game on the command line
 * and plays 100 games to test how many the WordleHelper will
 * correctly win
 * 
 * @author Brian Manesis
 * @version 1.0
 * @since 18 Feb 2022
 */
public class WordleHelperTest {

    /**
     * feedback of last guess attribute
     * number of wins attribute
     */
    public static char[] feedback;
    public static int wins;

    /**
     * The main method calls the play method 100 times and prints out how
     * many wins the wordle helper got
     * 
     * @param args
     */
    public static void main(String[] args) {
        WordleHelper w = new WordleHelper();
        int plays = 100;
        while (plays > 0) {
            play();
            plays--;
        }
        System.out.println("Number wins out of 100 is " + wins);
    }

    /**
     * This method generates a random 5 letter word and uses the WordleHelper to
     * make guesses, if it correctly guesses the word within 6 tries this play
     * is won
     */
    public static void play() {
        Scanner sc = new Scanner(System.in);
        WordleHelper help = new WordleHelper();
        Dictionary words = new Dictionary("Wordle-Solver\\src\\wordlewords.txt");
        Random rand = new Random();
        String word = words.getWord(rand.nextInt(2315));
        System.out.println("WORD: " + word);
        boolean correct = false;
        int tries = 6;
        int i = 0;
        String guess = new String("");
        while (i < tries && correct == false) {
            if (i == 0) {
                guess = "raise";
            } else {
                guess = help.suggestionFromPossible();
            }
            System.out.println("Make a guess");
            char[] feedback = new char[5];
            String copy = word;
            String guesscopy = guess;

            if (guess.equals(word)) {
                System.out.println("Correct");
                wins++;
                return;
            }
            for (int j = 0; j < 5; j++) {
                char guessletter = guesscopy.charAt(j);
                char wordletter = copy.charAt(j);
                int inIndex = in(guessletter, copy);
                if (guessletter == wordletter) {
                    feedback[j] = '!';
                    if (j != 4) {
                        copy = copy.substring(0, inIndex) + "!" + copy.substring(inIndex + 1);
                        guesscopy = guesscopy.substring(0, j) + "!" + guesscopy.substring(j + 1);
                    } else {
                        copy = copy.substring(0, j) + "!";
                        guesscopy = guesscopy.substring(0, j) + "!";
                    }
                }
            }
            for (int j = 0; j < 5; j++) {
                char guessletter = guesscopy.charAt(j);
                int inIndex = in(guessletter, copy);
                if (in(guessletter, copy) != -1 && guessletter != '!' && guessletter != '?') {
                    feedback[j] = '?';
                    if (j != 4) {
                        copy = copy.substring(0, inIndex) + "?" + copy.substring(inIndex + 1);
                        guesscopy = guesscopy.substring(0, j) + "?" + guesscopy.substring(j + 1);
                    } else {
                        copy = copy.substring(0, j) + "?";
                        guesscopy = guesscopy.substring(0, j) + "?";
                    }
                }
            }

            for (int j = 0; j < 5; j++) {
                char guessletter = copy.charAt(j);
                int inIndex = in(guessletter, copy);
                if (guesscopy.charAt(j) != '!' && guesscopy.charAt(j) != '?') {
                    feedback[j] = 'X';
                    if (j != 4) {
                        copy = copy.substring(0, inIndex) + "X" + copy.substring(inIndex + 1);
                        guesscopy = guesscopy.substring(0, j) + "X" + guesscopy.substring(j + 1);
                    } else {
                        copy = copy.substring(0, j) + "X";
                        guesscopy = guesscopy.substring(0, j) + "X";
                    }
                }
            }
            for (int j = 0; j < 5; j++) {
                System.out.print(guess.charAt(j) + "  ");
            }
            System.out.println("");
            for (int j = 0; j < 5; j++) {
                System.out.print(feedback[j] + "  ");
            }
            System.out.println("");

            help.updatePossible(guess, feedback);
            String suggestion = help.suggestionFromPossible();

            System.out.println("");
            System.out.println("Suggestion: " + suggestion);
            i++;
        }
    }

    /**
     * This method returns the position of a letter in a word
     * 
     * @param c
     * @param word
     * @return
     */
    public static int in(char c, String word) {
        for (int i = 0; i < 5; i++) {
            if (word.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }
}