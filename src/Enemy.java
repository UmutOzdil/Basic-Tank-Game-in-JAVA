import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {
	private Handler handler;
	int tankyon = 0;
	BufferedImageLoader loader = new BufferedImageLoader();
	private BufferedImage tank = loader.loadImage("/enemy.png");
	private BufferedImage tank1 = loader.loadImage("/enemy1.png");
	private BufferedImage tank2 = loader.loadImage("/enemy2.png");
	private BufferedImage tank3 = loader.loadImage("/enemy3.png");
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	int random = 0;
	int speed = 2;
	
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		choose = r.nextInt(100);
		
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Player) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					x += (velX*5) * -1;
					y += (velY*5) * -1;
					velX *= -1;
					velY *= -1;
				}else if(choose == 0) {
					tankyon = 0;
					velX = speed;
					velY = 0;
				}else if(choose == 1) {
					tankyon = 1;
					velY = speed;
					velX = 0;
				}else if(choose == 2) {
					tankyon = 2;
					velX = -speed;
					velY = 0;
				}else if(choose == 3) {
					tankyon = 3;
					velY = -speed;
					velX = 0;
				
			}
			}
			if(choose == 4) {
				random = r.nextInt(100);
				if(random < 50) {
					if(tempObject.getId()==ID.Enemy) {
						handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bllt, handler, null, this));
					}
				
				}
				
		}	
			
			
	
	
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					hp -= 50;
					handler.removeObject(tempObject);
			}
			}
		}
			if(hp <= 0) handler.removeObject(this);
			
			
			
	}
			
		
	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		switch(tankyon) {
		case 0:
			g.drawImage(tank, x, y, 56, 31, null);
			break;
		case 1:
			g.drawImage(tank3, x, y, 31, 56, null);
			break;
		case 2:
			g.drawImage(tank1, x, y, 56, 31, null);
			break;
		case 3:
			g.drawImage(tank2, x, y, 31, 56, null);
			break;
		}
		
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle(x-16, y-16, 64, 64);
	}

}
