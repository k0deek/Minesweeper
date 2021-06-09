package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.util.Scanner;

import static sample.GameController.gameView;

public class TextController {
    final int ExitFlag = 0;
    final int NewGameFlag = 1;

    public void showGameField(int lengthField, Integer[][] userMineField) {
        for (int i = 0; i < lengthField; ++i) {
            for (int j = 0; j < lengthField; ++j) {
                if (userMineField[i][j] == -5)
                    System.out.print("#");
                if (userMineField[i][j] == -1)
                    System.out.print("f");
                if (userMineField[i][j] >= 0 && userMineField[i][j] <= 9)
                    System.out.print(userMineField[i][j]);
            }
            System.out.println("\n");
        }
    }



    public int doGame(GameView model) throws IOException {
        System.out.println("Commands:\n");
        System.out.println("help\n");
        System.out.println("exit\n");
        System.out.println("open + field\n");
        System.out.println("flag + field\n");
        System.out.println("New game\n");
        int size = model.getLengthField();
        int minesCount = model.getMinesCount();
        Scanner scan = new Scanner(System.in);
        model.plantBombs(size, size);
        Integer[][] userMineField= model.getUserMineField(model);
        GameView.CellState[][] userField = model.generateUserField();
        while (model.countMarkedBombs < size*size - minesCount) {
            showGameField(size, userMineField);
            System.out.println("Enter your command");
            String turn = scan.nextLine();
            if (turn.equals("exit")) {
                return ExitFlag;
            }
            if (turn.equals("new game")) {
                return NewGameFlag;
            }
            if (turn.equals("help")) {
                System.out.println("Commands:\n");
                System.out.println("help\n");
                System.out.println("exit\n");
                System.out.println("open + field\n");
                System.out.println("flag + field\n");
                System.out.println("new game\n");
            }

            String[] comArgs;
            comArgs = turn.split(" ");
            if (comArgs[0].equals("open")) {
                Tile thisTile = model.grid[Integer.parseInt(comArgs[1].trim())][Integer.parseInt(comArgs[2].trim())];
                if (thisTile.hasBomb && !thisTile.isMarked) return 1;
                else if (!thisTile.isOpened && !thisTile.isMarked) gameView.openTiles(thisTile);
                else if (thisTile.isOpened) System.out.println("This is already pointed point! Choose another command\n");

            }
            if (comArgs[0].equals("flag")) {
                Tile thisTile = model.grid[Integer.parseInt(comArgs[1].trim())][Integer.parseInt(comArgs[2].trim())];
                if (!thisTile.isMarked){
                    thisTile.isMarked = true;
                    thisTile.mark();
                }
                else {
                    thisTile.isMarked = false;
                    thisTile.blank();
                }
                model.grid[Integer.parseInt(comArgs[1].trim())][Integer.parseInt(comArgs[2].trim())].mark();
            }
        }
        return ExitFlag;
    }

    public TextController() throws IOException {
        GameView model = new GameView();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter length of field: ");
        int howmuch = scan.nextInt();
        System.out.println("Enter count of mines: ");
        int minesCount = scan.nextInt();
        model.initialize1(howmuch, minesCount);
        int exit_status;
        do {
            exit_status = doGame(model);

        } while (exit_status != 0);
        if (model.getCountKnown() >= model.getLengthField() * model.getLengthField() - model.getMinesCount()) {
            System.out.println("You win!");
        }


    }



}