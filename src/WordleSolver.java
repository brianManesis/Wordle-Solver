package src;

import java.util.Scanner;

/**
 * This class helps you play the wordle game
 * giving a suggestion from the WordleHelper class
 * 
 * @author Brian Manesis
 * @version 1.0
 * @since 18 Feb 2022
 */
public class WordleSolver {

    /**
     * The main method takes in your guess from your
     * Wordle game and gives you suggestions with high success rate
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WordleHelper wordleHelper = new WordleHelper();
        for (int i = 0; i < 6; i++) {
            String guess = sc.nextLine();
            String feed = sc.nextLine();
            char[] feedback = new char[5];
            feedback[0] = feed.charAt(0);
            feedback[1] = feed.charAt(1);
            feedback[2] = feed.charAt(2);
            feedback[3] = feed.charAt(3);
            feedback[4] = feed.charAt(4);
            wordleHelper.updatePossible(guess, feedback);
            System.out.println(wordleHelper.suggestionFromPossible());
        }
    }
}
