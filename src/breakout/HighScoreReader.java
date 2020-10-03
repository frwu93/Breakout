package breakout;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreReader {

  private static String filePath = "data/highscore/highscores.txt";

  /**
   * Returns the high score stored in the file storing high scores
   * @return int that represents the high score
   */
  public int readInHighScore() {
    try {
      Scanner scanner = new Scanner(new File(filePath));
      return Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
      throw new RuntimeException("Invalid High Score File");
    }
  }

  /**
   * Writes new high score to file
   * @param updatedHighScore int representing a new updated high score to write to file
   */
  public void replaceHighScore(int updatedHighScore) {
    try {
      FileWriter fileWriter = new FileWriter(new File(filePath), false);
      fileWriter.write(Integer.toString(updatedHighScore));
      fileWriter.close();
    } catch (Exception e) {
      throw new RuntimeException("Invalid High Score File");
    }
  }

  /**
   * Testing: changes the filepath of the high score directory
   * @param filePath String representing the file path of the high score text file
   */
  public static void setHighScoreFilePath(String filePath) {
    HighScoreReader.filePath = filePath;
  }
}

