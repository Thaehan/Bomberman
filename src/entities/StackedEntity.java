package entities;

import entities.barrier.Brick;
import graphics.Renderer;

import java.util.LinkedList;

public class StackedEntity extends Entity {
	protected LinkedList<Entity> entities = new LinkedList<>();
	public StackedEntity (int x, int y, Entity ... entities) {
		positionX = x;
		positionY = y;
		
		for (int i = 0; i < entities.length; i++) {
			this.entities.add(entities[i]);
			if (i > 1) {
				if(entities[i] instanceof Brick) {
					((Brick) entities[i]).addStackedSprite (entities[i - 1].getSprite ());
				}
			}
		}
	}

	public void update() {
		clearRemoved();
		getTopEntity().update();
	}

	public void render(Renderer renderer) {
		getTopEntity().render(renderer);
	}
	
	public Entity getTopEntity() {
		
		return entities.getLast();
	}
	
	private void clearRemoved() {
		Entity top  = getTopEntity();
		if(top.isRemoved())  {
			entities.removeLast();
		}
	}

	public boolean collide(Entity e) {
		return getTopEntity().collide(e);
	}

}
