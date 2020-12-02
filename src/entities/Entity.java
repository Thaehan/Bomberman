package entities;

import factory.Game;
import graphics.Renderer;
import graphics.Sprite;

public abstract class Entity {
	protected double positionX, positionY;
	protected boolean isRemoved = false;
	protected Sprite sprite;

	public abstract void update();

	public abstract void render(Renderer renderer);
	
	public void remove() {
		isRemoved = true;
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public abstract boolean collide(Entity e);
	
	public double getX() {
		return positionX;
	}
	
	public double getY() {
		return positionY;
	}
	
	// tọa độ trong hệ tọa độ ô/
	public static int pixelToTile(double i) {
		return (int)(i / Game.DEFAULT_SIZE);
	}

	public static int tileToPixel(int i) {
		return i * Game.DEFAULT_SIZE;
	}

	public static int tileToPixel(double i) {
		return (int)(i * Game.DEFAULT_SIZE);
	}

	public int getXTile() {
		return pixelToTile(positionX + sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return pixelToTile(positionY - sprite.SIZE / 2);
	}
}
