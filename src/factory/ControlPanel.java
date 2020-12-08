package factory;

import entities.Entity;
import entities.bomb.Bomb;
import entities.bomb.FlameRange;
import entities.character.Bomber;
import entities.character.Character;
import graphics.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class ControlPanel {
	protected Map map;
	protected Game game;
	protected Controller controller;
	protected Renderer renderer;
	protected int lives = Game.lives;
	public Entity[] entities;
	public List<Character> characters = new ArrayList<>();
	protected List<Bomb> bombs = new ArrayList<>();

	private int presentScreen = 1; //1:endgame, 2:changelevel, 3:paused
        
	public ControlPanel(Game game, Controller input, Renderer renderer) {
		this.game = game;
		controller = input;
		this.renderer = renderer;
		loadLevel(1);
	}
	
	public void update() {
		if(game.isPaused()) return;
		
		updateEntities();
		updateCharacters();
		updateBombs();

		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(i).isRemoved()) {
				characters.remove(i);
			}
		}
	}

	public void render(Renderer renderer) {
		if(game.isPaused()) return;

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				entities[x + y * map.getWidth()].render(renderer);
			}
		}

		renderBombs(renderer);
		renderCharacter(renderer);
		
	}

	public void nextLevel() {
		map.setLevel(map.getLevel() + 1);
		loadLevel(map.getLevel());
	}

	public void newGame() {
		resetProperties();
		loadLevel(1);
	}

	public void restartLevel(){
		loadLevel(map.getLevel());
	}

	public void loadLevel(int level) {
		presentScreen = 2;
		game.resetScreenDelay();
		game.pause();
		characters.clear();
		bombs.clear();

		map = new Map(this, level);
		entities = new Entity[map.getHeight() * map.getWidth()];
		map.createEntities();
	}
	
	public void endGame() {
		presentScreen = 1;
		game.resetScreenDelay();
		game.pause();
		newGame();
		//
	}
        
	public boolean isEnemyCleared() {
		int total = 0;
		for (Character character : characters) {
			if (!(character instanceof Bomber))
				++total;
		}
		return total == 0;
	}
	
	public void renderScreen(Graphics g) {
		switch (presentScreen) {
			case 1:
				renderer.renderEnd(g);

				break;
			case 2:
				renderer.start(g, map);
				break;
		}
	}
	
	public Entity getEntity(double x, double y, Character m) {
		Entity res = null;
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return bombs;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}

	public Bomber getBomber() {
		Iterator<Character> itr = characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Bomber)
				return (Bomber) cur;
		}
		
		return null;
	}

	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = characters.iterator();
		Character cur;
		while (itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameRange getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			FlameRange e = b.flameAt(x, y);
			if (e != null) {
				return e;
			}
		}
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return entities[(int) x + (int) y * map.getWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		entities[pos] = e;
	}
	
	public void addCharacter(Character e) {
		characters.add(e);
	}
	
	public void addBomb(Bomb e) {
		bombs.add(e);
	}

	protected void renderCharacter(Renderer renderer) {
		Iterator<Character> itr = characters.iterator();
		while (itr.hasNext()) {
			itr.next().render(renderer);
		}
	}
	
	protected void renderBombs(Renderer renderer) {
		Iterator<Bomb> itr = bombs.iterator();
		while (itr.hasNext()) {
			itr.next().render(renderer);
		}
	}

	protected void updateEntities() {
		if(game.isPaused()) {
			return;
		}
		for (int i = 0; i < entities.length; i++) {
			entities[i].update();
		}
	}
	
	protected void updateCharacters() {
		if (game.isPaused()) {
			return;
		}
		Iterator<Character> itr = characters.iterator();
		
		while(itr.hasNext() && !game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if (game.isPaused()) {
			return;
		}
		Iterator<Bomb> itr = bombs.iterator();
		
		while (itr.hasNext()) {
			itr.next().update();
		}
	}

	public Controller getInput() {
		return controller;
	}
        
	public Game getGame() {
		return game;
	}

	public int getShow() {
		return presentScreen;
	}

	public void setShow(int i) {
		presentScreen = i;
	}

	public void resetProperties() {
		lives = Game.lives;
		Bomber.powerUp.clear();
		Game.speed = 1.0;
		game.power = 1;
		game.bombs = 1;
		
	}

    public int getLives() {
        return lives;
    }

    public void addLives(int i) {
		this.lives += i;
	}
   
}
