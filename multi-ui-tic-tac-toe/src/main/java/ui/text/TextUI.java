package ui.text;

import model.Board;
import ui.UI;

import java.util.Scanner;

public class TextUI implements UI {

    protected Scanner scanner;

    public TextUI() {
        scanner = new Scanner(System.in);
    }

    public static String playerSymbol(int playerNo) {
        switch(playerNo) {
            case Board.X: return "X";
            case Board.O: return "O";
            default:      return " ";
        }
    }

    public String getPlayerName(int playerNo) {
        System.out.printf("Enter the name for player (%s): ",playerSymbol(playerNo));
        return scanner.next();
    }

    public void showMessage(String greeting) {
        System.out.println(greeting);
    }

    public void announceGameOver(String message) {
        showMessage(message);
    }

    public void updateBoardDisplay(Board board) {
        for (int y = Board.HEIGHT-1; y>= 0; y--) {
            System.out.printf("%d",y);
            for (int x = 0; x<Board.WIDTH; x++) {
                if (!board.isOccupied(x,y)) {
                    System.out.print(" .");
                } else {
                    String symbol = playerSymbol(board.getPlayerNo(x,y));
                    System.out.printf(" %s",symbol);
                }
            }
            System.out.println();
        }

        System.out.print(" ");
        for (int x = 0; x<Board.WIDTH; x++) {
            System.out.printf(" %d",x);
        }
        System.out.println();
    }

    public void askPlayerToEnterCoordinates(int playerNo) {
        String symbol = playerSymbol(playerNo);
        System.out.println("Player ("+symbol+") enter x and y coordinates of the square to put '"+symbol+"' into:");
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

}
