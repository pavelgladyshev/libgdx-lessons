package model;

public class Move {
    int x;
    int y;
    int playerNo;

    public Move(int x, int y, int playerNo) {
        this.x = x;
        this.y = y;
        this.playerNo = playerNo;
    }

    public Move(Move m) {
        this.x =m.getX();
        this.y = m.getY();
        this.playerNo = m.getPlayerNo();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public boolean equal(Object o) {
        boolean isEqual = false;
        if (null != o) {
            if (o.getClass() == Move.class) {
                Move m = (Move) o;
                if ((this.x == m.getX()) && (this.y == m.getY()) && (this.playerNo == m.getPlayerNo())) {
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }
}
