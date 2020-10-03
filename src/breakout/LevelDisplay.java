package breakout;

/**
 * The LevelDisplay class is responsible for displaying the current level in the game console. It
 * extends the NumericDisplay class and implements all abstract methods, as well as additional methods
 * specific to the LevelDisplay class's functioning.
 */

public class LevelDisplay extends NumericDisplay {

  private int level;

  /**
   * Creates an instance of the LevelDisplay. No parameters are needed because all
   * needed information is stored in the GameStatus class, which is then passed through a super call.
   */
  public LevelDisplay() {
    super("Level", GameStatus.FIRST_LEVEL, GameStatus.LEVEL_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
    this.level = GameStatus.FIRST_LEVEL;
  }

  /**
   * Increases the current level by one and updates the display to reflect the new level
   */
  public void incrementLevel() {
    level += 1;
    updateDisplay();
  }

  /**
   * Updates the display with the current level
   */
  @Override
  public void updateDisplay() {
    setText("Level: " + level);
  }

  /**
   * Sets the current level to the given level and updates the display to reflect the new level
   * @param level the new level to be set to
   */
  public void setLevel(int level) {
    this.level = level;
    updateDisplay();
  }

  /**
   * Returns the current level
   * @return the current level
   */
  public int getLevel() {
    return level;
  }

  /**
   * Resets the level display to the first level
   */
  @Override
  public void resetDisplayValue() {
    level = GameStatus.FIRST_LEVEL;
  }
}
