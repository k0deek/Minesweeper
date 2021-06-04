package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ScoreController {

    private ObservableList<Player> playersData = FXCollections.observableArrayList();
    public Button returnButton;
    public TableView<Player> scoreTable;

    @FXML
    private TableColumn<Player, String> playerName;

    @FXML
    private TableColumn<Player, Integer> playerTime;

    @FXML
    private void initialize() throws IOException {
        initData();

        playerName.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));
        playerTime.setCellValueFactory(new PropertyValueFactory<Player, Integer>("Time"));

        scoreTable.setItems(playersData);

    }

    private void initData() throws IOException {
        File file = new File((new File("")).getAbsolutePath() + "/src/sample/scoreTable.properties");
        FileInputStream fin = new FileInputStream(file);

        Properties prop = new Properties();
        prop.load(fin);
        Enumeration<?> keys = prop.propertyNames();
        while (keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            playersData.add(new Player(key, Integer.parseInt(prop.getProperty(key))));
        }

    }


    public void returnClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene aboutScene = new Scene(parent);
        Scene mainScene = returnButton.getScene();
        Stage stage = (Stage) mainScene.getWindow();
        stage.setScene(aboutScene);
    }


}
