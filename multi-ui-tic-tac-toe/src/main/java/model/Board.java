package model;

import java.nio.charset.StandardCharsets;

public class Board {
    public final static int WIDTH = 3;
    public final static int HEIGHT = 3;
    public final static int X = 0;
    public final static int O = 1;

    boolean[][] occupied = new boolean[WIDTH][HEIGHT];
    int[][] occupyingPlayer = new int[WIDTH][HEIGHT];

    public Board() {
        for (int y=0; y<HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                occupied[x][y] = false;
            }
        }
    }

    public Board(Board b) {
        for (int y=0; y<HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                occupied[x][y] = b.isOccupied(x,y);
                occupyingPlayer[x][y] = b.getPlayerNo(x,y);
            }
        }
    }

    public Board(String init) {
        int x,y;

        x=y=0;

        for(byte b : init.getBytes(StandardCharsets.UTF_8)) {
            switch(b) {
                case 'X':
                    occupied[x][y] = true;
                    occupyingPlayer[x][y] = X;
                case 'O':
                    occupied[x][y] = true;
                    occupyingPlayer[x][y] = O;
                case '\n':
                    y++;
            }
            x++;
        }
    }

    public int getPlayerNo(int x, int y) {
        return occupyingPlayer[x][y];
    }

    public boolean isOccupied(int x, int y) {
        return occupied[x][y];
    }

    public boolean isValidMove(Move m) {
        boolean result = false;
        int x = m.getX();
        int y = m.getY();
        if (x>=0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            if (!occupied[x][y] && ((m.getPlayerNo() == X) || (m.getPlayerNo() == O))) {
                result = true;
            }
        }
        return result;
    }

    public boolean makeMove(Move m) {
        boolean result = false;
        if (isValidMove(m)) {
            occupied[m.getX()][m.getY()] = true;
            occupyingPlayer[m.getX()][m.getY()] = m.getPlayerNo();
            result = true;
        }
        return result;
    }

    public boolean squareOccupiedBy(int x, int y, int playerNo) {
        boolean result = true;
        if (x<0 || x > WIDTH || y < 0 || y > HEIGHT) {
            result = false;
        } else if (!occupied[x][y]) {
            result = false;
        } else if (this.occupyingPlayer[x][y] != playerNo) {
            result = false;
        }
        return result;
    }

    public boolean hasWon(int playerNo) {
        boolean hasWon = false;
        if ( (playerNo == X) || (playerNo == O))
        {
            // check for winning rows
            for (int y = 0; y<HEIGHT; y++) {
                boolean winningRow = true;
                for (int x = 0; x<WIDTH; x++) {
                    if (!squareOccupiedBy(x,y,playerNo)) winningRow = false;
                }
                if (winningRow) hasWon = true;
            }
            // check for winning columns
            for (int x = 0; x<WIDTH; x++) {
                boolean winningColumn = true;
                for (int y = 0; y<HEIGHT; y++) {
                    if (!squareOccupiedBy(x,y,playerNo)) winningColumn = false;
                }
                if (winningColumn) hasWon = true;
            }

            // check for winning diagonals
            boolean winningMajorDiagonal = true;
            for (int x = 0; x<WIDTH; x++) {
                if (!squareOccupiedBy(x,x,playerNo)) winningMajorDiagonal = false;
            }
            if (winningMajorDiagonal) hasWon = true;

            boolean winningMinorDiagonal = true;
            for (int x = 0; x<WIDTH; x++) {
                if (!squareOccupiedBy(x,HEIGHT-x-1,playerNo)) winningMinorDiagonal = false;
            }
            if (winningMinorDiagonal) hasWon = true;
        }
        return hasWon;
    }

    public boolean isADraw() {
        boolean hasEmplySquares = false;
        for (int y = 0; y<HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++ ) {
                if (!isOccupied(x,y)) {
                    hasEmplySquares = true;
                }
            }
        }
        return !hasEmplySquares;
    }

    public boolean gameOver() {
        return hasWon(X) || hasWon(O) || isADraw();
    }
}
