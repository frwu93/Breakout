package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The Game class is responsible for the functioning of the game and facilitates interactions between
 * all components of the game. The Game class handles key inputs, updates the positions of the game
 * components, checks for collisions, and checks whether a level has been beat or if the user has lost.
 */

public class Game {

  private final GameLauncher gameLauncher;
  private final Ball ball;
  private final Paddle paddle;
  private final ScoreDisplay scoreDisplay;
  private final LivesDisplay livesDisplay;
  private final LevelDisplay levelDisplay;
  private final HighScoreDisplay highScoreDisplay;
  // warning indicates it is never updated, but it is updated through an iterator
  private final List<PowerUp> activePowerUps; // contains power ups that are falling or have been activated
  private final List<PowerUp> fallingPowerUps; // contains power ups that are falling
  private GamePiece[][] gridOfGamePieces;
  private int level;
  private boolean pause;

  /**
   * Creates an instance of the Game class.
   * @param gameLauncher the GameLauncher used to launch the game
   * @param livesDisplay the lives display
   * @param scoreDisplay the score display
   * @param levelDisplay the level display
   * @param highScoreDisplay the high score display
   * @param ball the ball in the game
   * @param paddle the paddle in the game
   * @param gridOfGamePieces the block configuration as a 2D array of GamePieces
   */
  public Game(GameLauncher gameLauncher, LivesDisplay livesDisplay, ScoreDisplay scoreDisplay,
      LevelDisplay levelDisplay, HighScoreDisplay highScoreDisplay, Ball ball,
      Paddle paddle, GamePiece[][] gridOfGamePieces) {
    this.gameLauncher = gameLauncher;
    this.livesDisplay = livesDisplay;
    this.scoreDisplay = scoreDisplay;
    this.levelDisplay = levelDisplay;
    this.highScoreDisplay = highScoreDisplay;
    this.ball = ball;
    this.paddle = paddle;
    this.gridOfGamePieces = gridOfGamePieces;
    this.level = GameStatus.FIRST_LEVEL;
    this.fallingPowerUps = new ArrayList<>();
    this.activePowerUps = new ArrayList<>();
    this.pause = false;
  }

  /**
   * Launches the ball based on the mouse input
   * @param x the x-coordinate of the mouse click
   */
  public void handleMouseInput(double x) {
    if (ball.getXVelocity() == 0 && ball.getYVelocity() == 0) {
      ball.setXVelocity(x - GameStatus.WINDOWWIDTH / 2.0);
      ball.setYVelocity(-150);
    }
  }

  /**
   * Handles key inputs, including left and right keys and cheat key inputs
   * @param code the KeyCode of the key pressed
   */
  public void handleKeyInput(KeyCode code) {
    cheatKeys(code);
    switch (code) {
      case LEFT -> handleLeftPress();
      case RIGHT -> handleRightPress();
    }
  }

  /**
   * The step method progresses the game based on the elapsed time and updates the position of
   * the game components
   * @param elapsedTime
   */
  public void step(double elapsedTime) {
    if (!pause) {
      updateShapes(elapsedTime);
      updatePowerUps(elapsedTime);
    }
    checkGameStatus();
  }

  /**
   * Resets the current level, which regenerates the original block configuration, resets the ball
   * and paddle position, and resets the score display.
   */
  public void resetLevel() {
    resetBallPaddle();
    clearLevel();
    gridOfGamePieces = gameLauncher.setUpLevel(level);
    scoreDisplay.resetDisplayValue();
  }

  /**
   * Resets the ball and paddle position to their default position
   */
  public void resetBallPaddle() {
    ball.reset();
    paddle.reset();
  }

  /**
   * Clears the level, including the block configuration and any text pop-ups
   */
  public void clearLevel() {
    for (GamePiece[] rowOfGamePieces : gridOfGamePieces) {
      for (GamePiece gamePiece : rowOfGamePieces) {
        gamePiece.setLives(0);
        gameLauncher.removeFromRoot(gamePiece);
      }
    }
    gameLauncher.removeFromRoot("#winMessage");
    gameLauncher.removeFromRoot("#winGameMessage");
    gameLauncher.removeFromRoot("#lossMessage");
  }

