package entities.bomb;

import factory.ControlPanel;
import factory.Game;
import entities.AnimationEntity;
import entities.Entity;
import entities.character.Bomber;
import graphics.Renderer;
import graphics.Sprite;

public class Bomb extends AnimationEntity {

	protected double timer = 120;
	public int explodeTime = 20;
	
	protected ControlPanel controlPanel;
	protected Flame[] flame;
	protected boolean isExploded = false;
	protected boolean throughable = true;

    public boolean isExploded() {
        return isExploded;
    }

    public boolean isAllowedToPassThroght() {
        return throughable;
    }

	public Bomb(int x, int y, ControlPanel controlPanel) {
    	positionX = x;
		positionY = y;
		this.controlPanel = controlPanel;
		sprite = Sprite.bomb;
	}

	public void update() {
		if(timer > 0)
			timer--;
		else {
			if(!isExploded) {
				explode();
			}
			else {
				updateFlames ();
			}
			if(explodeTime > 0) {
				explodeTime--;
			}
			else {
				remove();
			}
		}
		animate();
	}

	public void render(Renderer renderer) {
		if (isExploded) {
			sprite = Sprite.bomb_exploded2;
			renderFlames(renderer);
		}
		else {
			sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animation , 60);
		}

		//ToDo: check;
		renderer.renderEntity((int) positionX << 4, (int) positionY << 4, this);
	}
	
	public void renderFlames(Renderer renderer) {
		for (int i = 0; i < flame.length; i++) {
			flame[i].render(renderer);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < flame.length; i++) {
			flame[i].update();
		}
	}


	protected void explode() {
    	isExploded = true;
    	throughable = true;
    	if (this.controlPanel.getBomber().getXTile() == this.getXTile() && this.getYTile() == this.controlPanel.getBomber().getYTile()) {
    		this.controlPanel.getBomber().kill();
    	}

    	//Create flame explode
		flame = new Flame[4];
    	for (int i = 0; i < flame.length; i++) {
    		flame[i] = new Flame((int) positionX, (int) positionY, i, Game.getPower(), controlPanel);
    	}
	}
	
        
	public FlameRange flameAt(int x, int y) {
		if (!isExploded) return null;
		for (int i = 0; i < flame.length; i++) {
			if(flame[i] == null) {
				return null;
			}
			FlameRange e = flame[i].flameSegmentAt(x, y);
			if (e != null) {
				return e;
			}
		}
		return null;
	}

	public boolean collide(Entity e) {
    	if (e instanceof Bomber) {
    		double pX = e.getX() - tileToPixel(getX());
    		double pY = e.getY() - tileToPixel(getY());
    		// ask if bomber has left the position planted bomb
			if (!(pY >= 1 && pY <= 30 && pX >= -10 && pX < 16)) {
				throughable = false;
			}
			return throughable;
	    }
    	if (e instanceof Flame){
    		this.timer =0;
    		// cho quả bom này thời gian nổ về 0
			return true;
    	}
    	if (e instanceof FlameRange){
    		this.timer =0;
    		// cho bom có thời gian nổ về 0
			return true;
    	}

    	if ( e instanceof Bomb ){
                //truong hop bom dat cung 1 luc
			if (((Bomb) e).isExploded()) {
				this.timer =0;
				return true;
			}
    	}
    	return false;
	}

	//tọa độ trong hệ tọa độ ô vuông
	public int getXTile(){
    	return (int) this.positionX;
    }

	public int getYTile(){
    	return (int) this.positionY;
    }
        
        
}
