package entities.character.enemy;

import factory.ControlPanel;
import factory.Game;
import graphics.Sprite;

public class Balloon extends Enemy {
	
	public Balloon(int x, int y, ControlPanel controlPanel) {
		super(x, y, controlPanel, Sprite.balloom_dead, Game.getSpeed() / 2);
		sprite = Sprite.balloom_left1;
		bot = new Bot();
		moveMent = bot.calculateDirection();
	}

	protected void chooseSprite() {
		switch(moveMent) {
			case 0:
			case 1: {
				sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animation , 20);
			}
				break;
			case 2:
			case 3: {
				sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animation , 20);
			}
				break;
		}
	}
}
