package entities.bomb;

import factory.ControlPanel;
import entities.Entity;
import entities.character.Bomber;
import entities.character.enemy.Enemy;
import graphics.Renderer;
//import uet.oop.bomberman.sound_effective.Sound;

public class Flame extends Entity {
	protected ControlPanel controlPanel;
	protected int moveMent;
	private int firePower;
	protected int xOrigin, yOrigin;
	protected FlameRange[] flameRange = new FlameRange[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param firePower độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int firePower, ControlPanel controlPanel) {
		xOrigin = x;
		yOrigin = y;
		positionX = x;
		positionY = y;
		moveMent = direction;
		this.firePower = firePower;
		this.controlPanel = controlPanel;
		createFlameRange();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameRange() {
		flameRange = new FlameRange[calculatePermitedDistance()];
		boolean lastRangeOfFlame;
		//  tạo các cạnh dưới đây
		int x = (int) positionX;
		int y = (int) positionY;
		for (int i = 0; i < flameRange.length; i++) {
			lastRangeOfFlame = (i == flameRange.length - 1);
			switch (moveMent) {
				case 0:
					y--;
					break;
				case 1:
					x++;
					break;
				case 2:
					y++;
					break;
				case 3:
					x--;
					break;
			}
			flameRange[i] = new FlameRange (x, y, moveMent, lastRangeOfFlame);
		}
	}

	//Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	private int calculatePermitedDistance() {
		//thực hiện tính toán độ dài của Flame khi bom nổ
		int range = 0;
		int x = (int) positionX;
		int y = (int) positionY;
		while(range < firePower) {
			if (moveMent == 0) {
				y--;// lên trên
			}
			if (moveMent == 1) {
				x++;// sang phải
			}
			if (moveMent == 2) {
				y++;// sang phải
			}
			if (moveMent == 3) {
				x--;// sang trái
			}
			// không thể bị đi qua
			Entity entity = controlPanel.getEntity(x, y, null);

			if (!entity.collide(this)) {
				break;
			}
			++range;
		}
		return range;
	}
	
	public FlameRange flameSegmentAt(int x, int y) {
		for (int i = 0; i < flameRange.length; i++) {
			if(flameRange[i].getX() == x && flameRange[i].getY() == y)
				return flameRange[i];
		}
		return null;
	}

	public void update() {}

	public void render(Renderer renderer) {
		for (int i = 0; i < flameRange.length; i++) {
			flameRange[i].render(renderer);
		}
	}

	public boolean collide(Entity e) {
		if (e instanceof Enemy) {
			((Enemy) e).kill();
			return false;
		}
		if (e instanceof Bomber) {
			((Bomber)e).kill();
			return false;
		}
		return true;
            
	}
}
