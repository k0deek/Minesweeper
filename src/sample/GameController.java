package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController {

    public Button returnButton;
    public Pane gameField = new Pane();
    public Label timeField;

    public static boolean isWindowOpened = false;

    public static GameView gameView = new GameView();

    public void clickedReturn(ActionEvent actionEvent) throws IOException {
        if (gameView.isEnd) return;
        Tile.setTrueIsFirstClick();
        Tile.isEnd = false;
        Tile.isWin = false;
        Parent parent = FXMLLoader.load(getClass().getResource("gameMenu.fxml"));
        Scene gameMenuScene = new Scene(parent);
        Scene mainScene = returnButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(gameMenuScene);
    }

    public void clickedTile(MouseEvent mouseEvent) throws IOException {
        if (gameView.isWin) {
            winGame();
        }
        int x = (int) (mouseEvent.getX() / Tile.TILE_SIZE);
        int y = (int) (mouseEvent.getY() / Tile.TILE_SIZE);
        if (x > gameView.X_TILES) x--;
        if (y > gameView.Y_TILES) y--;
        Tile thisTile = gameView.grid[x][y];
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            thisTile.open(thisTile);
        }
        if (mouseEvent.getButton() == MouseButton.SECONDARY){
            thisTile.flag(thisTile);
        }
        if (thisTile.isEnd) {
            if (thisTile.isWin){
                winGame();
                Tile.setTrueIsFirstClick();}
            else{
                gameOver(thisTile.x, thisTile.y);
                Tile.setTrueIsFirstClick();}
        }
    }

    private void gameOver(int x, int y) throws IOException {
        gameView.gameOver(x, y);
        Stage gameOver = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("gameOverMenu.fxml"));
        gameOver.setTitle("Minesweeper");
        gameOver.setScene(new Scene(root));
        gameOver.initModality(Modality.APPLICATION_MODAL);
        gameOver.showAndWait();
        gameOver.setResizable(false);
    }


     public void winGame() throws IOException {
        gameView.gameOver(0, 0);
        int score = gameView.score;
        Stage gameOver = new Stage();
        winWindowView winView = WinGameMenuController.winView;
        Parent root = winView.initialize(score);
        gameOver.setTitle("Minesweeper");
        gameOver.setScene(new Scene(root));
        gameOver.initModality(Modality.APPLICATION_MODAL);
        gameOver.showAndWait();
        gameOver.setResizable(false);
    }
}
