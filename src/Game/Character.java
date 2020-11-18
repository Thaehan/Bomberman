package Game;

public abstract class Character extends BaseSettings {
    //Graphic graphic;
    protected boolean isAlive;
    protected Position position;
    public void moveLeft() {
        position.setPosition(this.position.getX(), this.position.getY());
    }

    public void moveRight() {
        position.setPosition(this.position.getX(), this.position.getY());
    }

    public void moveUp() {
        position.setPosition(this.position.getX(), this.position.getY());
    }

    public void moveDown() {
        position.setPosition(this.position.getX(), this.position.getY());
    }

}
