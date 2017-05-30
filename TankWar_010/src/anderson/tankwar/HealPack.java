package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Ѫ���࣬�ҷ�̹�˳��˿��Ի���Ѫ
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class HealPack {
	//Ѫ����λ���Լ�����Լ����
	int x,y,w,h;
	//������������
	TankClient tc;
	//Ѫ���Ŀ�����
	boolean live=true;
	//��ȷѪ���˶��켣��ʹ�ö�ά���鹹��
	private int[][] path={
			{350,300},{360,310},{375,320},{375,315},{350,350},
			{360,310},{360,300},{355,321},{351,315},{353,300}
	};





	public HealPack(TankClient tc) {
		x=path[0][0];
		y=path[0][1];
		w=h=15;
		this.tc=tc;
	}

	//Ѫ���Ļ��Ʋ���
	int step=0;
	/**
	 * �ƶ�Ѫ��
	 */
	private void move(){
		step++;
		if(step==path.length){
			step=0;
		}
		x=path[step][0];
		y=path[step][1];
	}
	/**
	 * ��ײ���
	 * @return
	 */
	public Rectangle getRec(){
		return new Rectangle(x,y,w,h);
	}
	/**
	 * ����Ѫ��
	 * @param g
	 */
	public void draw(Graphics g){
		if(live){
			Color c=g.getColor();
			g.setColor(Color.CYAN);
			g.drawRect(x, y, w, h);
			g.setColor(c);
			move();
		}

	}
}
