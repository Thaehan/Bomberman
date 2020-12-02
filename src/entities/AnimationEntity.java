package entities;

/**
 * Entity có hiệu ứng hoạt hình
 */
public abstract class AnimationEntity extends Entity {

	protected int animation = 0;
	protected final int MAX_ANIMATE = 7500;
	
	protected void animate() {
		if (animation < MAX_ANIMATE) {
			animation++;
		}
		else {
			animation = 0;
		}
	}

}
