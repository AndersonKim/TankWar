package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	static final int BULLET_RADIUS = 10;
	static final float XSPEED = 10;
	static final float YSPEED = 10;
	private int x;
	private int y;
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
		if (tank.isLive()) {
			if (x < tank.getX() + tank.WIDTH && x > tank.getX() && y < tank.getY() + tank.WIDTH && y > tank.getY()) {
				hit = true;
				tank.setLive(false);
				this.live = false;
			}
		}

		return hit;
	}
}
