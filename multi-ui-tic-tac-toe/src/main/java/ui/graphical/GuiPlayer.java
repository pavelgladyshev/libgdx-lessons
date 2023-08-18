package ui.graphical;

import model.Move;
import model.Player;
import ui.text.TextUI;

public class GuiPlayer extends Player {

    GUI ui;
    public GuiPlayer(int playerNo, GUI ui) {
        super(playerNo);
        this.ui = ui;
    }

    public Move getMove() {
        ui.showMessage("drag and place "+TextUI.playerSymbol(getPlayerNo())+" piece");
        ui.setActivePlayerNo(getPlayerNo());

        int x = ui.getXCoordinate(); // waits until TicTacToeGame prints x and y of the move into pipedOutputStream
        int y = ui.getYCoordinate();

        return new Move(x, y, getPlayerNo());
    }
}
