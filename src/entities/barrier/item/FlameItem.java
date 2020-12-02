package entities.barrier.item;

import factory.Game;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public boolean collide(Entity e) {
		if(e instanceof Bomber) {
			((Bomber) e).powerUp (this);
			remove();
			return true;
		}
		return false;
	}

    public void powerUp() {
		Game.addBombRadius(1);
    }

}
