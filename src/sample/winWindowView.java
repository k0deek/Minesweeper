package sample;

import com.sun.source.tree.LambdaExpressionTree;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class winWindowView {

    public WinGameMenuController winController;
    public int score;

    public Parent initialize(int score) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("winGameMenu.fxml").openStream());
        winController = loader.getController();
        winController.scoreField.setText(Integer.toString(score));
        this.score = score;
        return root;
    }
}
