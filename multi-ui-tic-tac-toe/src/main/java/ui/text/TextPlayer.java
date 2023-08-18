package ui.text;

import model.Move;
import model.Player;
import ui.UI;

public class TextPlayer extends Player {

    TextUI ui;

    public TextPlayer(int playerNo, TextUI ui) {
        super(playerNo);
        this.ui = ui;
    }

    public Move getMove() {
        ui.askPlayerToEnterCoordinates(getPlayerNo());
        int x = ui.getXCoordinate();
        int y = ui.getYCoordinate();
        return new Move(x,y,getPlayerNo());
    }
}
