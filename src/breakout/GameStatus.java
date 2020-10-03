package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The GameStatus class contains all static constants needed to set up and run the game.
 */

public final class GameStatus {

  public static final int WINDOWHEIGHT = 600;
  public static final int WINDOWWIDTH = 500;
  public static final Paint BACKGROUND = Color.web("#f0f8ff");
  public static final String TITLE = "Breakout";
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final int RADIUS = 10;
  public static final double PADDLEWIDTH = 100;
  public static final double PADDLEHEIGHT = 15;
  public static final int GAP = RADIUS;
  public static final double PADDLEDELTA = 20;
  public static final int LIVES_DISPLAY_XPOS = 10;
  public static final int DISPLAY_YPOS = 14;
  public static final int LEVEL_DISPLAY_XPOS = 125;
  public static final int HIGH_SCORE_DISPLAY_XPOS = 290;
  public static final double DISPLAYHEIGHT = 18;
  public static final int SCORE_DISPLAY_XPOS = WINDOWWIDTH - 65;
  public static final int FIRST_LEVEL = 1;
  public static final double POWER_UP_PROBABILITY = 0.2;
  public static final List<Class<? extends PowerUp>> POWERUPS = new ArrayList<>(
      Arrays.asList(
          ExpandPaddlePowerUp.class,
          AddLifePowerUp.class,
          SlowDownPowerUp.class)
  );
  public static final String HEART_FILE_PATH = "data/images/heart.jpg";
  public static final String EXPAND_PADDLE_FILE_PATH = "data/images/expandpaddle.png";
  public static final String SLOW_DOWN_FILE_PATH = "data/images/snail.jpg";
  public static final int POWER_UP_TIMER = 10 * FRAMES_PER_SECOND;
  public static final int START_SCORE = 0;
  public static final int STARTING_LIVES = 3;
  public static final double PADDLE_INCREASE_FACTOR = 1.5;
  public static final double SLOW_DOWN_FACTOR = 0.5;
}
