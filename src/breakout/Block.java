package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The Block class is responsible for creating representations of the blocks in the game console.
 * Each block has a given amount of lives and an associated color.
 */

public class Block extends GamePiece {

  private final Paint ONE_HIT_BLOCK_COLOR = Color.web("#bae1ff");
  private final Paint TWO_HIT_BLOCK_COLOR = Color.web("#ffb3ba");
  private final Paint THREE_HIT_BLOCK_COLOR = Color.web("#baffc9");

  /**
   * Creates a Block at a specified location and with a specified image
   * @param x double representing the starting X position
   * @param y double representing the starting Y position
   * @param width double representing the width
   * @param height double representing the height
   * @param lives int representing the lives of the Block
   */
  public Block(double x, double y, double width, double height, int lives) {
    super(x, y, width, height, lives);
    setFill(determineColor(lives));
  }

  /**
   * Updates status of the block when hit. One life is subtracted and the color is updated
   * according to the new number of lives.
   */
  public void updateStatus() {
    subtractLife();
    setFill(determineColor(getLives()));
  }

  /**
   * Return the color of the block based on the number of lives it has remaining
   * @param lives the current number of lives remaining
   * @return the block's color
   */
  private Paint determineColor(int lives) {
    return switch (lives) {
      case 1 -> ONE_HIT_BLOCK_COLOR;
      case 2 -> TWO_HIT_BLOCK_COLOR;
      default -> THREE_HIT_BLOCK_COLOR;
    };
  }
}
