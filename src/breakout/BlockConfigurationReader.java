package breakout;

import java.util.Objects;
import javafx.scene.Group;

import java.io.File;
import java.util.Scanner;

/**
 * The BlockConfigurationReader class is responsible for parsing through files containing block
 * configurations and loading them into a data structure which can then be used to create the
 * same block configuration in the game console.
 */

public class BlockConfigurationReader {

  private final String directory = "data/blockfiles/";

  /**
   * Reads in the block configuration for a givene level into a 2D array of GamePieces
   * @param level the level to be read in
   * @param gridOfGamePieces the 2D array of GamePieces to store the read-in blocks
   */
  public void readInBlocks(int level, GamePiece[][] gridOfGamePieces) {
    String filePath = directory + "level" + level + ".txt";
    try {
      Scanner scanner = new Scanner(new File(filePath));
      int row = 0;
      String movement = scanner.nextLine();
      while (scanner.hasNextLine()) {
        String[] blockLives = scanner.nextLine().split(" ");
        createBlock(gridOfGamePieces, blockLives, movement, row, level);
        row++;
      }
    } catch (Exception e) {
      throw new RuntimeException("Invalid Block Configuration File!");
    }
  }

  /**
   * Creates a block based on the data that is read in
   * @param gridOfGamePieces the 2D array of GamePieces to store the block in
   * @param blockLives the number of lives the block starts with
   * @param movement the movement pattern of the block, if applicable
   * @param row the row in the 2D array of GamePieces where the block exists
   * @param level the level that contains the block
   */
  private void createBlock(GamePiece[][] gridOfGamePieces, String[] blockLives, String movement, int row, int level) {
    for (int i = 0; i < gridOfGamePieces[row].length; i++) {
      double xPos = i * (getBlockWidth(level) + GameStatus.GAP) + GameStatus.GAP;
      double yPos = row * (getBlockHeight(level) + GameStatus.GAP) + GameStatus.GAP
          + GameStatus.DISPLAYHEIGHT;
      gridOfGamePieces[row][i] = new Block(xPos, yPos, getBlockWidth(level),
          getBlockHeight(level),
          Integer.parseInt(blockLives[i]));
      gridOfGamePieces[row][i].setMovement(movement, row);
    }
  }

  /**
   * Loads a level onto the game console
   * @param root the Group to add the blocks into
   * @param level the level to be loaded
   * @return a 2D array of GamePieces that contains the block configuration
   */
  public GamePiece[][] loadLevel(Group root, int level) {
    GamePiece[][] gridOfGamePieces = new GamePiece[getRowNum(level)][getColNum(level)];
    readInBlocks(level, gridOfGamePieces);
    for (int i = 0; i < gridOfGamePieces.length; i++) {
      for (int j = 0; j < gridOfGamePieces[i].length; j++) {
        Block block = (Block) gridOfGamePieces[i][j]; // all game pieces are currently blocks
        if (block.getLives() > 0) {
          block.setId("block" + i + j);
          root.getChildren().add(block);
        }
      }
    }
    return gridOfGamePieces;
  }

  /**
   * Returns the number of rows of blocks for a given file
   * @param level the level to be used
   * @return the number of rows of blocks
   */
  private int getRowNum(int level) {
    try {
      String filePath = directory + "level" + level + ".txt";
      Scanner scanner = new Scanner(new File(filePath));
      int numOfRows = 0;
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        numOfRows++;
        scanner.nextLine();
      }
      return numOfRows;
    } catch (Exception e) {
      System.out.println("Invalid Block Configuration File!");
      return -1;
    }
  }

  /**
   * Returns the number of columns of blocks for a given file
   * @param level the level to be used
   * @return the number of columns of blocks
   */
  private int getColNum(int level) {
    try {
      String filePath = directory + "level" + level + ".txt";
      Scanner scanner = new Scanner(new File(filePath));
      scanner.nextLine();
      String[] columns = scanner.nextLine().split(" ");
      return columns.length;
    } catch (Exception e) {
      System.out.println("Invalid Block Configuration File!");
      return -1;
    }
  }

  /**
   * Returns the block width for a given level
   * @param level the level to be used
   * @return the width of a block for the level
   */
  private double getBlockWidth(int level) {
    return (GameStatus.WINDOWWIDTH - (getColNum(level) + 1) * GameStatus.GAP) / (double) getColNum(
        level);
  }

  /**
   * Returns the block height for a given level
   * @param level the level to be used
   * @return the height of a block for the level
   */
  private double getBlockHeight(int level) {
    return ((double) GameStatus.WINDOWHEIGHT / 2.5 - (getRowNum(level) + 1) * GameStatus.GAP)
        / (double) getRowNum(level);
  }

  /**
   * Returns the total number of levels in the game
   * @return the number of levels
   */
  public int getFileCount() {
    File folder = new File(directory);
    return Objects.requireNonNull(folder.list()).length;
  }
}

