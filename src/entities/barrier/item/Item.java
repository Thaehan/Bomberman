package entities.barrier.item;

import entities.barrier.Barrier;
import graphics.Sprite;

public abstract class Item extends Barrier {
        
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract void powerUp();


}
