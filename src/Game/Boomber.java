package Game;

public class Boomber extends Character {
    protected int health;

    public Boomber() {}

    public Boomber(/*Graphic graphic, */Position position) {
        /*this.graphic = graphic;*/
        this.health = 3;
        this.position = position;
    }

    public void heal() {
        if (this.health < 3) {
            health ++;
        }
    }

    public void damaged() {
        if (this.health != 0) {
            health --;
        }
    }

    public void controlMove() {
        //KeyBoard control.
    }

    public Boom createBoom() {
        return new Boom(this.position);
    }
}
