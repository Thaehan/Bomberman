package entities.character.enemy;

import factory.Board;
import factory.Game;
import entities.character.enemy.ai.AILow;
import graphics.Sprite;

public class Balloon extends Enemy {
	
	
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2);
		sprite = Sprite.balloom_left1;
		ai = new AILow( this.board.getBomber(), this , board);
		moveMent = ai.calculateDirection();
	}

	protected void chooseSprite() {
		switch(moveMent) {
			case 0:
			case 1:
				sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animation , 20);
				break;
			case 2:
			case 3:
				sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animation , 20);
				break;
		}
	}
}
