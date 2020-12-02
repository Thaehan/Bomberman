package entities.barrier;


import entities.Entity;
import graphics.Sprite;

public class Grass extends Barrier {
	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public boolean collide(Entity e) {
		return true;
	}
}
