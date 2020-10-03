package breakout;

/**
 * The LivesDisplay class is responsible for displaying the number of lives remaining in the
 * Game console. It extends NumericDisplay and implements all of its abstract methods, as well as
 * other methods specific to the LivesDisplay class (i.e addLife, subtractLife).
 */

public class LivesDisplay extends NumericDisplay {

  int lives;

  /**
   * Creates an instance of the LivesDisplay object. No parameters are needed because all
   * needed information is stored in the GameStatus class, which is then passed through a super call.
   */
  public LivesDisplay() {
    super("Lives", GameStatus.STARTING_LIVES, GameStatus.LIVES_DISPLAY_XPOS,
        GameStatus.DISPLAY_YPOS);
    this.lives = 3;
  }

  /**
   * Returns the number of lives remaining at any given point in the game.
   * @return the number of lives remaining
   */
  public int getLives() {
    return lives;
  }


  /**
   * Adds a life to the current remaining lives and updates the display to reflect the new number
   * of lives.
   */
  public void addLife() {
    lives += 1;
    updateDisplay();
  }

  /**
   * Subtracts a life from the current remaining lives and updates the display to reflect the new number
   * of lives.
   */
  public void subtractLife() {
    lives -= 1;
    updateDisplay();
  }

  /**
   * Updates display in console with the current number of lives remaining.
   */
  @Override
  public void updateDisplay() {
    setText("Lives: " + lives);
  }

  /**
   * Resets the lives display to show three lives, which is the default number of lives;
   */
  @Override
  public void resetDisplayValue() {
    lives = GameStatus.STARTING_LIVES;
    updateDisplay();
  }
}