  /**
   * Pauses the game
   */
  public void setPause() {
    pause = !pause;
  }

  /**
   * Changes the paddle width by a given factor
   * @param factor the factor to change the paddle width by
   */
  public void scalePaddleSize(double factor) {
    paddle.setWidth(paddle.getWidth() * factor);
  }

  /**
   * Adds a life to the lives display
   */
  public void addLife() {
    livesDisplay.addLife();
  }

  /**
   * Changes the ball velocity by a given factor
   * @param factor the factor to change the ball velocity by
   */
  public void scaleBallVelocity(double factor) {
    ball.setXVelocity(ball.getXVelocity() * factor);
    ball.setYVelocity(ball.getYVelocity() * factor);
  }

  /**
   * Updates the game components' position based on the elapsed time
   * @param elapsedTime the time elapsed
   */
  private void updateShapes(double elapsedTime) {
    checkPaddleCollision();
    checkBallGamePieceCollision();
    checkBorderBallCollision();
    checkBorderBlockCollision();
    updateBlockPositions(elapsedTime);
    updatePowerUps(elapsedTime);
    ball.updatePosition(elapsedTime);
  }

  /**
   * Updates the block positions based on the elapsed time
   * @param elapsedTime the time elapsed
   */
  private void updateBlockPositions(double elapsedTime) {
    for (GamePiece[] rowOfGamePieces : gridOfGamePieces) {
      for (GamePiece gamePiece : rowOfGamePieces) {
        gamePiece.updatePosition(elapsedTime);
      }
    }
  }

  /**
   * Handles whether or not the ball and/or falling power-up(s) have collided with the paddle
   */
  private void checkPaddleCollision() {
    if (isIntersectingWithBall(paddle)) {
      ball.updateVelocityUponCollision(paddle);
    }
    for (PowerUp fallingPowerUp : fallingPowerUps) {
      if (fallingPowerUp != null && isIntersectingWithPaddle(fallingPowerUp)) {
        fallingPowerUp.updateGameStatus(this);
        updateGamePieceStatus(fallingPowerUp);
      }
    }
  }

