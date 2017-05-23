package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Missile {
	static final int BULLET_RADIUS = 10;
	static final float XSPEED = 10;
	static final float YSPEED = 10;
	private int x;
	private int y;
	private boolean good;
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}


	private TankClient tc;
	private boolean live = true;
	Tank.Direction dir;

	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, Tank.Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
	}
	public Missile(int x, int y, boolean good,Tank.Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good=good;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.missiles.remove(this);
			return;
		}

		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(x, y, BULLET_RADIUS, BULLET_RADIUS);
		g.setColor(c);
		move();
	}

	private void move() {
		switch (dir) {
		case U:
			y -= XSPEED;
			break;
		case D:
			y += XSPEED;
			break;
		case L:
			x -= XSPEED;
			break;
		case UL:
			x -= Math.sqrt(2) * XSPEED;
			y -= Math.sqrt(2) * YSPEED;
			break;
		case UR:
			x += Math.sqrt(2) * XSPEED;
			y -= Math.sqrt(2) * YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case DL:
			x -= Math.sqrt(2) * XSPEED;
			y += Math.sqrt(2) * YSPEED;
			break;
		case DR:
			x += Math.sqrt(2) * XSPEED;
			y += Math.sqrt(2) * YSPEED;
			break;
		case STOP:
			break;
		}
		checkPos();
	}

	private void checkPos() {
		if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HIGHT) {
			setLive(false);
		}

	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean hitTank(Tank tank) {
		boolean hit = false;
		if (tank.isLive()&&this.good!=tank.isGood()) {
			if (x+BULLET_RADIUS/2 < tank.getX() + tank.WIDTH &&
					x+BULLET_RADIUS/2 > tank.getX() &&
					y+BULLET_RADIUS/2 < tank.getY() + tank.WIDTH &&
					y+BULLET_RADIUS/2 > tank.getY()) {
				hit = true;
				tank.setLive(false);
				this.live = false;
				
				Explode e=new Explode(x, y, tc);
				tc.explodes.add(e);
			}
		}

		return hit;
	}
	
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
}
