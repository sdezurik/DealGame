import java.io.*;
import java.util.Scanner;

/**
 * Handles the logic behind the Deal or No Deal
 * game
 *
 * @author Dr. Jessica Young Schmidt
 * @author Sean DeZurik
 */
public class DealGame {
  
    /** Number of boxes in the game */
    public static final int NUM_BOXES = 26;
  
  
    /** Monetary values that will be used in the game */
    public static final double[] BOX_VALUES = {0.01, 1, 5, 10, 25, 50, 75, 
                                               100, 200, 300, 400, 500, 
                                               750, 1000, 5000, 10000, 
                                               25000, 50000, 75000, 
                                               100000, 200000, 300000, 
                                               400000, 500000, 750000, 
                                               1000000};
  
    /** Number of boxes to be opened in each round */
    public static final int[] BOXES_IN_ROUND = {0, 6, 5, 4, 3, 2, 
                                                1, 1, 1, 1, 1};
                                                
    /** Number of rounds in the game */
    public static final int NUM_ROUNDS = 10;
  
    /** Number of times boxes are swapped during the set up */
    private static final int BOX_SWAPS = 500;
  
    /** Name of the file that contains the high score */
    public static final String HIGH_SCORE_FILE = "highscore.txt";
    
    /** Divisor for calculating bank's offer */
    private static final int OFFER_DIVISOR = 10;
  
    /** Create a list of boxes for the game */
    private BoxList boxList;
    /** Index of the player's box in the box list */
    private int playerBoxIndex;
    /** Whether player has chosen a box or not */
    private boolean hasPlayerChosenBox;
    /** Current round number */
    private int round;
    /** How many boxes opened in this round */
    private int boxesOpenedThisRound;
    /** How many total boxes opened in the game */
    private int boxesOpenedTotal;
    /** Highest score over all plays of the game */
    private double highScore;
    
    /**
     * Constructor for DealGame class
     *
     * @param testing true if in testing mode and
     *        false if not testing
     */
    public DealGame(boolean testing) {
        // Create a new box list for the game
        boxList = new BoxList(BOX_VALUES);
        
        // Only shuffle boxes around if not testing
        if (!testing) {
            boxList.shuffle(BOX_SWAPS);
        }
        
        // Initialize to first round with no boxes opened at all
        round = 1;
        boxesOpenedThisRound = 0;
        boxesOpenedTotal = 0;
        /** File with the high score */
        File highScoreFile = null;
        /** Scanner for high score file */
        Scanner scoreInput = null;
        
        // Try to access the high score file
        try {
            highScoreFile = new File(HIGH_SCORE_FILE);
            scoreInput = new Scanner(highScoreFile);
        } catch (FileNotFoundException error) {
            // Print error but continue
            System.out.println("Could not access " + highScoreFile.getName());
        }
        
        // Check for file with previous high score
        if (highScoreFile.exists()) {
        
            // Make sure file has the expected integer
            if (scoreInput.hasNextDouble()) {
                // Set high score to number in file
                highScore = scoreInput.nextDouble();
                scoreInput.close();
            } else {
                // Not a double in the file
                System.out.println("Incorrect content in " + highScoreFile.getName());
                System.exit(1);
            }
            
        } else {
            // No previous high score
            highScore = 0.0;
        }
        
    }
    
    /**
     * Provides whether the player has chosen a box
     *
     * @return boolean true if player has chosen a box
     *         and false if not
     */
    public boolean hasPlayerChosenBox() {
        return hasPlayerChosenBox;
    }
    
    /**
     * Sets the choice for a player's box. If the choice
     * has already been set, then it opens that box.
     *
     * @param index an int with index in the box list
     */
    public void selectBox(int index) {
        // Player has not chosen a box yet
        if (!hasPlayerChosenBox()) {
            // Make it the player's choice
            playerBoxIndex = index;
            hasPlayerChosenBox = true;
        } else {
            // Player has already chosen a box so mark
            // that box as open and increment the counts
            // of the number of boxes opened
            boxList.open(index);
            boxesOpenedThisRound++;
            boxesOpenedTotal++;
        }
    }
    
    /**
     * Gives number of boxes not yet opened in this round
     *
     * @return an int with number of boxes to open this round
     */
    public int getBoxesRemainingToOpenThisRound() {
        return BOXES_IN_ROUND[round] - getBoxesOpenedThisRound();
    }
    
    /**
     * Gives number of boxes opened this round
     *
     * @return int with number of boxes opened this round
     */
    public int getBoxesOpenedThisRound() {
        return boxesOpenedThisRound;
    }
    
    /**
     * Get the current round
     *
     * @return an int with the current round
     */
    public int getRound() {
        return round;
    }
    
    /**
     * Sets values to star the next round
     */
    public void startNextRound() {
        // Increment the round
        round++;
        // Set boxes opened in this round to zero
        boxesOpenedThisRound = 0;
    }
    
    /**
     * Determines if the end of the round has been reached.
     * It is the end of a round if the number of boxes opened
     * has reached the limit of the number of boxes allowed
     * to be opened for that round.
     *
     * @return boolean true if end of round has been reached
     *                 false if end of round has not been reached
     */
    public boolean isEndOfRound() {
        if (getBoxesOpenedThisRound() < BOXES_IN_ROUND[round]) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Gets the value of the box chosen by the player
     *
     * @return double with value in the box at the index
     */
    public double getPlayerBoxValue() {
        return boxList.getValue(playerBoxIndex);
    }
    
    /**
     * Gives whether box is open or not at an index
     *
     * @param index an int that is index in box list
     * @return boolean true if box is open and
     *         false if it is not
     */
    public boolean isBoxOpen(int index) {
        return boxList.isOpen(index);
    }
    
    /**
     * Gives the value of the box at the particular index
     *
     * @param index an int that is an index in the box list
     * @return double that is the value in the box
     */
    public double getValueInBox(int index) {
        return boxList.getValue(index);
    }
    
    /**
     * Calculate and return the bank's current offer. It is
     * the average value of the unopened boxes (including the
     * player's box) * current round number / 10
     *
     * @return double with value of current offer
     */
    public double getCurrentOffer() {
        return boxList.averageValueOfUnopenedBoxes() * round / OFFER_DIVISOR;
    }
    
    /**
     * Gives highest score in all plays of the game
     *
     * @return double that is the highest score
     */
    public double getHighScore() {
        return highScore;
    }
    
    /**
     * Compares the value to the highest score so far. If the
     * value is greater, it returns true and sets the highest
     * score to the new value and writes it to the file.
     * Otherwise it returns false.
     *
     * @param value a double that is the score to compare to the
     *        highest score
     * @return boolean that is true if the value is greater than
     *         the high score and false if the value is not
     */
    public boolean isNewHighScore(double value) {
        // See if current score is higher than highest score
        if (value > highScore) {
            // Set high score to current value
            highScore = value;
            /** File object for writing to highest score file */
            File scoreFile = null;
            /** PrintStream object for writing to highest score file */
            PrintStream writeFile = null;
            
            // Attempt to open file for writing
            try {
                scoreFile = new File(HIGH_SCORE_FILE);
                writeFile = new PrintStream(scoreFile);
            } catch (FileNotFoundException error) {
                System.out.println("Could not open " + scoreFile.getName() + " for writing");
                System.exit(1);
            }
            
            // Save high score to the file
            writeFile.print(value);
            // Close the file
            writeFile.close();
            return true;
        } else {
            return false;
        }
    }
}