package entities.barrier;

import entities.Entity;
import graphics.Renderer;
import graphics.Sprite;

public abstract class Barrier extends Entity {
	
	public Barrier (int x, int y, Sprite sprite) {
		positionX = x;
		positionY = y;
		this.sprite = sprite;
	}

	public boolean collide(Entity e) {
		return false;
	}

	public void render(Renderer renderer) {
		renderer.renderEntity(tileToPixel(positionX), tileToPixel(positionY), this);
	}

	public void update() {}
}
