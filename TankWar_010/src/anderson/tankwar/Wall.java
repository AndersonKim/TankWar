package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * ǽ����,̹���Լ��ӵ��޷���Խ
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class Wall {
	//ǽ��λ���Լ�����Լ����
	int x,y,w,h;
	//���еĹ�����������
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
	 * ����ǽ��
	 * @param g
	 */
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	/**
	 * ��ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
}
