package factory;

import graphics.Renderer;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;


public class Game extends Canvas {
	public static final int DEFAULT_SIZE = 16,
	WIDTH = DEFAULT_SIZE * 13,
	HEIGHT = 13 * DEFAULT_SIZE;
                         
	public static int WIDTHTile = 31;
	public static int HEIGHTTile = 14;
        
        // tỉ lệ ? 
	public static int SCALE = 3;
	public static final int limitedTime = 200;
	private static final int startBombs = 1;
	private static final int startPower = 1;
	private static final double startSpeed = 1.0;
	public static int startLives = 1;
	protected static int presentBombs = startBombs;
	protected static int presentPower = startPower;
	protected static double bomberSpeed = startSpeed;
	protected static int presentLives = startLives;

	protected static int delayTransition = 3;
	protected int presentDelayTransition = delayTransition;

	private Controller input;
        //nút pause
	private boolean isRunning = false;
	private boolean isPaused = true;
	
        
	private Board board;
	private Renderer renderer;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(GameJFrame frame) {
		renderer = new Renderer (WIDTH, HEIGHT);
		input = new Controller ();
		
		board = new Board(this, input , renderer);
		addKeyListener(input);
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		renderer.clear();
		board.render(renderer);
		System.arraycopy(renderer.pixels , 0, pixels, 0, pixels.length);
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		renderer.clear();
		Graphics g = bs.getDrawGraphics();
		
		board.renderScreen(g);
		g.dispose();
		bs.show();
	}

	private void update() {
		input.update();
		board.update();
	}

	//thời gian thực trong quá khư , vòng loop cũ
	// denta t tức là thời gian  vòng loob cũ .  đơn vị  s^2
	// denta =1 ; now -lastTime = ns  => thời gian của của 1 vòng loob = T chu kì chuẩn
	// để last time  cho vòng loob while sau tính
	// đo vòng while  loob lớn  trong bao lâu
	// nếu denta t >=1  thì thơi gian mỗi vòng loob sẽ = với T thời gian 1 vòng loob chuẩn
	
	public void start() {
		isRunning = true;
		long lastLoopTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second ( tần số f) vậy 1/ns là thời gian load 1 frame
		double delta = 0;
                
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime(); // thời gian bây h
			delta += (now - lastLoopTime) / ns;
			lastLoopTime = now;
                        
			while(delta >= 1) {
				update();
				delta--;
			}

			if (isPaused) {
				if (presentDelayTransition <= 0) {
					board.setShow(-1);
					isPaused = false;
				}
				renderScreen();
			}
			else {
				renderGame();

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (board.getShow() == 2) {
					--presentDelayTransition;
				}
			}

		}
	}
        
	public Board getBoard() {
		return board;
	}
        
	public static double getBomberSpeed() {
		return bomberSpeed;
	}
	
	public static int getPresentBombs () {
		return presentBombs;
	}
	
	public static int getPresentPower () {
		return presentPower;
	}

	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		presentPower += i;
	}
	
	public static void addBombRate(int i) {
		presentBombs += i;
	}
        
	public void resetScreenDelay() {
		presentDelayTransition = delayTransition;
	}

	public boolean isPaused() {
		return isPaused;
	}
	
	public void pause() {
		isPaused = true;
	}

	public static void resetPower(){
		bomberSpeed = startSpeed;
		presentPower = startPower;
		presentBombs = startBombs;
	}
}
