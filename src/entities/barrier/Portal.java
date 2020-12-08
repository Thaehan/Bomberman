package entities.barrier;

import factory.ControlPanel;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;

public class Portal extends Barrier {
    public ControlPanel controlPanel;
	public Portal(int x, int y, ControlPanel controlPanel, Sprite sprite) {
		super(x, y, sprite);
                this.controlPanel = controlPanel;
	}

	public boolean collide(Entity e) {
		if (e instanceof Bomber){
			if (!controlPanel.isEnemyCleared()) {
				return false;
			}
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(controlPanel.isEnemyCleared()) {
					controlPanel.nextLevel();
				}
			}
		}
		return true;
	}

}
