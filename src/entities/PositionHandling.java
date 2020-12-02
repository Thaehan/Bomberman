package entities;

import factory.Game;

public class PositionHandling {
    //Chuyển vật thể -> tọa độ và ngược lại để xử lý va chạm các kiểu.
	public static int pixelToTile(double i) {
		return (int)(i / Game.DEFAULT_SIZE);
	}
	
	public static int tileToPixel(int i) {
		return i * Game.DEFAULT_SIZE;
	}
	
	public static int tileToPixel(double i) {
		return (int)(i * Game.DEFAULT_SIZE);
	}
	
	
}
