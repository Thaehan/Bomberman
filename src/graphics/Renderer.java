package graphics;

import factory.Game;
import entities.Entity;
import factory.Map;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;

public class Renderer {
	protected int width, height;
	public int[] pixels;
	private int transparentColor = 0xffff00ff;
	
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderEntity(int xp, int yp, Entity entity) { //save entity pixels
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp; //add offset
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp; //add offset
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
				if(xa < 0) xa = 0; //start at 0 from left
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor) pixels[xa + ya * width] = color;
			}
		}
	}
	
	public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if (xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) {
					break; //fix black margins
				}
				if (xa < 0) {
					xa = 0;
				}
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if (color != transparentColor) {
					pixels[xa + ya * width] = color;
				}
				else {
					pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
				}
			}
		}
	}
	
	public void renderEnd(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * 3);
		g.setFont(font);
		g.setColor(Color.white);
		renderTitle ("You lose!", getRealWidth(), getRealHeight(), g);

	}

	public void start(Graphics g, Map map) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());

		Font font = new Font("Arial", Font.PLAIN, 20 * 3);
		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		if (map.getLevel() == 1) {
			renderTitle("Start", getRealWidth(), getRealHeight(), g);
		}
		else {
			renderTitle("Level " + map.getLevel(),getRealWidth(), getRealHeight(), g);
		}
	}

	public void renderTitle(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	}
	
	public int getRealWidth() {
		return width * 3;
	}
	
	public int getRealHeight() {
		return height * 3;
	}
}
