/**
 * Creates, modifies, and returns information about
 * a box in the Deal Game
 *
 * @author Sean DeZurik
 */
public class Box {
    /** Monetary value in the box */
    private double value;
    /** Whether or not the box has been opened */
    private boolean isOpen;
    
    /**
     * Constructor
     *
     * @param value a double with value for the box
     */
    public Box(double value) {
        this.value = value;
        isOpen = false;
    }
    
    /**
     * Getter method for value
     *
     * @return a double with value in box
     */
    public double getValue() {
        return value;
    }
    
    /**
     * Gives status whether box is open or not
     *
     * @return a boolean that is true if box is open
     *         and false if the box is not open
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * Sets the state of the box as open
     */
    public void open() {
        isOpen = true;
    }
    
    /**
     * Gives textual representation of the object
     *
     * @return a String with text representation of object
     */
    public String toString() {
        return "Open: " + isOpen() + " Value: " + getValue();
    }
}