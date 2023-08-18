package ui.graphical;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import model.Board;

public class PlayScreenInputProcessor extends InputAdapter {

    TicTacToeGame game;
    PlayScreen playScreen;
    GraphicalBoard graphicalBoard;
    int activePlayerNo;
    GraphicalGamepiece selected;

    public PlayScreenInputProcessor(TicTacToeGame game) {
        this.game = game;
        playScreen = game.playScreen;
        graphicalBoard = playScreen.graphicalBoard;
        selected = null;
        activePlayerNo = Board.X;
    }

    public void setActivePlayerNo(int playerNo) {
        activePlayerNo = playerNo;
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (null != selected) {
            selected.resetLocation();
            selected = null;
        }
        Vector3 coordinates = unprojectScreenCoordinates(screenX, screenY);
        for (GraphicalGamepiece piece : playScreen.pieces) {
            if ((piece.getPlayerNo() == activePlayerNo) && piece.isHit(coordinates.x, coordinates.y)) {
                selected = piece;
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (null != selected) {
            Vector3 coordinates = unprojectScreenCoordinates(screenX, screenY);
            int boardColumn = graphicalBoard.getBoardColumn(coordinates.x);
            int boardRow = graphicalBoard.getBoardRow(coordinates.y);
            if (graphicalBoard.isHit(coordinates.x,coordinates.y) &&
               (!graphicalBoard.isOccupied(boardColumn,boardRow))) {
                // send coordinates of the board location chosen by the user to the game control.
                // This action will "unfreeze" game control thread waiting for input in GuiPlayer.getMove()
                game.uiStream.printf("%d %d\n",boardColumn, boardRow);
                selected.setVisible(false);
            } else {
                selected.resetLocation();
            }
            selected = null;
        }
        return true;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean result = false;
        if (null != selected) {
            Vector3 coordinates = unprojectScreenCoordinates(screenX, screenY);
            selected.setLocation(coordinates.x-selected.picture.getRegionWidth()*0.5f,
                                 coordinates.y-selected.picture.getRegionHeight()*0.5f);
            result = true;
        }
        return result;
    }

    Vector3 unprojectScreenCoordinates(int x, int y) {
        Vector3 screenCoordinates = new Vector3(x, y,0);
        Vector3 worldCoordinates = game.camera.unproject(screenCoordinates);
        return worldCoordinates;
    }
}
