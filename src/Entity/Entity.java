package Entity;

import Graphic.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Scanner;

public abstract class Entity {
    protected Image image;
    protected int positionX;
    protected int positionY;

    public Entity(int x, int y, Image image) {
        this.positionX = x * Sprite.SCALED_SIZE;
        this.positionY = y * Sprite.SCALED_SIZE;
        this.image = image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, this.positionX, this.positionY);
    }

    public void update() {}
}
