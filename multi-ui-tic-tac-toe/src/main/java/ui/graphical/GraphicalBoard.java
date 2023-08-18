package ui.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Board;

public class GraphicalBoard {
    float boardX;
    float boardY;
    float boardWidth;
    float boardHeight;
    float cellWidth;
    float cellHeight;
    TextureRegion xPicture;
    TextureRegion oPicture;
    Board board;

    public GraphicalBoard(float boardX, float boardY, float boardWidth, float boardHeight, TextureRegion xPicture, TextureRegion oPicture, Board board) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.xPicture =xPicture;
        this.oPicture = oPicture;
        this.board = board;
        cellHeight = boardHeight / Board.HEIGHT;
        cellWidth = boardWidth / Board.WIDTH;
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }

    public void draw(SpriteBatch batch) {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (board.isOccupied(x,y)) {
                    TextureRegion picture;
                    picture = (board.getPlayerNo(x,y) == Board.X) ? xPicture : oPicture;
                    batch.draw(
                            picture,
                            boardX + x * cellWidth + cellWidth * 0.2f,
                            boardY + y * cellHeight  + cellHeight * 0.1f
                    );
                }
            }
        }
    }

    public int getBoardColumn(float x) {
        int result = -1;
        if ((boardX < x) && ((boardX + boardWidth) > x)) {
            result = (int)((x - boardX) / cellWidth);
        }
        return result;
    }

    public int getBoardRow(float y) {
        int result = -1;
        if ((boardY < y) && ((boardY + boardHeight) > y)) {
            result = (int)((y - boardY) / cellHeight);
        }
        return result;
    }

    public boolean isHit(float x, float y) {
        return  (getBoardColumn(x) != -1) && (getBoardRow(y) != -1);
    }

    public boolean isOccupied(int boardColumn, int boardRow) {
        return board.isOccupied(boardColumn,boardRow);
    }
}
