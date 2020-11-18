package Game;

public class Position {
    private int x;
    private int y;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
