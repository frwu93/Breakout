package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  private final double startX;

  /**
   * Creates a Paddle at a specified location and with a specified image
   * @param x double representing the starting X position
   * @param y double representing the starting Y position
   * @param width double representing the width
   * @param height double representing the height
   * @param color Paint representing the initial color of the paddle
   */
  public Paddle(double x, double y, double width, double height, Paint color) {
    super(x, y, width, height);
    startX = x;
    setFill(color);
    setId("paddle");
    setArcHeight(10);
    setArcWidth(10);
  }

  /**
   * Resets the paddle to start position
   */
  public void reset() {
    setX(startX);
  }

  /**
   * Gets the starting X position of the block
   * @return double representing the starting X position
   */
  public double getStartX() {
    return startX;
  }
}
