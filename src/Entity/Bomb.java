package Entity;

import javafx.scene.image.Image;

public class Bomb extends Entity {
    private Image image;
    private Bomber bomber;
    private int power;
    private int timer;

    public Bomb(int x, int y, int power, int timer, Bomber bomber, Image image) {
        super(x, y, image);

    }

}
