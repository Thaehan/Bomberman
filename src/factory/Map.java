package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entities.PositionHandling;
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
	protected int width, height;
	protected int level;
	protected Board board;

	public static char[][] map;

	public Map (Board board, int level){
		this.board = board;
		loadLevel(level);
	}

	public void loadLevel(int level) {
		try {
        	BufferedReader br = new BufferedReader(new FileReader("res\\levels\\level" + level + ".txt"));
			String data = br.readLine();

			this.level = Integer.parseInt(data.substring(0,1));
			height = Integer.parseInt(data.substring(2,4));
			width = Integer.parseInt(data.substring(5,7));

            map = new char[height][width];
            for (int i = 0; i < height; i++ ) {
                data = br.readLine();

                for (int j = 0; j < width; j++ ) {
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
				int newPosition = x + y * width;
				switch (newEntity) {
					case '#':
						board.addEntity (newPosition , new Wall (x , y , Sprite.wall));
						break;
					case '*':
						board.addEntity (newPosition , new StackedEntity (x , y , new Grass (x , y , Sprite.grass) , new Brick (x , y , Sprite.brick)));
						break;
					case 'p':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Bomber (PositionHandling.tileToPixel (x) , PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					case 'f':
						board.addEntity (newPosition , new StackedEntity (x , y , new Grass (x , y , Sprite.grass) ,
								new FlameItem (x , y , Sprite.powerup_flames) , new Brick(x , y , Sprite.brick)));
						break;
					case 'b':
						board.addEntity (newPosition , new StackedEntity (x , y , new Grass (x , y , Sprite.grass) ,
								new BombItem (x , y , Sprite.powerup_bombs) , new Brick(x , y , Sprite.brick)));
						break;
					case 's':
						board.addEntity (newPosition , new StackedEntity (x , y , new Grass (x , y , Sprite.grass) ,
								new SpeedItem (x , y , Sprite.powerup_speed) , new Brick(x , y , Sprite.brick)));
						break;
					case 'g':
						board.addEntity (newPosition , new StackedEntity (x , y , new Grass (x , y , Sprite.grass) ,
								new Portal (x , y , board , Sprite.portal) , new Brick(x , y , Sprite.brick)));
						break;
					//Create enemy
					case '1':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Balloon (PositionHandling.tileToPixel (x) ,
								PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					case '2':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Oneal (PositionHandling.tileToPixel (x) ,
								PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					case '3':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Doll (PositionHandling.tileToPixel (x) ,
								PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					case '4':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Minvo (PositionHandling.tileToPixel (x) ,
								PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					case '5':
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
						board.addCharacter (new Kondoria (PositionHandling.tileToPixel (x) ,
								PositionHandling.tileToPixel (y) + Game.DEFAULT_SIZE , board));
						break;
					default:
						board.addEntity (newPosition , new Grass (x , y , Sprite.grass));
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
}
