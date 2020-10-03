package breakout;

import javafx.scene.image.Image;

import java.io.File;

public class SlowDownPowerUp extends PowerUp {

  /**
   * Creates an Slow Down Power Up which slows down the ball when hit by the paddle
   * @param x double representing the X position
   * @param y double representing the Y position
   * @param width double representing the width
   * @param height double representing the height
   */
  public SlowDownPowerUp(double x, double y, double width, double height) {
    super(x, y, width, height,
        new Image(new File(GameStatus.SLOW_DOWN_FILE_PATH).toURI().toString()));
  }

  /**
   * Scales ball speed down by a factor specified in Game Status
   * @param game Game for the power up to take effect on
   */
  @Override
  public void updateGameStatus(Game game) {
    if (!super.isActive()) {
      game.scaleBallVelocity(GameStatus.SLOW_DOWN_FACTOR);
      super.setActive();
    }
  }

  /**
   * Resets ball in game to original speed
   * @param game Game for the power up to reset after the time is up
   */
  @Override
  public void resetGameStatus(Game game) {
    game.scaleBallVelocity(1.0/GameStatus.SLOW_DOWN_FACTOR); // back to original speed
  }
}
