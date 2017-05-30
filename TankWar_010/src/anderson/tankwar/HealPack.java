package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 血包类，我方坦克吃了可以回满血
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class HealPack {
	//血包的位置以及宽度以及厚度
	int x,y,w,h;
	//管理器的引用
	TankClient tc;
	//血包的可用性
	boolean live=true;
	//明确血包运动轨迹，使用二维数组构成
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

	//血包的绘制步骤
	int step=0;
	/**
	 * 移动血包
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
	 * 碰撞检测
	 * @return
	 */
	public Rectangle getRec(){
		return new Rectangle(x,y,w,h);
	}
	/**
	 * 绘制血包
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
