package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * 导弹类，攻击其他坦克
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class Missile {
	//导弹的半径
	static final int BULLET_RADIUS = 10;
	//导弹的XY轴向的分速度
	static final float XSPEED = 10;
	static final float YSPEED = 10;
	//导弹的位置
	private int x;
	private int y;
	//导弹的敌我属性
	private boolean good;


	//控制器的引用
	private TankClient tc;
	//是否是激活的导弹
	private boolean live = true;
	//导弹的方向
	Tank.Direction dir;
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
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
	/**
	 * 绘制导弹
	 * @param g
	 */
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
/**
 * 导弹移动
 */
	private void move() {
		//所有方向的遍历
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
		//移动后判断在游戏区域否
		checkPos();
	}
/**
 * 查看导弹是否在游戏区域
 */
	private void checkPos() {
		if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HIGHT) {
			setLive(false);
		}

	}


/**
 * 击中坦克
 * @param tank
 * @return
 */
	public boolean hitTank(Tank tank) {
		boolean hit = false;
		//激活的导弹，导弹与发射者所属不同阵营
		if (tank.isLive()&&this.good!=tank.isGood()) {
			//导弹碰撞到坦克
			if (this.getRect().intersects(tank.getRect())) {
				//己方坦克扣血
				if(tank.isGood()){
					tank.setLife(tank.getLife()-20);
					if(tank.getLife()<=0){
						tank.setLive(false);
					}
				}else{
					//敌方坦克直接击杀
					tank.setLive(false);
				}
				//击中标记
				hit = true;
				//消除激活态
				this.live = false;
				//创建爆炸
				Explode e=new Explode(x, y, tc);
				//添加到爆炸列表用于绘制
				tc.explodes.add(e);
			}
		}

		return hit;
	}

/**
 * 击中坦克集合
 * @param tanks
 * @return
 */
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
/**
 * 碰撞墙壁
 * @param w
 * @return
 */
	public boolean hitWall(Wall w){
		if(this.getRect().intersects(w.getRect())&&this.isLive()){
			this.setLive(false);
			return true;	
		}
		return false;
	}
/**
 * 碰撞检测
 * @return
 */
	public Rectangle getRect(){
		return new Rectangle(x,y,BULLET_RADIUS,BULLET_RADIUS);
	}
}
