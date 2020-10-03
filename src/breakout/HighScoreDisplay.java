package breakout;

public class HighScoreDisplay extends NumericDisplay {

  private int highScore;
  private final HighScoreReader scoreReader;

  /**
   * Creates a display showing the high score
   * @param highScore int representing a high score to be displayed
   */
  public HighScoreDisplay(int highScore) {
    super("High Score", highScore, GameStatus.HIGH_SCORE_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
    this.highScore = highScore;
    scoreReader = new HighScoreReader();
  }

  /**
   * Updates high score if the current score is greater than the high score
   * @param currentScore int representing the current score
   */
  public void updateHighScore(int currentScore) {
    if (currentScore >= highScore) {
      highScore = currentScore;
      scoreReader.replaceHighScore(currentScore);
      updateDisplay();
    }
  }

  /**
   * Updates display with high score
   */
  @Override
  public void updateDisplay() {
    setText("High Score: " + highScore);
  }

  /**
   * Resets display with original high score
   */
  @Override
  public void resetDisplayValue() {
    highScore = scoreReader.readInHighScore();
  }

  /**
   * Clears high score and replaces it with 0
   */
  public void clearHighScore() {
    highScore = 0;
    scoreReader.replaceHighScore(highScore);
    updateDisplay();
  }

  /**
   * Gets the current high score
   * @return int representing the current high score
   */
  public int getHighScore() {
    return highScore;
  }
}
