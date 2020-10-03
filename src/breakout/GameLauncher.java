package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLauncher extends Application {

  private Group root;
  private Game game;
  private ScoreDisplay scoreDisplay;
  private LivesDisplay livesDisplay;
  private LevelDisplay levelDisplay;
  private HighScoreDisplay highScoreDisplay;

  /**
   * Sets up the window application
   * @param primaryStage window application that displays game
   */
  @Override
  public void start(Stage primaryStage) {
    // attach scene to the stage and display it
    Scene myScene = setupScene();
    primaryStage.setScene(myScene);
    primaryStage.setTitle(GameStatus.TITLE);
    primaryStage.setResizable(false);
    primaryStage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    KeyFrame frame = new KeyFrame(Duration.seconds(GameStatus.SECOND_DELAY),
        e -> game.step(GameStatus.SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Sets up the scene that will be passed onto the stage
   * @return Scene that contains all the components of the game
   */
  protected Scene setupScene() {
    root = new Group();
    Ball ball = new Ball(GameStatus.WINDOWWIDTH / 2,
        GameStatus.WINDOWHEIGHT - GameStatus.RADIUS - (int) GameStatus.PADDLEHEIGHT - 1,
        GameStatus.RADIUS, Color.web("#ff7f50"));

    root.getChildren().add(ball);
    Paddle paddle = new Paddle(GameStatus.WINDOWWIDTH / 2.0 - GameStatus.PADDLEWIDTH / 2,
        GameStatus.WINDOWHEIGHT - GameStatus.PADDLEHEIGHT,
        GameStatus.PADDLEWIDTH, GameStatus.PADDLEHEIGHT, Color.web("#6897bb"));
    root.getChildren().add(paddle);
    setUpDisplayBar();
    setUpLivesDisplay();
    setUpScoreDisplay();
    setUpLevelDisplay();
    setUpHighScoreDisplay();
    GamePiece[][] gridOfGamePieces = setUpLevel(GameStatus.FIRST_LEVEL);
    game = new Game(this, livesDisplay, scoreDisplay,
        levelDisplay, highScoreDisplay, ball, paddle, gridOfGamePieces);
    Scene scene = new Scene(root, GameStatus.WINDOWWIDTH, GameStatus.WINDOWHEIGHT,
        GameStatus.BACKGROUND);
    scene.setOnKeyPressed(e -> game.handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> game.handleMouseInput(e.getX()));
    return scene;
  }

  /**
   * Returns a 2D array of game pieces based on the level
   * @param level integer representing the level number
   * @return a 2D array of game pieces that represents all the game pieces on the screen
   */
  public GamePiece[][] setUpLevel(int level) {
    BlockConfigurationReader levelReader = new BlockConfigurationReader();
    return levelReader.loadLevel(root, level);
  }

  private void setUpDisplayBar() {
    Rectangle display = new Rectangle(GameStatus.WINDOWWIDTH, GameStatus.DISPLAYHEIGHT);
    display.setFill(Color.LIGHTGREY);
    root.getChildren().add(display);
  }

  private void setUpLivesDisplay() {
    livesDisplay = new LivesDisplay();
    root.getChildren().add(livesDisplay);
  }

  private void setUpScoreDisplay() {
    scoreDisplay = new ScoreDisplay();
    root.getChildren().add(scoreDisplay);
  }

  private void setUpLevelDisplay() {
    levelDisplay = new LevelDisplay();
    root.getChildren().add(levelDisplay);
  }

  private void setUpHighScoreDisplay() {
    HighScoreReader highScoreReader = new HighScoreReader();
    int highScore = highScoreReader.readInHighScore();
    highScoreDisplay = new HighScoreDisplay(highScore);
    root.getChildren().add(highScoreDisplay);
  }

  /**
   * Adds Node to the root
   * @param element Node that is to be added to the root
   */
  public void addToRoot(Node element) {
    root.getChildren().add(element);
  }

  /**
   * Removes Node from the root
   * @param element Node that is to be removed from the root
   */
  public void removeFromRoot(Node element) {
    root.getChildren().remove(element);
  }

  /**
   * Removes Node from root based on ID
   * @param id String representing ID of the Node to be removed
   */
  public void removeFromRoot(String id) {
    root.getChildren().remove(root.lookup(id));
  }

  // following methods for testing purposes

  /**
   * Testing: get the Game
   * @return Game
   */
  public Game getGame() {
    return game;
  }

  /**
   * Testing: get the Score Display
   * @return ScoreDisplay
   */
  public ScoreDisplay getScoreDisplay() {
    return scoreDisplay;
  }

  /**
   * Testing: get the Lives Display
   * @return LivesDisplay
   */
  public LivesDisplay getLivesDisplay() {
    return livesDisplay;
  }

  /**
   * Testing: get the Level Display
   * @return LevelDisplay
   */
  public LevelDisplay getLevelDisplay() {
    return levelDisplay;
  }

  /**
   * Testing: get the High Score Display
   * @return HighScoreDisplay
   */
  public HighScoreDisplay getHighScoreDisplay() {
    return highScoreDisplay;
  }

  public static void main(String[] args) {
    launch(args);
  }

}
