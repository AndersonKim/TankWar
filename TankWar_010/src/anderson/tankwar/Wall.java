package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 墙体类,坦克以及子弹无法穿越
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class Wall {
	//墙的位置以及宽度以及厚度
	int x,y,w,h;
	//持有的管理器的引用
	TankClient tc;

	public Wall(int x, int y, int w, int h, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	/**
	 * 绘制墙体
	 * @param g
	 */
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	/**
	 * 碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
}
