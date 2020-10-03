package breakout;

import javafx.scene.image.Image;

import java.io.File;

/**
 * The EpandPaddlePowerUp is responsible for creating a power-up that, when applied, expands
 * the paddle's width to make deflecting the ball easier. It extends PowerUp and implements all
 * abstract methods, as well as other methods specific to this class's functioning.
 */

public class ExpandPaddlePowerUp extends PowerUp {

  /**
   * Creates an Expand Paddle Power Up which adds 1 life when hit by the paddle
   * @param x double representing the X position
   * @param y double representing the Y position
   * @param width double representing the width
   * @param height double representing the height
   */
  public ExpandPaddlePowerUp(double x, double y, double width, double height) {
    super(x, y, width, height,
        new Image(new File(GameStatus.EXPAND_PADDLE_FILE_PATH).toURI().toString()));
  }

  /**
   * Expands paddle to a factor specified in Game Status
   * @param game Game for the power up to take effect on
   */
  @Override
  public void updateGameStatus(Game game) {
    if (!super.isActive()) {
      game.scalePaddleSize(GameStatus.PADDLE_INCREASE_FACTOR);
      super.setActive();
    }
  }

  /**
   * No effect (not timer dependent)
   * @param game Game for the power up to reset after the time is up
   */
  @Override
  public void resetGameStatus(Game game) {
    game.scalePaddleSize(1.0 / GameStatus.PADDLE_INCREASE_FACTOR); // back to original size
  }
}
