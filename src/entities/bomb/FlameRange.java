package entities.bomb;

import entities.Entity;
import entities.character.Bomber;
import graphics.Renderer;
import graphics.Sprite;


public class FlameRange extends Entity {
	protected boolean isLastFlame;

	//ask if last flame range
	public FlameRange(int x, int y, int direction, boolean last) {
		positionX = x;
		positionY = y;
		isLastFlame = last;

		switch (direction) {
			case 0:
				if (!last) {
					sprite = Sprite.explosion_vertical2;
				}
				else {
					sprite = Sprite.explosion_vertical_top_last2;
				}
				break;
			case 1:
				if (!last) {
					sprite = Sprite.explosion_horizontal2;
				}
				else {
					sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if (!last) {
					sprite = Sprite.explosion_vertical2;
				}
				else {
					sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if (!last) {
					sprite = Sprite.explosion_horizontal2;
				}
				else {
					sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}

	public void render(Renderer renderer) {
		int xt = (int) positionX << 4;
		int yt = (int) positionY << 4;
		renderer.renderEntity(xt, yt ,this);
	}

	public void update() {}

	public boolean collide(Entity e) {
		if(e instanceof Bomber) {
			((Bomber) e).kill();
		}
		if ( e instanceof Bomb){
			((Bomb) e).explode();
		}
		return true;
	}

}