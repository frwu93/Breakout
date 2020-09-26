package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLauncher extends Application {
    private Group root;
    private Game game;

    @Override
    public void start(Stage primaryStage) {
        // attach scene to the stage and display it
        Scene myScene = setupScene(GameStatus.WINDOWWIDTH, GameStatus.WINDOWHEIGHT, GameStatus.BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(GameStatus.TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(GameStatus.SECOND_DELAY), e -> game.step(GameStatus.SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public Scene setupScene(int width, int height, Paint background) {
        root = new Group();

        Ball ball = new Ball(width / 2,
                height - GameStatus.RADIUS - (int)GameStatus.PADDLEHEIGHT - 1, GameStatus.RADIUS, Color.ORANGE);
        root.getChildren().add(ball);

        Paddle paddle = new Paddle(width/2.0 - GameStatus.PADDLEWIDTH/2, height - GameStatus.PADDLEHEIGHT,
                GameStatus.PADDLEWIDTH, GameStatus.PADDLEHEIGHT, GameStatus.PADDLEDELTA, Color.RED); //TODO: Clean this
        root.getChildren().add(paddle);

        BlockConfigurationReader levelReader = new BlockConfigurationReader();
        Block[][] gridOfBlocks = levelReader.loadLevel(root, 1);

        game = new Game(this, ball, paddle, gridOfBlocks);
        setUpDisplay();

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> game.getInputReader().handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> game.getInputReader().handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void setUpDisplay() {
        Rectangle display = new Rectangle(GameStatus.WINDOWWIDTH, GameStatus.DISPLAYHEIGHT);
        display.setFill(Color.LIGHTGREY);
        root.getChildren().add(display);

        LivesDisplay livesDisplay = new LivesDisplay(GameStatus.LIVES, GameStatus.LIVES_DISPLAY_XPOS, GameStatus.LIVES_DISPLAY_YPOS);
        root.getChildren().add(livesDisplay);
        game.addLivesDisplay(livesDisplay);

        ScoreDisplay scoreDisplay = new ScoreDisplay(GameStatus.SCORE_DISPLAY_XPOS, GameStatus.SCORE_DISPLAY_YPOS);
        root.getChildren().add(scoreDisplay);
        game.addScoreDisplay(scoreDisplay);
    }

    public void addToRoot(Node element) {
        root.getChildren().add(element);
    }

    public void removeFromRoot(Node element) {
        root.getChildren().remove(element);
    }

    public static void main (String[] args) {
        launch(args);
    }
}