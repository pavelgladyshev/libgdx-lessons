package control;

import model.Board;
import model.Move;
import model.Player;
import ui.UI;

import java.util.Random;

public class GameControl implements Runnable {

    UI ui;
    Player[] players;

    public GameControl(UI ui, Player[] players) {
        this.ui = ui;
        this.players = players;
    }

    @Override
    public void run() {
        Board board = new Board();
        for (Player p : players ) {
            p.setName(ui.getPlayerName(p.getPlayerNo()));
        }

        int activePlayer = new Random().nextInt(players.length);
        Move m;
        do {
            do {
                ui.updateBoardDisplay(board);
                m = players[activePlayer].getMove();
            } while (!board.makeMove(m));
            activePlayer = (activePlayer + 1) % players.length;
        } while (!board.gameOver());

        ui.updateBoardDisplay(board);

        if (board.isADraw()) {
            ui.announceGameOver("It is a draw!");
        } else {
            for (Player p : players) {
                if (board.hasWon(p.getPlayerNo())) {
                    ui.announceGameOver("Player " + p.getName() + " has won!");
                }
            }
        }
    }
}
