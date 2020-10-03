package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The Ball class is responsible for the ball representation in the breakout game. It extends JavaFX's
 * Circle class and includes methods to get various positional coordinates of the ball, change its
 * velocity upon impact, and update the ball's position.
 */
public class Ball extends Circle {

  private final int startX;
  private final int startY;
  private final int radius;
  private double xVelocity;
  private double yVelocity;

  /**
   * Creates an instance of the Ball class
   * @param xPosition the x-coordinate of the ball's center
   * @param yPosition the y-coordinate of the ball's center
   * @param radius the radius of the ball
   * @param color the color of the ball
   */

  public Ball(int xPosition, int yPosition, int radius, Paint color) {
    super(xPosition, yPosition, radius, color);
    this.startX = xPosition;
    this.startY = yPosition;
    this.radius = radius;
    this.xVelocity = 0;
    this.yVelocity = 0;
    setId("ball");
  }

  /**
   * Resets the ball to its starting position and sets all velocities to zero.
   */
  public void reset() {
    setCenterX(startX);
    setCenterY(startY);
    xVelocity = 0;
    yVelocity = 0;
  }

  /**
   * Sets the x velocity to the given value
   * @param value the value to assign towards the ball's x-velocity
   */
  public void setXVelocity(double value) {
    xVelocity = value;
  }

  /**
   * Sets the y velocity to the given value
   * @param value the value to assign towards the ball's y-velocity
   */
  public void setYVelocity(double value) {
    yVelocity = value;
  }

  /**
   * Updates the ball's position based on the amount of elapsed time.
   * @param elapsedTime the amount of elapsed time in seconds
   */
  public void updatePosition(double elapsedTime) {
    setCenterX(getCenterX() + xVelocity * elapsedTime);
    setCenterY(getCenterY() + yVelocity * elapsedTime);
  }

  /**
   * Updates the ball's velocity upon collision with a GamePiece. If the ball hits the top
   * or the bottom of the GamePiece, the y-velocity is reversed. If the ball hits the left or the
   * right of the GamePiece, the x-velocity is reversed.
   * @param gamePiece the game piece that is being checked with the ball
   */
  public void updateVelocityUponCollision(Rectangle gamePiece) {
    if (intersectBottom(gamePiece) || intersectTop(gamePiece)) {
      reverseYVelocity();
    } else if (intersectLeft(gamePiece) || intersectRight(gamePiece)) {
      reverseXVelocity();
    }
  }

  /**
   * Reverses the x velocity of the ball upon a collision with the border of a game piece or the
   * window border
   */
  public void updateXVelocityUponBorderCollision() {
    reverseXVelocity();
  }

  /**
   * Reverses the y velocity of the ball upon a collision with the border of a game piece or the
   * window border
   */
  public void updateYVelocityUponBorderCollision() {
    reverseYVelocity();
  }

  /**
   * Returns the current x-velocity
   * @return the current x-velocity
   */
  public double getXVelocity() {
    return xVelocity;
  }

  /**
   * Returns the current y-velocity
   * @return the current y-velocity
   */
  public double getYVelocity() {
    return yVelocity;
  }

  /**
   * Returns true if either the x-velocity or y-velocity is zero -- that is, the ball will bounce
   * unidirectionally for an infinite amount of time.
   * @return true if x or y velocity is 0.
   */
  public boolean notInMotion() {
    return xVelocity == 0 || yVelocity == 0;
  }

  /**
   * Returns the x-coordinate of the the left-most side of the ball
   * @return x-coordinate of the the left-most side of the ball
   */
  public double getLeft() {
    return getCenterX() - radius;
  }

  /**
   * Returns the x-coordinate of the the right-most point of the ball
   * @return x-coordinate of the the right-most point of the ball
   */
  public double getRight() {
    return getCenterX() + radius;
  }

  /**
   * Returns the y-coordinate of the the top side of the ball
   * @return y-coordinate of the the top side of the ball
   */
  public double getTop() {
    return getCenterY() - radius;
  }

  /**
   * Returns the y-coordinate of the the bottom-most point of the ball
   * @return y-coordinate of the the bottom-most point of the ball
   */
  public double getBottom() {
    return getCenterY() + radius;
  }

  /**
   * Returns the initial x-coordinate of the ball
   * @return the initial x-coordinate of the ball
   */
  public double getStartX() {
    return startX;
  }

  /**
   * Returns the initial y-coordinate of the ball
   * @return the initial y-coordinate of the ball
   */
  public double getStartY() {
    return startY;
  }

  /**
   * Reverses the x-component of the ball's velocity
   */
  private void reverseXVelocity() {
    setXVelocity(-1 * xVelocity);
  }

  /**
   * Reverses the y-component of the ball's velocity
   */
  private void reverseYVelocity() {
    setYVelocity(-1 * yVelocity);
  }

  /**
   * Returns true if the ball intersects the bottom side of the game piece
   * @param gamepiece the game piece to be used
   * @return true if the ball intersects the bottom side of the game piece
   */
  private boolean intersectBottom(Rectangle gamepiece) {
    return getBottom() >= gamepiece.getY() + gamepiece.getHeight();
  }

  /**
   * Returns true if the ball intersects the top side of the game piece
   * @param gamepiece the game piece to be used
   * @return true if the ball intersects the top side of the game piece
   */
  private boolean intersectTop(Rectangle gamepiece) {
    return getTop() <= gamepiece.getY();
  }

  /**
   * Returns true if the ball intersects the left side of the game piece
   * @param gamepiece the game piece to be used
   * @return true if the ball intersects the left side of the game piece
   */
  private boolean intersectLeft(Rectangle gamepiece) {
    return getLeft() <= gamepiece.getX();
  }

  /**
   * Returns true if the ball intersects the right side of the game piece
   * @param gamepiece the game piece to be used
   * @return true if the ball intersects the right side of the game piece
   */
  private boolean intersectRight(Rectangle gamepiece) {
    return getRight() >= gamepiece.getX() + gamepiece.getWidth();
  }
}
