package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x,y;
	boolean live=true;
	int[] diameter={4,7,12,18,28,32,49,30,14,6};
	int step=0;
	TankClient tc;
	
	
	public Explode(int x, int y,TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc=tc;
	}



	public void draw(Graphics g){
		if(!live) return;
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x,y,diameter[step],diameter[step]);
		g.setColor(c);
		step++;
	}
}
