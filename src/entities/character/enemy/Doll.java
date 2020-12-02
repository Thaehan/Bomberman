package entities.character.enemy;

import factory.Board;
import factory.Game;
import entities.character.enemy.ai.AILow;
import graphics.Sprite;

public class Doll extends Enemy {
	public Doll (int x, int y, Board board) {
		super (x, y, board, Sprite.doll_dead, Game.getBomberSpeed());
		sprite = Sprite.doll_right1;

		ai = new AILow (this.board.getBomber(), this, board);
		moveMent = ai.calculateDirection();
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