package entities.character.enemy;

import factory.ControlPanel;
import factory.Game;
import entities.Entity;
import entities.bomb.Flame;
import entities.character.Bomber;
import entities.character.Character;
import graphics.Renderer;
import graphics.Sprite;
//import uet.oop.bomberman.sound_effective.Sound;

public abstract class Enemy extends Character {
	protected double speed;
	protected Bot bot;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double footStep;
	
	protected int finalAnimation = 30;
	protected Sprite deadSprite;
	
	public Enemy(int x, int y, ControlPanel controlPanel, Sprite dead, double speed) {
		super(x, y, controlPanel);
		this.speed = speed;
		MAX_STEPS = Game.DEFAULT_SIZE / this.speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		footStep = MAX_STEPS;
		timeAfter = 20;
		deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		if(!isAlive) {
			afterKill();
			return;
		}
		if(isAlive) {
			calculateMove();
		}
	}
	
	@Override
	public void render(Renderer renderer) {
		if (isAlive) {
			chooseSprite();
		}
		else {
			if(timeAfter > 0) {
				sprite = deadSprite;
				animation = 0;
			} else {
				sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animation , 60);
			}
		}
			
		renderer.renderEntity((int) positionX, (int) positionY - sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		//  Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		//  sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		//  sử dụng move() để di chuyển
		//  nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển

		int xa = 0, ya = 0;
            
		if (footStep <= 0){
			moveMent = bot.calculateDirection();
			footStep = MAX_STEPS;
		}

		//giống như bomber  nhận cái dircton từ ai sẽ đi theo chiều
		if(moveMent == 0) ya--;
		if(moveMent == 2) ya++;
		if(moveMent == 3) xa--;
		if(moveMent == 1) xa++;
		// kiểm tra bằng canmove và cập nhập trạng thái _moving cho emnemy

		if(canMove(xa, ya)) {
			footStep -= 1 + rest;
			move(xa * speed , ya * speed);
			moveAble = true;
		}
		else {
			footStep = 0;
			moveAble = false;
		}
	}

	public void move(double xa, double ya) {
		if (!isAlive) {
			return;
		}
		positionY += ya;
		positionX += xa;
	}

	public boolean canMove(double x, double y) {
		// kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
		double xr = positionX, yr = positionY - 16; //subtract y to get more accurate results
		
		//the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
		//we avoid the shaking inside tiles with the help of steps
		if(moveMent == 0) {
			yr += sprite.getSize() -1;
			xr += sprite.getSize() / 2;
		}
		if(moveMent == 1) {
			yr += sprite.getSize() / 2;
			xr += 1;
		}
		if(moveMent == 2) {
			xr += sprite.getSize() / 2;
			yr += 1;
		}
		if(moveMent == 3) {
			xr += sprite.getSize() -1;
			yr += sprite.getSize() / 2;
		}
		
		int xx = pixelToTile(xr) +(int)x;
		int yy = pixelToTile(yr) +(int)y;
		
		Entity a = controlPanel.getEntity(xx, yy, this); //entity of the position we want to go
		
		return a.collide(this);		
                
               
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Flame) {
			kill();
			return false;
		}

		if (e instanceof Bomber) {
			((Bomber) e).kill();
			return false;
		}
		return true;
	}
	
	@Override
	public void kill() {
		if (!isAlive) {
			return;
		}
		isAlive = false;
	}
	
	
	@Override
	protected void afterKill() {
		if(timeAfter > 0) {
			--timeAfter;
		}
		else {
			if(finalAnimation > 0) {
				--finalAnimation;
			}
			else {
				remove();
			}
		}
	}
	
	protected abstract void chooseSprite();
}
