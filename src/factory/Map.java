package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entities.StackedEntity;
import entities.character.Bomber;
import entities.character.enemy.Balloon;
import entities.character.enemy.Doll;
import entities.character.enemy.Kondoria;
import entities.character.enemy.Minvo;
import entities.character.enemy.Oneal;
import entities.barrier.Grass;
import entities.barrier.Portal;
import entities.barrier.Wall;
import entities.barrier.Brick;
import entities.barrier.item.BombItem;
import entities.barrier.item.FlameItem;
import entities.barrier.item.SpeedItem;
import graphics.Sprite;

public class Map {
	protected int width = Game.WIDTH_TILE, height = Game.HEIGHT_TILE;
	protected int level = 1;
	protected ControlPanel controlPanel;

	public static char[][] map;

	public Map(ControlPanel controlPanel, int level){
		this.controlPanel = controlPanel;
		loadLevel(level);
	}

	public void loadLevel(int level) {
		try {
        	BufferedReader br = new BufferedReader(new FileReader("res\\levels\\level" + level + ".txt"));
			String data = br.readLine();

			this.level = Integer.parseInt(data);
            map = new char[height][width];
            for (int i = 0; i < height; i++) {
                data = br.readLine();

                for (int j = 0; j < width; j++) {
                    map[i][j] = data.charAt(j);
                }
            }

		} catch (IOException e) {
			System.out.println("Error loading level ");
			e.printStackTrace();
		}
                
	}

	public void createEntities() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char newEntity = map[y][x];
				int position = x + y * width;
				switch (newEntity) {
					case '#':
						controlPanel.addEntity(position, new Wall(x , y , Sprite.wall));
						break;
					case '*':
						controlPanel.addEntity(position, new StackedEntity(x , y , new Grass(x , y , Sprite.grass) , new Brick(x , y , Sprite.brick)));
						break;
					case 'p':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Bomber(tileToPixel (x) , tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					case 'f':
						controlPanel.addEntity(position, new StackedEntity(x , y , new Grass(x , y , Sprite.grass) ,
								new FlameItem(x, y, Sprite.powerup_flames) , new Brick(x , y , Sprite.brick)));
						break;
					case 'b':
						controlPanel.addEntity(position, new StackedEntity(x , y , new Grass(x , y , Sprite.grass) ,
								new BombItem(x , y, Sprite.powerup_bombs) , new Brick(x , y , Sprite.brick)));
						break;
					case 's':
						controlPanel.addEntity(position, new StackedEntity(x , y , new Grass(x , y , Sprite.grass) ,
								new SpeedItem(x , y, Sprite.powerup_speed) , new Brick(x , y , Sprite.brick)));
						break;
					case 'g':
						controlPanel.addEntity(position, new StackedEntity(x , y , new Grass(x , y , Sprite.grass) ,
								new Portal(x , y , controlPanel, Sprite.portal) , new Brick(x , y , Sprite.brick)));
						break;
					//Create enemy
					case '1':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Balloon(tileToPixel (x) ,
								tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					case '2':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Oneal(tileToPixel (x) ,
								tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					case '3':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Doll(tileToPixel (x) ,
								tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					case '4':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Minvo(tileToPixel (x) ,
								tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					case '5':
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						controlPanel.addCharacter (new Kondoria(tileToPixel (x) ,
								tileToPixel (y) + Game.DEFAULT_SIZE , controlPanel));
						break;
					default:
						controlPanel.addEntity(position, new Grass(x , y , Sprite.grass));
						break;
				}
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

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
