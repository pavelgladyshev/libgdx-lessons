package ui;

import model.Board;

public interface UI {
    String getPlayerName(int playerNo);
    void showMessage(String greeting);
    void updateBoardDisplay(Board board);
    void announceGameOver(String message);
}
