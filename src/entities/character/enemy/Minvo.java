/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.character.enemy;

import factory.Board;
import factory.Game;
import graphics.Sprite;

/**
 *
 * @author DoQuangTrung
 */
public class Minvo extends Enemy{
    public Minvo(int x, int y, Board board) {
		super(x, y, board, Sprite.minvo_dead, Game.getBomberSpeed() * 0.1);
		sprite = Sprite.minvo_right1;
		moveMent = ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(moveMent) {
			case 0:
			case 1:
				if(moveAble) {
					sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animation , 60);
				}
				else {
					sprite = Sprite.minvo_left1;
				}
				break;
			case 2:
			case 3:
				if (moveAble) {
					sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animation , 60);
				}
				else {
					sprite = Sprite.minvo_left1;
				}
				break;
		}
	}
}
