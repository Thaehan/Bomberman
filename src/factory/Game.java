package factory;

import graphics.Renderer;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;


public class Game extends Canvas {
	public static final int DEFAULT_SIZE = 16, WIDTH_TILE = 20, HEIGHT_TILE = 15;
	public static int WIDTH = DEFAULT_SIZE * WIDTH_TILE;
	public static int HEIGHT = DEFAULT_SIZE * HEIGHT_TILE;

	protected static int bombs = 1;
	protected static int power = 1;
	protected static double speed = 1.0;
	protected static int lives = 1;

	protected int presentDelayTransition = 3;

	private Controller input;
        //nút pause
	private boolean isRunning = false;
	private boolean isPaused = true;
	
        
	private ControlPanel controlPanel;
	private Renderer renderer;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(GameJFrame frame) {
		renderer = new Renderer(WIDTH, HEIGHT);
		input = new Controller();
		controlPanel = new ControlPanel(this, input , renderer);
		addKeyListener(input);
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		renderer.clear();
		controlPanel.render(renderer);
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
		
		controlPanel.renderScreen(g);
		g.dispose();
		bs.show();
	}

	private void update() {
		input.update();
		controlPanel.update();
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
					controlPanel.setShow(-1);
					isPaused = false;
				}
				renderScreen();
			}
			else {
				renderGame();

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (controlPanel.getShow() == 2) {
					--presentDelayTransition;
				}
			}

		}
	}
        
	public ControlPanel getBoard() {
		return controlPanel;
	}
        
	public static double getSpeed() {
		return speed;
	}
	
	public static int getBombs() {
		return bombs;
	}
	
	public static int getPower() {
		return power;
	}

	public static void addBomberSpeed(double i) {
		speed += i;
	}
	
	public static void addBombRadius(int i) {
		power += i;
	}
	
	public static void addBombRate(int i) {
		bombs += i;
	}
        
	public void resetScreenDelay() {
		presentDelayTransition = 3;
	}

	public boolean isPaused() {
		return isPaused;
	}
	
	public void pause() {
		isPaused = true;
	}

	public static void resetPower(){
		speed = 1.0;
		power = 1;
		bombs = 1;
	}
}
