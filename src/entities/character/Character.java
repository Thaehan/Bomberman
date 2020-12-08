package entities.character;

import factory.ControlPanel;
import entities.AnimationEntity;
import graphics.Renderer;

/**
 * Bao gồm Bomber và Enemy
 */
public abstract class Character extends AnimationEntity {
	
	public ControlPanel controlPanel;
	protected int moveMent;
	protected boolean isAlive = true;
	protected boolean moveAble = false;
	public int timeAfter = 40;
	
	public Character(int x, int y, ControlPanel controlPanel) {
		positionX = x;
		positionY = y;
		this.controlPanel = controlPanel;
	}

	public abstract void update();

	public abstract void render(Renderer renderer);

	/**
	 * Tính toán hướng đi
	 */
	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);

	/**
	 * Được gọi khi đối tượng bị tiêu diệt
	 */
	public abstract void kill();

	 // Xử lý hiệu ứng bị tiêu diệt
	protected abstract void afterKill();


	//Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
	protected abstract boolean canMove(double x, double y);
}
