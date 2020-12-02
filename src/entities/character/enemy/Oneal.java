package entities.character.enemy;


import factory.Board;
import factory.Game;
import entities.character.enemy.ai.AIAdvance;
import graphics.Sprite;

public class Oneal extends Enemy {
	      
        
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed()*1.1);
		sprite = Sprite.oneal_left1;
		ai = new AIAdvance(this.board.getBomber(), this, board);
		moveMent = ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch (moveMent) {
			case 0:
			case 1:
				if (moveAble) {
					sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animation , 30);
				}
				else {
					sprite = Sprite.oneal_left1;
				}
				break;
			case 2:
			case 3:
				if (moveAble) {
					sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animation , 30 );
				}
				else {
					sprite = Sprite.oneal_left1;
				}
				break;
		}
	}
}
