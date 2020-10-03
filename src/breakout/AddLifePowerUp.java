package breakout;

import javafx.scene.image.Image;

import java.io.File;

/**
 * The AddLifePowerUp is responsible for creating a power-up that, when applied, gives the
 * user one extra life when playing the game. It extends PowerUp and implements all abstract methods,
 * as well as other methods specific to this class's functioning.
 */
public class AddLifePowerUp extends PowerUp {

  /**
   * Creates an Add Life Power Up which adds 1 life when hit by the paddle
   * @param x double representing the X position
   * @param y double representing the Y position
   * @param width double representing the width
   * @param height double representing the height
   */
  public AddLifePowerUp(double x, double y, double width, double height) {
    super(x, y, width, height, new Image(new File(GameStatus.HEART_FILE_PATH).toURI().toString()));
  }

  /**
   * Adds 1 life to the game
   * @param game Game for the power up to take effect on
   */
  @Override
  public void updateGameStatus(Game game) {
    if (!super.isActive()) {
      game.addLife();
      super.setActive();
    }
  }

  /**
   * No effect (not timer dependent)
   * @param game Game for the power up to reset after the time is up
   */
  @Override
  public void resetGameStatus(Game game) {
  } // not timer dependent
}
