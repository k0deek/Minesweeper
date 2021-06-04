package sample;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {

    public Button PlayAgain;
    public Button returnToMenu;
    public Stage mainStage = Main.primaryStage;

    public void clickedPlay(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameMenu.fxml"));
        Scene gameMenuScene = new Scene(parent);
        mainStage.setScene(gameMenuScene);

        Scene subScene = PlayAgain.getScene();
        Stage stage = (Stage) subScene.getWindow();
        stage.close();

    }

    public void clickedReturn(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene MenuScene = new Scene(parent);
        mainStage.setScene(MenuScene);

        Scene subScene = returnToMenu.getScene();
        Stage stage = (Stage) subScene.getWindow();
        stage.close();
    }
}
