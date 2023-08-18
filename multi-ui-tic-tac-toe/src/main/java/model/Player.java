package model;

public abstract class Player {

    int playerNo;
    String name;

    public Player(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Move getMove();
}
