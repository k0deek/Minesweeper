package sample;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    public Button exitButton;
    public Button aboutButton;
    public Button gameButton;
    public Button scoreButton;

    public void exitClicked() {
        System.exit(0);
    }

    public void aboutClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene aboutScene = new Scene(parent);
        Scene mainScene = aboutButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(aboutScene);
    }

    public void gameClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameMenu.fxml"));
        Scene gameMenuScene = new Scene(parent);
        Scene mainScene = gameButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(gameMenuScene);
    }

    public void scoreClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("highscore.fxml"));
        Scene scoreScene = new Scene(parent);
        Scene mainScene = aboutButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(scoreScene);
    }




}
