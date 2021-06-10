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


    public void showGameField(int lengthField, Tile [][] grid) {
        for (int i = 0; i < lengthField; ++i) {
            for (int j = 0; j < lengthField; ++j) {
                if (!grid[i][j].isOpened)
                    //System.out.print(grid[i][j].countBomb);
                    System.out.print("#");
                else if (grid[i][j].isMarked)
                    System.out.print("f");
                else if (grid[i][j].countBomb >= 0 && grid[i][j].countBomb <= 9)
                    System.out.print(grid[i][j].countBomb);
            }
            System.out.println("\n");
        }
    }

    public int doGame(GameView model) throws IOException {
        System.out.println("Commands:");
        System.out.println("help");
        System.out.println("exit");
        System.out.println("open + field");
        System.out.println("flag + field");
        System.out.println("New game");
        int size = model.getLengthField();
        int minesCount = model.getMinesCount();
        Scanner scan = new Scanner(System.in);
        while (GameView.countMarkedBombs < size*size - minesCount) {
            showGameField(size, GameView.grid);
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
            int x = Integer.parseInt(comArgs[1].trim());
            int y = Integer.parseInt(comArgs[2].trim());
            if (x > model.X_TILES) x--;
            if (y > model.Y_TILES) y--;
            if (comArgs[0].equals("open")) {
                Tile thisTile = GameView.grid[x][y];
                System.out.print(thisTile.x);
                System.out.println(thisTile.y);
                if (thisTile.hasBomb && !thisTile.isMarked) return ExitFlag;
                else if (!thisTile.isOpened && !thisTile.isMarked) gameView.openTiles(thisTile);
                else if (thisTile.isOpened) System.out.println("This is already pointed point! Choose another command\n");

            }
            if (comArgs[0].equals("flag")) {
                Tile thisTile = GameView.grid[x][y];
                if (!thisTile.isMarked){
                    thisTile.isMarked = true;
                    thisTile.mark();
                }
                else {
                    thisTile.isMarked = false;
                    thisTile.blank();
                }
                GameView.grid[Integer.parseInt(comArgs[1].trim())][Integer.parseInt(comArgs[2].trim())].mark();
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
