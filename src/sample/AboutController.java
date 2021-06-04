package sample;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutController {

    public Button returnButton;

    public void returnClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene aboutScene = new Scene(parent);
        Scene mainScene = returnButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(aboutScene);
    }




}