  /**
   * Handles when the ball collides into a GamePiece. If the ball collides with a falling power-up,
   * it is ignored. Otherwise, the ball will bounce off of the GamePiece and the score displays are updated
   * accordingly.
   */
  private void checkBallGamePieceCollision() {
    for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
      for (GamePiece gamePiece : gridOfGamePiece) {
        if (gamePiece instanceof PowerUp && fallingPowerUps.contains(gamePiece)) {
          continue; // avoid collision of ball with falling power up
        }
        if (gamePiece.getLives() > 0 && isIntersectingWithBall(gamePiece)) {
          ball.updateVelocityUponCollision(gamePiece);
          scoreDisplay.increaseScore();
          highScoreDisplay.updateHighScore(scoreDisplay.getScore());
          updateGamePieceStatus(gamePiece);
        }
      }
    }
  }

  /**
   * Updates the GamePiece status after it is hit. In the event a block is broken, there is a change that
   * a powerup may be generated in its place.
   * @param gamePiece the GamePiece to be operated on
   */
  private void updateGamePieceStatus(GamePiece gamePiece) {
    gamePiece.updateStatus();
    if (gamePiece instanceof PowerUp && !((PowerUp) gamePiece).isFalling()) {
      gridOfGamePieces[gamePiece.getRowPosition()][gamePiece.getColPosition()] =
          new Block(0, 0, 1, 1, 0);
      PowerUp powerUp = (PowerUp) gamePiece;
      powerUp.beginFalling();
      fallingPowerUps.add(powerUp); // put power up in to begin falling
    }
    if (gamePiece.getLives() <= 0) {
      gameLauncher.removeFromRoot(gamePiece);
      if (gamePiece instanceof Block) {
        generatePowerUp((Block) gamePiece, gamePiece.getRowPosition(), gamePiece.getColPosition());
      }
    }
  }

  /**
   * Generates a power-up in the position of a destroyed block
   * @param deletedBlock the recently-destroyed block
   * @param i the row of the destroyed block
   * @param j the column of the destroyed block
   */
  private void generatePowerUp(Block deletedBlock, int i, int j) {
    if (Math.random() <= GameStatus.POWER_UP_PROBABILITY) {
      PowerUp powerUp = makePowerUp(deletedBlock.getX() + deletedBlock.getWidth() / 4,
          deletedBlock.getY(),
          deletedBlock.getWidth() / 2, deletedBlock.getHeight());
      if (powerUp != null) {
        powerUp.getAttributesFromBlock(deletedBlock);
        gameLauncher.addToRoot(powerUp);
        gridOfGamePieces[i][j] = powerUp;
      }
    }
  }

  /**
   * Generates one of three powerups at random
   * @param x x-coordinate of the power-up gamepiece
   * @param y y-coordinate of the power-up gamepiece
   * @param width the width of the power-up gamepiece
   * @param height the height of the power-up gamepiece
   * @return the power-up generated
   */
  private PowerUp makePowerUp(double x, double y, double width, double height) {
    Random random = new Random();
    try {
      Class<? extends PowerUp> powerUpClass = GameStatus.POWERUPS
          .get(random.nextInt(GameStatus.POWERUPS.size()));
      Constructor<? extends PowerUp> powerUpConstructor =
          powerUpClass.getConstructor(double.class, double.class, double.class, double.class);
      return powerUpConstructor
          .newInstance(x, y, width, height); // half size of block and move it to center
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Turns the first block into a random power-up
   */
  private void makePowerUpFromFirstBlock() {
    Block replaceBlock = getFirstBlock();
    if (replaceBlock != null) {
      int row = replaceBlock.getRowPosition();
      int col = replaceBlock.getColPosition();
      PowerUp powerUp = makePowerUp(replaceBlock.getX() + replaceBlock.getWidth() / 4,
          replaceBlock.getY(),
          replaceBlock.getWidth() / 2, replaceBlock.getHeight());
      if (powerUp != null) {
        powerUp.getAttributesFromBlock(replaceBlock);
        gameLauncher.addToRoot(powerUp);
        gameLauncher.removeFromRoot(replaceBlock);
        gridOfGamePieces[row][col] = powerUp;
      }
    }
  }

  /**
   * Checks if the ball collides into the window borders and updates the game accordingly
   */
  private void checkBorderBallCollision() {
    if (ball.getLeft() <= 0 || ball.getRight() >= GameStatus.WINDOWWIDTH) {
      ball.updateXVelocityUponBorderCollision();
    } else if (ball.getTop() <= GameStatus.DISPLAYHEIGHT) {
      ball.updateYVelocityUponBorderCollision();
    } else if (ball.getTop() > GameStatus.WINDOWHEIGHT) { // goes below the screen
      resetBallPaddle();
      livesDisplay.subtractLife();
      scoreDisplay.resetBonus();
    }
  }

  /**
   * Checks if the blocks collide into the window border and updates the game acccordingly
   */
  private void checkBorderBlockCollision() {
    checkHorizontalBorderBlockCollision();
    checkVerticalBorderBlockCollision();
  }

  /**
   * Checks if the blocks hits the top or bottom of the block movement range, and if so, changes
   * the y-velocities of the blocks
   */
  private void checkVerticalBorderBlockCollision() {
    for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
      for (int j = 0; j < gridOfGamePieces[0].length; j++) {
        GamePiece block = gridOfGamePiece[j];
        if (block.getLives() > 0 && (block.getLeft() <= 0
            || block.getRight() >= GameStatus.WINDOWWIDTH)) {
          changeXVelRow(block);
          break;
        }
      }
    }
  }

  /**
   * Checks if the blocks hits the left or right of the block movement range, and if so, changes
   * the x-velocities of the blocks.
   */
  private void checkHorizontalBorderBlockCollision() {
    for (int i = 0; i < gridOfGamePieces[0].length; i++) {
      for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
        GamePiece columnBlock = gridOfGamePiece[i];
        if (columnBlock.getLives() > 0 && (columnBlock.getTop() <= GameStatus.DISPLAYHEIGHT ||
            columnBlock.getBottom() >= 300)) {
          changeYVelCol(columnBlock);
          break;
        }
      }
    }
  }

  /**
   * Inverses all the x-velocities in a given block's row
   * @param block the block to be used
   */
  private void changeXVelRow(GamePiece block) {
    int row = block.getRowPosition();
    for (int i = 0; i < gridOfGamePieces[row].length; i++) {
      GamePiece change = gridOfGamePieces[row][i];
      change.updateXVelocityUponCollision();
    }
  }

  /**
   * Inverses all the y-velocities in a given block's column
   * @param block the block to be used
   */
  private void changeYVelCol(GamePiece block) {
    int col = block.getColPosition();
    for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
      GamePiece change = gridOfGamePiece[col];
      if (change instanceof PowerUp && ((PowerUp) change).isFalling()) {
        return;
      }
      change.updateYVelocityUponCollision();

    }
  }

  /**
   * Returns true if the ball is intersecting with the game piece
   * @param gamePiece the game piece to be used
   * @return true if the ball is intersecting with the game piece
   */
  private boolean isIntersectingWithBall(Rectangle gamePiece) {
    return gamePiece.getBoundsInParent().intersects(ball.getBoundsInParent());
  }

  /**
   * Returns true if the game piece is intersecting with the paddle
   * @param gamePiece the game piece to be used
   * @return true if the game piece is intersecting with the paddle
   */
  private boolean isIntersectingWithPaddle(Rectangle gamePiece) {
    return gamePiece.getBoundsInParent().intersects(paddle.getBoundsInParent());
  }

  /**
   * Updates the power-up positions based on the elapsed time
   * @param elapsedTime the time elapsed
   */
  private void updatePowerUps(double elapsedTime) {
    updateActivePowerUps();
    updateFallingPowerUps(elapsedTime);
  }

  /**
   * Updates the state of the current active power-ups
   */
  private void updateActivePowerUps() {
    Iterator<PowerUp> iterator = activePowerUps.iterator();
    while (iterator
        .hasNext()) { // iterator has to be used instead in order to avoid Concurrent Modification errors
      PowerUp activePowerUp = iterator.next();
      if (activePowerUp.isActive()) {
        activePowerUp.decrementTimer();
        if (activePowerUp.hasTimerEnded()) {
          activePowerUp.resetGameStatus(this);
          iterator.remove(); // remove power up from active power up collection
        }
      }
    }
  }

  /**
   * Updates the position of the current falling power-ups based on the elapsed time
   * @param elapsedTime the time elpased
   */
  private void updateFallingPowerUps(double elapsedTime) {
    for (PowerUp fallingPowerUp : fallingPowerUps) { // iterator has to be used instead in order to avoid Concurrent Modification errors
      fallingPowerUp.fall(elapsedTime);
    }
  }

  /**
   * Checks if a level is passed, the game is won, or the game is lost
   */
  private void checkGameStatus() {
    Text gameMessage = new Text(200, 300, "");
    if (hasWon()) {
      gameMessage.setText("You Passed This Level!");
      gameMessage.setId("winMessage");
      loadNextLevel();
    } else if (hasLost()) {
      gameMessage.setText("You Ran Out Of Lives! You lost!");
      gameMessage.setId("lossMessage");
      pause = true;
    }
    gameLauncher.addToRoot(gameMessage);
  }

  /**
   * Returns true if the level has been cleared
   * @return true if the level has been cleared
   */
  private boolean hasWon() {
    for (GamePiece[] rowOfGamePieces : gridOfGamePieces) {
      for (GamePiece gamePiece : rowOfGamePieces) {
        if (gamePiece.getLives() != 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns true if the number of remaining lives is equal to 0
   * @return true if the number of remaining lives is equal to 0
   */
  private boolean hasLost() {
    return livesDisplay.getLives() == 0;
  }

  /**
   * Handles the left key press to move the paddle left
   */
  private void handleLeftPress() {
    if (!pause && paddle.getX() >= GameStatus.PADDLEDELTA) {
      paddle.setX(paddle.getX() - GameStatus.PADDLEDELTA);
      if (ball.notInMotion()) {
        ball.setCenterX(ball.getCenterX() - GameStatus.PADDLEDELTA);
      }
    }
  }

  /**
   * Handles the right key press to move the paddle right
   */
  private void handleRightPress() {
    if (!pause
        && paddle.getX() + paddle.getWidth() <= GameStatus.WINDOWWIDTH - GameStatus.PADDLEDELTA) {
      paddle.setX(paddle.getX() + GameStatus.PADDLEDELTA);
      if (ball.notInMotion()) {
        ball.setCenterX(ball.getCenterX() + GameStatus.PADDLEDELTA);
      }
    }
  }

  /**
   * Handles cheatkeys
   * @param code the KeyCode of the cheatkey
   */
  private void cheatKeys(KeyCode code) {
    switch (code) {
      case R -> resetLevel();
      case SPACE -> setPause();
      case L -> addLife();
      case P -> makePowerUpFromFirstBlock();
      case C -> clearLevel();
      case D -> clearFirstBlock();
      case S -> highScoreDisplay.clearHighScore();
      case F -> allBlocksLoseLife();
      case G -> clearFirstRowBlock();
      case DIGIT1 -> jumpToLevel(1);
      case DIGIT2 -> jumpToLevel(2);
      case DIGIT3 -> jumpToLevel(3);
      case DIGIT4 -> jumpToLevel(4);
      case DIGIT5 -> jumpToLevel(5);
    }
  }

  /**
   * Clears the bottom-most row of blocks
   */
  private void clearFirstRowBlock() {
    GamePiece[] rowToRemove = getFirstRowBlock();
    if (rowToRemove != null) {
      for (GamePiece block : rowToRemove) {
        block.setLives(0);
        gameLauncher.removeFromRoot(block);
      }
    }
  }

  /**
   * Clears the left-most remaining block on the bottom-most remaining row.
   */
  private void clearFirstBlock() {
    Block blockToRemove = getFirstBlock();
    if (blockToRemove != null) {
      blockToRemove.setLives(0);
      gameLauncher.removeFromRoot(blockToRemove);
    }
  }

  /**
   * Subtracts one life from all remaining blocks on the screen
   */
  private void allBlocksLoseLife() {
    for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
      for (int j = 0; j < gridOfGamePieces[0].length; j++) {
        if (gridOfGamePiece[j].getLives() > 0) {
          updateGamePieceStatus(gridOfGamePiece[j]);
        }
      }
    }
  }

  /**
   * Jumps to a given level
   * @param level the level to jump to
   */
  private void jumpToLevel(int level) {
    clearLevel();
    resetBallPaddle();
    this.level = level;
    gridOfGamePieces = gameLauncher.setUpLevel(level);
    levelDisplay.setLevel(level);
  }

  /**
   * Returns the left-most block on the bottom-most row
   * @return the first block
   */
  private Block getFirstBlock() {
    for (int i = gridOfGamePieces.length - 1; i >= 0; i--) {
      for (int j = 0; j < gridOfGamePieces[0].length; j++) {
        if (gridOfGamePieces[i][j].getLives() > 0 && gridOfGamePieces[i][j] instanceof Block) {
          return (Block) gridOfGamePieces[i][j];
        }
      }
    }
    return null;
  }

  /**
   * Gets the bottom-most row of blocks that still contains remaining blocks
   * @return a row of game pieces corresponding with the bottom-most row of blocks
   */
  private GamePiece[] getFirstRowBlock() {
    for (int i = gridOfGamePieces.length - 1; i >= 0; i--) {
      for (int j = 0; j < gridOfGamePieces[0].length; j++) {
        if (gridOfGamePieces[i][j].getLives() > 0) {
          return gridOfGamePieces[i];
        }
      }
    }
    return null;
  }

  /**
   * Loads the next level after the current level has been cleared
   */
  private void loadNextLevel() {
    BlockConfigurationReader blockReader = new BlockConfigurationReader();
    int maxLevel = blockReader.getFileCount();
    if (level >= maxLevel) {
      Text gameMessage = new Text(150, 300, "WOWOWOW!!! YOU BEAT THE WHOLE GAME");
      gameMessage.setId("winGameMessage");
      gameLauncher.addToRoot(gameMessage);
    } else {
      clearLevel();
      level += 1;
      gridOfGamePieces = gameLauncher.setUpLevel(level);
      scoreDisplay.setCheckPointScore();
      levelDisplay.incrementLevel();
    }
    resetBallPaddle();
  }
}
