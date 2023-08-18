import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import control.GameControl;
import model.Board;
import model.Player;
import ui.UI;
import ui.graphical.GUI;
import ui.graphical.GuiPlayer;
import ui.graphical.TicTacToeGame;
import ui.text.TextPlayer;
import ui.text.TextUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        boolean useGui = false;
        UI ui;
        Player[] players;
        GameControl gameControl;
        Thread gameControlThread;

        for (String arg : args) {
            if (arg.equals("-gui")) {
                useGui = true;
            }
        }

        if (useGui) {
            ui = new GUI();
            players = new Player[] {new GuiPlayer(Board.X,(GUI)ui), new GuiPlayer(Board.O,(GUI)ui) };
        } else {
            ui = new TextUI();
            players = new Player[] {new TextPlayer(Board.X,(TextUI)ui), new TextPlayer(Board.O,(TextUI)ui) };
        }
        gameControl = new GameControl(ui,players);
        gameControlThread = new Thread(gameControl);
        gameControlThread.start();

        if (useGui) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Tic Tac Toe");
            config.setWindowedMode(TicTacToeGame.WIDTH,TicTacToeGame.HEIGHT);
            config.setResizable(false);
            config.setWindowIcon("icon.png");
            new Lwjgl3Application(new TicTacToeGame(gameControlThread,ui), config);
        }
    }
}
