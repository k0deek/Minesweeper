package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.*;
import java.awt.image.SampleModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Application {

    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) throws IOException {
        System.out.println(args.length);
        if(args.length == 0)
            launch(args);
        else
        {
            //TextController textgame = new TextController();
        }
    }
}
