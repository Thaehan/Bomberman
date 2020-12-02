/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.character.enemy;

import factory.Board;
import factory.Game;
import entities.character.enemy.ai.AIMedium;
import graphics.Sprite;

/**
 *
 * @author DoQuangTrung
 */
public class Kondoria  extends Enemy{
    
	public Kondoria(int x, int y, Board board) {
		super (x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed());
		sprite = Sprite.kondoria_right1;
		ai = new AIMedium (this.board.getBomber(), this, board);
		moveMent = ai.calculateDirection();
	}

	protected void chooseSprite() {
		switch (moveMent) {
			case 0:
			case 1:
				if(moveAble) {
					sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animation, 20);
				}
				else {
					sprite = Sprite.kondoria_left1;
				}
				break;
			case 2:
			case 3:
				if(moveAble) {
					sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animation, 20);
				}
				else {
					sprite = Sprite.kondoria_left1;
				}
				break;
		}
	}
}
