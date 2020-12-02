package entities.barrier;


import entities.Entity;
import graphics.Sprite;

public class Wall extends Barrier {
	public Wall(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public boolean collide(Entity e) {
		return false;
	}
        
}
