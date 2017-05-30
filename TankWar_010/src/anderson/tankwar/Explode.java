package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ��ը�࣬��̹���ܵ�����ʱ����
 * @author lenovo
 *
 */
public class Explode {
	//��ը����
	int x,y;
	//�Ƿ��ǻ�ı�ը�������Ƿ����
	boolean live=true;
	//�趨��ը�Ļ��ư뾶
	int[] diameter={4,7,12,18,28,32,49,30,14,6};
	//ȷ����ǰ���е��ڼ����Ļ���
	int step=0;
	//���еĹ�����������
	TankClient tc;


	public Explode(int x, int y,TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc=tc;
	}



	/**
	 * ���Ʊ�ը
	 * @param g 
	 */
	public void draw(Graphics g){
		//ȥ���Ѿ���ȥ�ı�ը
		if(!live) {
			tc.explodes.remove(this);
			return;	
		}
		//����ը���е����һ��ʱ����ը��������������
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		//ȡɫ�ݴ沢�һ��Ʊ�ը�����е���һ���뾶
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x,y,diameter[step],diameter[step]);
		g.setColor(c);
		step++;
	}
}
