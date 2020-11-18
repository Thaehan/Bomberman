package Game;

public class Boom extends Barrier {

    public Boom() {}

    public Boom(Position position) {
        setDestroyable(true);
        setExist(true);
        setPosition(position);
        setThroughable(false);
    }

    public void explode() {

    }
}
