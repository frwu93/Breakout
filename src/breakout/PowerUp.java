package breakout;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public abstract class PowerUp extends GamePiece {

  private int timer;
  private boolean falling;
  private boolean powerUpActive;

  /**
   * Creates a Power Up at a specified location and with a specified image
   * @param x double representing the X position
   * @param y double representing the Y position
   * @param width double representing the width
   * @param height double representing the height
   * @param image Image representing the image to be displayed on the powerup
   */
  public PowerUp(double x, double y, double width, double height, Image image) {
    super(x, y, width, height, 2);
    timer = GameStatus.POWER_UP_TIMER;
    powerUpActive = false;
    setFill(new ImagePattern(image));
  }

  /**
   * Sets the power up to begin falling
   */
  public void beginFalling() {
    setYVelocity(50);
    falling = true;
  }

  /**
   * Updates the block to new position during fall
   * @param elapsedTime double representing the time spent per frame
   */
  public void fall(double elapsedTime) {
    updatePosition(elapsedTime);
  }

  /**
   * Sets the power up's effects to be active
   */
  public void setActive() {
    powerUpActive = true;
  }

  /**
   * Returns if the power up's effects are active
   * @return boolean active
   */
  public boolean isActive() {
    return powerUpActive;
  }

  /**
   * Returns if the power up is falling
   * @return boolean falling
   */
  public boolean isFalling() {
    return falling;
  }

  /**
   * decreases timer by 1 "frame"
   */
  public void decrementTimer() {
    timer -= 1;
  }

  /**
   * Returns if timer has ended
   * @return boolean that represents if timer is less than 0
   */
  public boolean hasTimerEnded() {
    return timer <= 0;
  }

  /**
   * Calls subtract life to subtract 1 life
   */
  public void updateStatus() {
    subtractLife();
  }

  /**
   * Gets attributes from block so that the power up can replace it directly
   * @param block block for the powerup to get its attributes from
   */
  public void getAttributesFromBlock(Block block) {
    setId(block.getId());
    setXVelocity(block.getXVelocity());
    setYVelocity(block.getYVelocity());
  }

  /**
   * Method to be implemented in subclasses to apply the power up's effect
   * @param game Game for power up to apply its effects to
   */
  abstract void updateGameStatus(Game game);

  /**
   * Method to be implemented in subclass that resets the power up's effect
   * @param game Game for power up to reset its effects for
   */
  abstract void resetGameStatus(Game game);
}
