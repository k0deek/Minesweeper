package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
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

    private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
    public Text text = new Text();

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

    public void open(){
        Color[] colors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.DARKGREEN,
        Color.MAGENTA, Color.RED, Color.BROWN, Color.DARKGOLDENROD, Color.OLIVE};
        this.isOpened = true;
        countOpened++;
        border.setFill(Color.WHITE);
        text.setFill(colors[countBomb]);
    }

    public void mark() throws IOException {
        text.setFill(Color.DARKOLIVEGREEN);
        border.setFill(Color.DARKOLIVEGREEN);

        if (hasBomb) countMarkedBombs++;
        if (countMarkedBombs == num_bomb){
            gameView.isWin = true;
            gameView.isEnd = true;
            gameView.gameController.winGame();
        }
    }

    public void blank(){
        text.setFill(Color.GRAY);
        border.setFill(Color.GRAY);

        if (hasBomb) countMarkedBombs--;
    }

    public void bomb(){
        text.setFill(Color.RED);
        border.setFill(Color.RED);
    }

    public void showBomb(){
        text.setFill(Color.RED);
        border.setFill(Color.WHITE);
    }

}
