import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

	int tankyon = 0;
	int speed = 3;
	int hp = 200;
	Handler handler;
	BufferedImageLoader loader = new BufferedImageLoader();
	private BufferedImage tank = loader.loadImage("/tanky.png");
	private BufferedImage tank1 = loader.loadImage("/tanky1.png");
	private BufferedImage tank2 = loader.loadImage("/tanky2.png");
	private BufferedImage tank3 = loader.loadImage("/tanky3.png");
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		if(handler.isShoot()) {
			for(int i =0; i < handler.object.size();i++) {
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId()==ID.Player) {
					handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bullet, handler, this, null));
				}
		}
		}
		//movement
		if(handler.isUp())
			{
			tankyon = 0;
			
			velY = -speed; }
			else if (!handler.isDown()) velY = 0;
	
		
		if(handler.isDown()) { 
			tankyon = 1;
			velY = speed;} else if (!handler.isUp()) velY = 0;
		if(handler.isLeft()) {
			tankyon = 2;
			velX = -speed; 
		}else if (!handler.isRight()) velX = 0;
		if(handler.isRight()) {
			tankyon = 3;
			velX = speed;} 
		else if (!handler.isLeft()) velX = 0;
}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x += velX * -1;
					y += velY * -1;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		switch(tankyon) {
		case 0:
			g.drawImage(tank1, x, y, 31, 56, null);
			break;
		case 1:
			g.drawImage(tank3, x, y, 31, 56, null);
			break;
		case 2:
			g.drawImage(tank, x, y, 56, 31, null);
			break;
		case 3:
			g.drawImage(tank2, x, y, 56, 31, null);
			break;
		}
		
		
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y,16 , 24);
	}
	
}
