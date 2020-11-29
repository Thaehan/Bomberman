package Entity;

import javafx.scene.image.Image;

public class Bomber extends Entity {
    protected int valX = 0;
    protected int valY = 0;

    public Bomber(int x, int y, Image image) {
        super(x, y, image);
    }

    public void setValX(int x) {
        this.valX = x;
    }

    public void setValY(int y) {
        this.valY = y;
    }

    public int getValX() {
        return this.valX;
    }

    public int getValY() {
        return this.valY;
    }

    public void update() {
        setPosition(positionX + valX, positionY + valY);
    }
}
