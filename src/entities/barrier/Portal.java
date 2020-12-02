package entities.barrier;

import factory.Board;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;

public class Portal extends Barrier {
    public Board board;
	public Portal(int x, int y,Board board ,Sprite sprite) {
		super(x, y, sprite);
                this.board =board;
	}

	public boolean collide(Entity e) {
		if (e instanceof Bomber){
			if (!board.isEnemyCleared()) {
				return false;
			}
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(board.isEnemyCleared()) {
					board.nextLevel();
				}
			}
		}
		return true;
	}

}
