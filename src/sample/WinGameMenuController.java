package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

public class WinGameMenuController {
    public Button PlayAgain;
    public Button returnToMenu;
    public TextField nameField;
    public Label scoreField;

    public Stage mainStage = Main.primaryStage;

    public static winWindowView winView = new winWindowView();


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

    public void typedName(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        Integer score = winView.score;
        String scoreSec = Integer.toString(score);


        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("").getAbsolutePath() + "/src/sample/scoreTable.properties"));
        properties.setProperty(name, scoreSec);
        //properties.put(name, score);
        String play = properties.getProperty(name);

        String newAppConfigPropertiesFile = new File("").getAbsolutePath() + "/src/sample/scoreTable.properties";
        properties.store(new FileWriter(newAppConfigPropertiesFile), "");

    }
}
