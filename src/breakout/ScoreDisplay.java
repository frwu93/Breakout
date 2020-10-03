package breakout;

/**
 * The ScoreDisplay class is responsible for displaying the player's current score in the game
 * console. It extends the Numeric Display class, implementing all abstract methods as well as
 * additional methods that are specific to the functioning of ScoreDisplay.
 */

public class ScoreDisplay extends NumericDisplay {

  private int score;
  private int checkPointScore;
  private int bonus;
  private final int POINT_VALUE_PER_HIT = 10;
  private final int CONSECUTIVE_HIT_BONUS = 5;

  /**
   * Creates an instance of the ScoreDisplay class. No parameters are needed because all
   * needed information is stored in the GameStatus class, which is then passed through a super call.
   */
  public ScoreDisplay() {
    super("Score", GameStatus.START_SCORE, GameStatus.SCORE_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
    this.score = GameStatus.START_SCORE;
    this.bonus = 0;
    this.checkPointScore = 0;
  }


  /**
   * Increases the score after block breakage via a scoring algorithm, and then updates the display
   * to reflect the increased score.
   */
  public void increaseScore() {
    score += POINT_VALUE_PER_HIT + bonus;
    bonus += CONSECUTIVE_HIT_BONUS;
    updateDisplay();
  }

  /**
   * Updates display in console with the current score.
   */
  @Override
  public void updateDisplay() {
    setText("Score: " + score);
  }

  /**
   * Resets the display value to the last score check point, which is the score as of the last passed
   * level.
   */
  @Override
  public void resetDisplayValue() {
    score = checkPointScore;
    resetBonus();
    updateDisplay();
  }

  /**
   * Returns the current score
   * @return the current score
   */
  public int getScore() {
    return score;
  }

  /**
   * Resets the score bonus to 0
   */
  public void resetBonus() {
    bonus = 0;
  }

  /**
   * Sets the checkpoint score equal to the current score
   */
  public void setCheckPointScore() {
    checkPointScore = score;
  }
}
