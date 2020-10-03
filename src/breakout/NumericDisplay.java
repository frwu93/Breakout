package breakout;

import javafx.scene.text.Text;

/**
 * The NumericDisplay class is an abstract class that acts as the superclass of all display-related
 * classes for the Game project. It contains two abstract methods: updateDisplay and resetDisplayValue.
 * NumericDisplay extends the JavaFX Text class.
 */

public abstract class NumericDisplay extends Text {

  /**
   * Creates an instance of the NumericDisplay class
   * @param label the Text label of the display
   * @param display the integer to display
   * @param xPos the x-coordinate of the upper-left corner of the display
   * @param yPos the y-coordinate of the upper-left corner of the display
   */
  public NumericDisplay(String label, int display, int xPos, int yPos) {
    super(label + ": " + display);
    setX(xPos);
    setY(yPos);
  }

  /**
   * Updates display with current value
   */
  public abstract void updateDisplay();

  /**
   * Resets display to a pre-determined text label and value
   * */
  public abstract void resetDisplayValue();

}
