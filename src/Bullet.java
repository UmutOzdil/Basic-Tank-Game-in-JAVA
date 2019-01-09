import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject {
	public Player player;
	public Enemy enemy;
	private Handler handler;
	
	public Bullet(int x, int y, ID id, Handler handler, Player player, Enemy enemy) {
		super(x, y, id);
		this.enemy = enemy;
		this.player = player;
		this.handler = handler;
		if(player != null) {		
		switch(player.tankyon) {
		case 0:
			velY = -10;
			break;
		case 1:
			velY = +10;
			break;
		case 2:
			velX = -10;
			break;
		case 3:
			velX = +10;
			break;
		}
		}
		if(enemy != null) {
			switch(enemy.tankyon) {
			case 0:
				velY = -10;
				break;
			case 1:
				velY = +10;
				break;
			case 2:
				velX = -10;
				break;
			case 3:
				velX = +10;
				break;
			}
		}
	
		
	}

	@Override
	public void tick() {	
		x += velX;
		y += velY;
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
				}
			}
			if(tempObject.getId() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) {
					player.hp -= 50;
					handler.removeObject(this);
			}
			}
			
			
		}
		}

	@Override
	public void render(Graphics g) {	
		g.setColor(Color.black);
		g.fillOval(x, y, 10, 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 10, 10);
	}

}
