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


public class GameView {
    public int WIDTH = 457;
    public int HEIGHT = 457;
    public int X_TILES;
    public int Y_TILES;
    private boolean isFirstClick = true;
    public boolean isEnd = false;
    public boolean isWin = false;
    public static int num_bomb;
    public GameController gameController;
    public int score = 1000;

    public Stage gameStage;
    public static Tile [][] grid;

    private int[] timeArr = {0};
    private Timeline timeline;

    public static int countOpened = 0;
    public static int countTiles = 0;
    public static int countMarkedBombs = 0;


    public Parent initialize(int num_x, int num_y, int num_bomb) throws IOException {
        this.num_bomb = num_bomb;
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

    private void plantBombs(int tilex, int tiley){
        int countBombs = 0;
        while(countBombs < num_bomb){
            int x = (int) (Math.random() * X_TILES);
            int y = (int) (Math.random() * Y_TILES);
            if (!grid[x][y].hasBomb && !(x == tilex && y == tiley)){
                grid[x][y].hasBomb = true;
                grid[x][y].text.setText("X");
                countBombs++;
            }
        }

        for (int y = 0; y < Y_TILES; y++){
            for (int x = 0; x < X_TILES; x++){
                Tile tile = grid[x][y];

                if (tile.hasBomb) continue;

                tile.neighbors = getNeighbors(tile);
                long bombs = tile.neighbors.stream().filter(t -> t.hasBomb).count();
                tile.text.setText(bombs == 0?  "" : String.valueOf(bombs));
                if (bombs == 0) tile.isEmpty = true;
                else tile.isEmpty = false;
                tile.countBomb = (int)bombs;

            }
        }
    }

    private List<Tile> getNeighbors(Tile tile){
        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[] {
           -1, -1,
           -1, 0,
           -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1
        };
        for (int i = 0; i < points.length; i++){
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES){
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }


    public void openTiles(Tile tile) throws IOException {
        if (isFirstClick) {
            isFirstClick = false;
            plantBombs(tile.x, tile.y);
        }

        tile.open();
        if (!tile.isEmpty) return;

        openNeighbors(tile);
        if (X_TILES * Y_TILES - countOpened == num_bomb){
            isWin = true;
            isEnd = true;
            gameController.winGame();
        }

    }

    public void openNeighbors(Tile tile) throws IOException {
        int [] points = {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++){
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES){
                if (!grid[newX][newY].hasBomb && !grid[newX][newY].isOpened) {
                    grid[newX][newY].open();
                    if (X_TILES * Y_TILES - countOpened == num_bomb){
                        isWin = true;
                        isEnd = true;
                        gameController.winGame();
                    }
                    if (grid[newX][newY].isEmpty) openNeighbors(grid[newX][newY]);
                }

            }
        }
    }

    public void gameOver(int tilex, int tiley){
        timeline.stop();
        score = 100 * X_TILES * Y_TILES - timeArr[0] * 50;
        isEnd = true;

        if (!isWin) {
            for (int y = 0; y < Y_TILES; y++){
                for (int x = 0; x < X_TILES; x++){
                    if (grid[x][y].hasBomb) grid[x][y].showBomb();
                }
            }
            grid[tilex][tiley].bomb();
        }
        else {
            for (int y = 0; y < Y_TILES; y++){
                for (int x = 0; x < X_TILES; x++){
                    if (grid[x][y].hasBomb) grid[x][y].showBomb();
                }
            }
        }
    }
}
