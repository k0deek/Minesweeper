package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class GameView {
    public int WIDTH = 457;
    public int HEIGHT = 457;
    public static int X_TILES;
    public static int Y_TILES;
    private boolean isFirstClick = true;
    public boolean isEnd = false;
    public boolean isWin = false;
    public static int num_bomb;
    public GameController gameController;
    public int score = 1000;
    public Stage gameStage;
    public static Tile [][] grid;
    private final int[] timeArr = {0};
    private Timeline timeline;

    public static int countOpened = 0;
    public static int countTiles = 0;
    public static int countMarkedBombs = 0;

    Integer lengthField = 0;


    public Parent initialize(int num_x, int num_y, int num_bomb) throws IOException {
        GameController.isWindowOpened = true;
        GameView.num_bomb = num_bomb;
        isEnd = false;
        isFirstClick = true;
        X_TILES = num_x;
        Y_TILES = num_y;
        countMarkedBombs = 0;
        countOpened = 0;
        countTiles = 0;
        timeArr[0] = 0;
        countTiles = X_TILES * Y_TILES;
        Tile.TILE_SIZE = WIDTH / num_x;
        Tile.TILE_SIZE = WIDTH / Y_TILES;

        grid = new Tile[X_TILES][Y_TILES];

        FXMLLoader loader = new FXMLLoader();
        Parent parent = loader.load(getClass().getResource("game.fxml").openStream());
        gameController = loader.getController();
        Pane root = gameController.gameField;

        for (int y = 0; y < Y_TILES; y++){
            for (int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x, y, false);
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        Label time = gameController.timeField;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000), //1000 мс = 1 c
                        ae -> {
                            timeArr[0]++;
                            time.setText(String.valueOf(timeArr[0]));
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        time.setText("0");

        timeline.play(); //Запускаем

        return parent;
    }

    public void initialize(int howmuch, int num_bomb) throws IOException {
        GameView.num_bomb = num_bomb;
        isEnd = false;
        isFirstClick = true;
        lengthField = howmuch;
        X_TILES = lengthField;
        Y_TILES = lengthField;
        countMarkedBombs = 0;
        countOpened = 0;
        countTiles = 0;
        countTiles = X_TILES * Y_TILES;
        Tile.TILE_SIZE = WIDTH / X_TILES;
        Tile.TILE_SIZE = WIDTH / Y_TILES;
        grid = new Tile[X_TILES][Y_TILES];

        for (int y = 0; y < Y_TILES; y++){
            for (int x = 0; x < X_TILES; x++){
                Tile tile = new Tile(x, y, false);
                grid[x][y] = tile;
            }
        }
    }

    public void gameOver(int tilex, int tiley){
        timeline.stop();
        score = 100 * X_TILES * Y_TILES - timeArr[0] * 50;
        Tile.getBombInfo(tilex, tiley);
    }

    public static void showGameField(int lengthField) {
        for (int i = 0; i < lengthField; ++i) {
            for (int j = 0; j < lengthField; ++j) {
                if (Tile.getIsMarked(i,j))
                    System.out.print("f");
                else if (!Tile.getIsOpened(i,j))
                    System.out.print("#");
                else if (Tile.getCountBomb(i,j) >= 0 && Tile.getCountBomb(i,j) <= 9)
                    System.out.print(Tile.getCountBomb(i,j));
            }
            System.out.println("\n");
        }
    }

    public Integer getLengthField() {
        return lengthField;
    }

    public Integer getMinesCount() {
        return num_bomb;
    }

}
