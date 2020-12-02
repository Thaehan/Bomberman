package entities.barrier;

import entities.Entity;
import entities.bomb.Bomb;
import entities.bomb.Flame;
import graphics.Renderer;
import graphics.Sprite;

import java.util.ArrayList;

/**
 * Đối tượng cố định có thể bị phá hủy
 */
public class Brick extends Barrier {
	public static ArrayList<Integer> xOfAllBrick = new ArrayList<>();
	public static ArrayList<Integer> yOfAllBrick = new ArrayList<>();
	private final int MAX_ANIMATE = 7500;
	private int animate = 0;
	protected boolean isDestroyed = false;
	protected int vanishingTime = 20;
	protected Sprite stackedSprite = Sprite.grass;
	
        
	public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public static void addXBrick(int x) {
		xOfAllBrick.add(x);
	}

	public static void addYBrick(int y) {
		yOfAllBrick.add(y);
	}

	public void update() {
		if(isDestroyed) {
			if(animate < MAX_ANIMATE) {
				animate++;
			}
			else {
				animate = 0;
			}
			
			if(vanishingTime > 0) {
				vanishingTime--;
			}
			else {
				remove();
			}
		}
	}

	public void destroy() {
		isDestroyed = true;
	}

	public void render(Renderer renderer) {
			int x = tileToPixel(positionX);
			int y = tileToPixel(positionY);

			if(isDestroyed) {
				sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
				renderer.renderEntityWithBelowSprite(x, y, this, stackedSprite);
			}
			else {
				renderer.renderEntity(x, y, this);
			}
		}

	public boolean collide(Entity e) {
		if( e instanceof Bomb){
			if(((Bomb)e).isExploded()) {
				destroy();
			}

		}
		if(e instanceof Flame){
			addXBrick (this.getXtile());
			addYBrick (this.getYtile());
			destroy();
			return false;
		}
		return false;
	}

	public void addStackedSprite(Sprite sprite) {
		stackedSprite = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = animate % 30;
		if(calc < 10) {
			return normal;
		}
			
		if(calc < 20) {
			return x1;
		}

		return x2;
	}

	public int getXtile(){
		return (int) this.positionX;
	}
	public int getYtile(){
		return (int) this.positionY;
	}
}
