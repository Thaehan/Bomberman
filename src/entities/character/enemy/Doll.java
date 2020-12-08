package entities.character.enemy;

import factory.ControlPanel;
import factory.Game;
import graphics.Sprite;

public class Doll extends Enemy {
	public Doll (int x, int y, ControlPanel controlPanel) {
		super (x, y, controlPanel, Sprite.doll_dead, Game.getSpeed());
		sprite = Sprite.doll_right1;
		bot = new Bot();
		moveMent = bot.calculateDirection();
	}

	protected void chooseSprite () {
		switch (moveMent) {
			case 0:
			case 1:
				if (moveAble) {
					sprite = Sprite.movingSprite (Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animation , 20);
				}
				else {
					sprite = Sprite.doll_left1;
				}
				break;
			case 2:
			case 3:
				if (moveAble) {
					sprite = Sprite.movingSprite (Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animation , 20);
				}
				else {
					sprite = Sprite.doll_left1;
				}
				break;
		}
	}

}