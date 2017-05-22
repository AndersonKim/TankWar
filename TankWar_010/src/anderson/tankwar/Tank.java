package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}
	public void currentPos(){
		System.out.println(x+":"+y);
	}
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()){
		case 38:
			y-=5;
			break;
		case 40:
			y+=5;
			break;
		case 37:
			x-=5;
			break;
		case 39:
			x+=5;
			break;
		}
		currentPos();
	}

}
