import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	
	private BufferedImage level = null;

	private int frames;
	public Game() {
		new Window(1000, 563, "Game", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0, 0);
		
		this.addKeyListener(new KeyInput(handler));
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png");
		
		loadLevel(level);

	}
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
			    delta--;
			   }
			   render();
			   frames++;
			   if(System.currentTimeMillis() - timer > 1000) {   
			    timer += 1000;
			    //System.out.println(frames);
			    frames = 0;
			   }
			  }
			  stop();
		}
	
	private void tick() {
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
			}
		}
		handler.tick();

	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g.fillRect(0, 0, 1000, 563);
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		handler.render(g);
		
		g2d.translate(camera.getX(), camera.getY());

		g.dispose();
		bs.show();
		

	}
	
	//loading level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
			int pixel = image.getRGB(xx, yy);
			int red = (pixel >> 16) & 0xff;
			int green = (pixel >> 8) & 0xff;
			int blue = (pixel) & 0xff;
			
			if(red == 255) handler.addObject(new Block(xx*32, yy*32, ID.Block));
			if(blue == 255) handler.addObject(new Player(xx*32, yy*32, ID.Player, handler));
			if(green == 255) handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler));
			
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		new Game();
	}

	

}
