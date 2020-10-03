package breakout;

import javafx.scene.shape.Rectangle;

/**
 * The GamePiece class is an abstract class that acts as the superclass of the blocks and power-ups
 * used in the game. It contains both abstract and non-abstract methods that are inherent to both
 * subclasses. GamePiece extends the JavaFX Rectangle class.
 */

public abstract class GamePiece extends Rectangle {

  private int lives;
  private double xVelocity;
  private double yVelocity;

  /**
   * Creates a Game Piece at a specified location and with a specified image
   * @param x double representing the X position
   * @param y double representing the Y position
   * @param width double representing the width
   * @param height double representing the height
   * @param lives int representing how many times the block can be hit
   */
  public GamePiece(double x, double y, double width, double height, int lives) {
    super(x, y, width, height);
    this.lives = lives;
    xVelocity = 0;
    yVelocity = 0;
  }

  /**
   * Gets number of lives left
   * @return int representing how many lives are left
   */
  public int getLives() {
    return lives;
  }

  /**
   * Sets lives to new number of lives
   * @param lives int representing lives
   */
  public void setLives(int lives) {
    this.lives = lives;
  }

  /**
   * Subtracts 1 life from block
   */
  public void subtractLife() {
    this.lives -= 1;
  }

  /**
   * Moves the block in a certain direction (may depend on which row it is in)
   * @param movement String representing a specified movement of block
   * @param row int representing what row the block is in
   */
  public void setMovement(String movement, int row) {
    switch (movement) {
      case "sideways" -> setSidewaysMovement(row);
      case "descending" -> setVerticalMovement();
    }
  }

  /**
   * Updates position of the block based on velocities
   * @param elapsedTime double representing the time length of 1 frame
   */
  public void updatePosition(double elapsedTime) {
    setX(getX() + xVelocity * elapsedTime);
    setY(getY() + yVelocity * elapsedTime);
  }

  /**
   * Gets the position of the left side
   * @return double position of left side
   */
  public double getLeft() {
    return getX();
  }

  /**
   * Gets the position of the right side
   * @return double position of right side
   */
  public double getRight() {
    return getX() + getWidth();
  }

  /**
   * Gets the position of the top
   * @return double position of top
   */
  public double getTop() {
    return getY();
  }

  /**
   * Gets the position of the bottom
   * @return double position of bottom
   */
  public double getBottom() {
    return getY() + getHeight();
  }

  /**
   * Reverses X velocity upon collision
   */
  public void updateXVelocityUponCollision() {
    reverseXVelocity();
  }

  /**
   * Reverses Y velocity upon collision
   */
  public void updateYVelocityUponCollision() {
    reverseYVelocity();
  }

  /**
   * Returns which row the Game Piece is in
   * @return int representing row position
   */
  public int getRowPosition() {
    return Integer.parseInt(this.getId().substring(5, 6));
  }

  /**
   * Returns which column the Game Piece is in
   * @return int representing column position
   */
  public int getColPosition() {
    return Integer.parseInt(this.getId().substring(6, 7));
  }

  /**
   * Sets new X Velocity
   * @param xVelocity double representing the new X velocity of the Game Piece
   */
  public void setXVelocity(double xVelocity) {
    this.xVelocity = xVelocity;
  }

  /**
   * Sets new Y Velocity
   * @param yVelocity double representing the new Y velocity of the Game Piece
   */
  public void setYVelocity(double yVelocity) {
    this.yVelocity = yVelocity;
  }

  /**
   * Gets the current X Velocity
   * @return double representing the current X Velocity
   */
  public double getXVelocity() {
    return xVelocity;
  }

  /**
   * Gets the current Y Velocity
   * @return double representing the current Y Velocity
   */
  public double getYVelocity() {
    return yVelocity;
  }

  private void reverseXVelocity() {
    xVelocity *= -1;
  }

  private void reverseYVelocity() {
    yVelocity *= -1;
  }

  private void setSidewaysMovement(int row) {
    if (row % 2 == 1) {
      setXVelocity(-50);
    } else {
      setXVelocity(50);
    }
  }

  private void setVerticalMovement() {
    setYVelocity(-50);
  }

  /**
   * Method to be implemented by subclasses that updates status of block
   */
  abstract void updateStatus();

}
