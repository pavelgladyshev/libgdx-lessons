package ui.graphical;

import com.badlogic.gdx.Gdx;
import model.Board;
import ui.UI;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class GUI implements UI {

    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private TicTacToeGame ticTacToeGame;
    private Scanner scanner;

    public GUI() throws IOException {
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream);
        scanner = new Scanner(pipedInputStream);
    }

    void setGame(TicTacToeGame ticTacToeGame) {
        this.ticTacToeGame = ticTacToeGame;
    }

    public PipedOutputStream getPipedOutputStream() {
        return pipedOutputStream;
    }

    public String getPlayerName(int playerNo) {
        return scanner.next();
    }

    public void showMessage(String greeting) {
        ticTacToeGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                ticTacToeGame.activatePlayScreen();
                ticTacToeGame.showMessage(greeting);
            }
        });
    }

    public void setActivePlayerNo(int playerNo) {
        ticTacToeGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                ticTacToeGame.setActivePlayerNo(playerNo);
            }
        });
    }

    public void updateBoardDisplay(Board board) {
        ticTacToeGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                ticTacToeGame.updateBoard(board);
            }
        });
    }

    int getInteger() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    public int getXCoordinate() {
        return getInteger();
    }

    public int getYCoordinate() {
        return getInteger();
    }

    public void announceGameOver(String message) {
        ticTacToeGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                ticTacToeGame.showMessage(message);
                Gdx.input.setInputProcessor(null);
            }
        });
    }


}
