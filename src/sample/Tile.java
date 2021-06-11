package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static sample.GameController.gameView;
import static sample.GameView.*;


public class Tile extends StackPane {
    public int x;
    public int y;
    public boolean hasBomb;
    public boolean isOpened;
    public boolean isMarked;
    public boolean isEmpty;
    public List<Tile> neighbors;
    public int countBomb;
    public static int TILE_SIZE = 50;
    public static boolean isEnd = false;
    public static boolean isWin = false;
    private static boolean isFirstClick = true;
    private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
    public Text text = new Text();
    public static GameView gameView = new GameView();

    public Tile(int x, int y, boolean hasBomb){
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;
        border.setStroke(Color.WHITE);
        border.setFill(Color.GRAY);


        text.setText(hasBomb? "X":"");
        text.setFill(Color.GRAY);

        getChildren().addAll(border, text);

        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);
    }

    public void open(Tile thisTile) throws IOException {
        if (thisTile.hasBomb && !thisTile.isMarked) isEnd = true;
        else if (!thisTile.isOpened && !thisTile.isMarked) openTiles(thisTile);
    }

    public void flag(Tile thisTile) throws IOException {
        if (thisTile.isOpened) return;
        if (!thisTile.isMarked){
            thisTile.isMarked = true;
            thisTile.mark();
        }
        else {
            thisTile.isMarked = false;
            thisTile.blank();
        }
    }

    private void open(){
        Color[] colors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.DARKGREEN,
        Color.MAGENTA, Color.RED, Color.BROWN, Color.DARKGOLDENROD, Color.OLIVE};
        this.isOpened = true;
        countOpened++;
        border.setFill(Color.WHITE);
        text.setFill(colors[countBomb]);
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
                tile.isEmpty = bombs == 0;
                tile.countBomb = (int)bombs;

            }
        }
    }
    private void openTiles(Tile tile) throws IOException {
        if (isFirstClick) {
            isFirstClick = false;
            tile.plantBombs(tile.x, tile.y);
        }

        tile.open();
        if (!tile.isEmpty) return;

        openNeighbors(tile);
        if (X_TILES * Y_TILES - countOpened == num_bomb){
            isWin = true;
            isEnd = true;
            Tile.setTrueIsFirstClick();
        }

    }

    private void openNeighbors(Tile tile) throws IOException {
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
                        Tile.setTrueIsFirstClick();
                    }
                    if (grid[newX][newY].isEmpty) openNeighbors(grid[newX][newY]);
                }

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

    private void mark() throws IOException {
        text.setFill(Color.DARKOLIVEGREEN);
        border.setFill(Color.DARKOLIVEGREEN);

        if (hasBomb) countMarkedBombs++;
        if (countMarkedBombs == num_bomb){
            gameView.isWin = true;
            gameView.isEnd = true;
            if(GameController.isWindowOpened)
                gameView.gameController.winGame();
        }
    }

    private void blank(){
        text.setFill(Color.GRAY);
        border.setFill(Color.GRAY);

        if (hasBomb) countMarkedBombs--;
    }

    private void bomb(){
        text.setFill(Color.RED);
        border.setFill(Color.RED);
    }

    private void showBomb(){
        text.setFill(Color.RED);
        border.setFill(Color.WHITE);
    }

    public static boolean getIsMarked(int i, int j){
        return grid[i][j].isMarked;
    }
    public static boolean getIsOpened(int i, int j){
        return grid[i][j].isOpened;
    }
    public static int getCountBomb(int i, int j){
        return grid[i][j].countBomb;
    }
    public static void getBombInfo(int tilex, int tiley){
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
        Tile.isEnd = false;
        Tile.isWin = false;
    }
    public static void setTrueIsFirstClick(){
        isFirstClick = true;
    }

}
