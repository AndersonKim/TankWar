package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 爆炸类，在坦克受到攻击时产生
 * @author lenovo
 *
 */
public class Explode {
	//爆炸坐标
	int x,y;
	//是否是活的爆炸，决定是否绘制
	boolean live=true;
	//设定爆炸的绘制半径
	int[] diameter={4,7,12,18,28,32,49,30,14,6};
	//确定当前进行到第几步的绘制
	int step=0;
	//持有的管理器的引用
	TankClient tc;


	public Explode(int x, int y,TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc=tc;
	}



	/**
	 * 绘制爆炸
	 * @param g 
	 */
	public void draw(Graphics g){
		//去除已经死去的爆炸
		if(!live) {
			tc.explodes.remove(this);
			return;	
		}
		//当爆炸进行到最后一步时，爆炸结束；计数归零
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		//取色暂存并且绘制爆炸，进行到下一步半径
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x,y,diameter[step],diameter[step]);
		g.setColor(c);
		step++;
	}
}
