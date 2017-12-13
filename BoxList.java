import java.util.Random;

/**
 * Creates a list of Box items that are used in the game
 *
 * @author Sean DeZurik
 */
public class BoxList {
    /** List of all the boxes in the game */
    private Box[] boxes;
    
    /**
     * Constructor for BoxList class
     *
     * @param monetaryAmounts a double with the list
     *        of values for each box in the game
     */
    public BoxList(double[] monetaryAmounts) {
        /** Number of boxes to create */
        int numBoxes = monetaryAmounts.length;
        // Initialize array of Box items
        boxes = new Box[numBoxes];
        
        // Instantiate each Box in the array and set its value
        for (int i = 0; i < numBoxes; i++) {
            boxes[i] = new Box(monetaryAmounts[i]);
        }
    }
    
    /**
     * Get value of the box at the particular index
     *
     * @param index an int that is an index in the Box array
     * @return a double with the value in the box
     */
    public double getValue(int index) {
        return boxes[index].getValue();
    }
    
    /**
     * Show whether the box at the index has been opened or not
     *
     * @param index an int that is an index in the Box array
     * @return a boolean that is true if the box has been opened
     *         and false if it has not
     */
    public boolean isOpen(int index) {
        return boxes[index].isOpen();
    }
    
    /**
     * Opens the box at the particular index
     *
     * @param index an int that is an index in the Box array
     */
    public void open(int index) {
        boxes[index].open();
    }
    
    /**
     * Provides the average value in the boxes that have not
     * yet been opened
     *
     * @return a double with the average value of unopened boxes
     */
    public double averageValueOfUnopenedBoxes() {
        /** Sum of values in unopened boxes */
        double total = 0.0;
        /** Count of unopened boxes */
        int count = 0;
        
        // Check every box in the array
        for (int i = 0; i < boxes.length; i++) {
        
            // If the box has not been opened, add its value
            // to the total sum
            if (!boxes[i].isOpen()) {
                total += boxes[i].getValue();
                count++;
            }
        }
        
        // Divide total sum by count of unopened to get the average
        if (count == 0) {
            return 0.0;
        } else {
            return total / count;
        }
    }
    
    /**
     * Randomly swaps the positions of two boxes in the array
     *
     * @param numberOfSwaps an int with the number of times that
     *        two random boxes will be swapped
     */
    public void shuffle(int numberOfSwaps) {
        /** Create a Random number object */
        Random randomNumber = new Random();
        
        for (int i = 0; i < numberOfSwaps; i++) {
            /** Get a random number between zero and the number of boxes */
            int random1 = randomNumber.nextInt(boxes.length);
            /** A second random number, initially set to the first random number */
            int random2 = random1;
        
            // Generate a new second random number as long as it is the same value
            // as the first random number
            while (random2 == random1) {
                // Between zero and number of boxes
                random2 = randomNumber.nextInt(boxes.length);
            }
            
            // Swap the boxes
            Box temp = boxes[random1];
            boxes[random1] = boxes[random2];
            boxes[random2] = temp;
        }
    }
    
    /**
     * Textual representation of the list of boxes
     *
     * @return String that is textual representation of each
     *         box in the list of boxes
     */
    public String toString() {
        /** String to return with textual representation of each box */
        String representation = "";
        
        // Get toString for each box in the box list
        for (int i = 0; i < boxes.length; i++) {
            // Append box toString to previous ones
            representation += boxes[i].toString() + "\n";
        }
        
        return representation;
    }
}