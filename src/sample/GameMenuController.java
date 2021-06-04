package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuController {
    public Button returnButton;
    public Button ThirtyButton;
    public Button TwentyButton;
    public Button NineButton;


    public void returnClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene aboutScene = new Scene(parent);
        Scene mainScene = returnButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(aboutScene);

    }

    public void ThirtyClicked() throws IOException {
        Scene mainScene = ThirtyButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        GameView gameView = GameController.gameView;
        Parent root = gameView.initialize(20, 20, 90);
        Scene anotherGame = new Scene(root);
        stage.setScene(anotherGame);
    }

    public void TwentyClicked() throws IOException {
        Scene mainScene = TwentyButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        GameView gameView = GameController.gameView;
        Parent root = gameView.initialize(16, 16, 40);
        Scene anotherGame = new Scene(root);
        stage.setScene(anotherGame);
    }

    public void NineClicked() throws IOException {
        Scene mainScene = NineButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        GameView gameView = GameController.gameView;
        Parent root = gameView.initialize(9, 9, 10);
        Scene anotherGame = new Scene(root);
        stage.setScene(anotherGame);


    }
}
