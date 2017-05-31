package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
/**
 * 坦克类，移动以及攻击
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class Tank {
	//坦克的xy轴的分速度
	static final float XSPEED = 5;
	static final float YSPEED = 5;
	//坦克的尺寸
	static final int WIDTH = 30;
	static final int HEIGHT = 30;
	//坦克的位置
	private int x;
	private int y;
	//坦克上一步的位置
	int oldX;
	int oldY;
	//坦克的生命值
	int life=100;
	//坦克方向
	private boolean bL=false;
	private boolean bR=false;
	private boolean bU=false;
	private boolean bD=false;
	//坦克阵营
	private boolean good=false;
	//坦克步骤
	int step=0;
	//枚举方向
	public enum Direction{UL,U,UR,L,STOP,R,DL,D,DR};
	//坦克移动方向
	private Direction dir=Direction.STOP;
	//炮筒的方向
	private Direction ptDir=Direction.D;
	//控制器引用
	private TankClient tc;
	//坦克生死
	private boolean live=true;
	//随机数生成器：控制敌方坦克转弯，开炮
	private Random r=new Random();
	//血包
	private HealthBar hb=new HealthBar();
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}



	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.good=good;
	}
	public Tank(int x, int y ,boolean good,TankClient tc) {
		this.x = x;
		this.y = y;
		this.good=good;
		this.tc=tc;
	}
	public Tank(int x, int y ,boolean good,Direction dir,TankClient tc) {
		this.x = x;
		this.y = y;
		this.good=good;
		this.dir=dir;
		this.tc=tc;
	}
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
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 绘制坦克
	 * @param g
	 */
	public void draw(Graphics g){
		//死亡的坦克移除绘制序列
		if(!live) {
			if(!good){
				tc.tanks.remove(this);
				return;
			}
			return;
		}
		//敌人坦克与自己坦克不同颜色
		Color c = g.getColor();
		if(good){
			g.setColor(Color.RED);	
		}else{
			g.setColor(Color.GREEN);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		//绘制炮筒
		drawCanon(ptDir,g);
		//移动
		move();
		//己方坦克则绘制血条
		if(good) 
			hb.draw(g);
	}
	/**
	 * 绘制炮筒
	 * @param ptDir
	 * @param g
	 */
	private void drawCanon(Direction ptDir,Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		//根据炮筒方向绘制炮筒
		switch (ptDir) {
		case U:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x+WIDTH/2, y);
			break;
		case D:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x+WIDTH/2, y+HEIGHT);
			break;
		case L:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x, y+HEIGHT/2);
			break;
		case UL:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x, y);
			break;
		case UR:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x+WIDTH, y);
			break;
		case R:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			break;
		case DL:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x, y+HEIGHT);
			break;
		case DR:
			g.drawLine(x+WIDTH/2,y+HEIGHT/2, x+WIDTH, y+HEIGHT);
			break;			
		}

	}
	/**
	 * 监测框架
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	/**
	 * 坦克移动
	 */
	private void move(){
		//移动前记录上一步位置
		this.oldX=x;
		this.oldY=y;
		//根据位置移动
		switch (dir) {
		case U:
			y-=XSPEED;
			break;
		case D:
			y+=XSPEED;
			break;
		case L:
			x-=XSPEED;
			break;
		case UL:
			x-=Math.sqrt(2)*XSPEED;
			y-=Math.sqrt(2)*YSPEED;
			break;
		case UR:
			x+=Math.sqrt(2)*XSPEED;
			y-=Math.sqrt(2)*YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case DL:
			x-=Math.sqrt(2)*XSPEED;
			y+=Math.sqrt(2)*YSPEED;
			break;
		case DR:
			x+=Math.sqrt(2)*XSPEED;
			y+=Math.sqrt(2)*YSPEED;
			break;			
		case STOP:
			break;
		}
		//炮筒与移动方向相同
		if(this.dir!=Direction.STOP){
			this.ptDir=this.dir;
		}
//边界检测防止移动出边界
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.WIDTH) x=TankClient.WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.HIGHT) y=TankClient.HIGHT-Tank.HEIGHT;
//敌方坦克的自主移动与开炮
		if(!good){

			if(step>4){
				Direction[] dirs=Direction.values();
				int rint=r.nextInt(dirs.length);
				dir=dirs[rint];
				step=0;
			}else{
				step++;	
			}

			if(r.nextInt(40)>=38) this.fireMissile();
		}
	}
	/**
	 * 当前位置
	 */
	public void currentPos(){
		System.out.println(System.currentTimeMillis()+" to: "+dir+" at:"+x+":"+y);
	}
	/**
	 * 控制触发器
	 * @param e
	 */
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		}
		locateDirection();
		//currentPos();
	}
	/**
	 * 确定方向
	 */
	private void locateDirection(){
		if(bL&&!bU&&!bR&&!bD) dir=Direction.L;
		else if(!bL&&!bU&&bR&&!bD) dir=Direction.R;
		else if(!bL&&!bU&&!bR&&bD) dir=Direction.D;
		else if(!bL&&bU&&!bR&&!bD) dir=Direction.U;
		else if(bL&&bU&&!bR&&!bD) dir=Direction.UL;
		else if(!bL&&bU&&bR&&!bD) dir=Direction.UR;
		else if(bL&&!bU&&!bR&&bD) dir=Direction.DL;
		else if(!bL&&!bU&&bR&&bD) dir=Direction.DR;
		else if(!bL&&!bU&&!bR&&!bD) dir=Direction.STOP;

	}
	/**
	 * 释放按键触发器
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_F:
			fireMissile();
			break;
		case KeyEvent.VK_A:
			fireSuper();
			break;
		case KeyEvent.VK_F2:
			if(!this.isLive()){
				this.live=true;
				this.life=100;
			}
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		}
		locateDirection();
	}
	/**
	 * 发射导弹
	 * @return
	 */
	public Missile fireMissile(){
		if(!live) return null;
		Missile m=new Missile(
				x+Tank.WIDTH/2-Missile.BULLET_RADIUS/2,
				y+Tank.HEIGHT/2-Missile.BULLET_RADIUS/2,
				good,
				ptDir,
				tc);
		tc.missiles.add(m);
		return m;
	}
	public Missile fireMissile(Direction _dir){
		if(!live) return null;
		Missile m=new Missile(
				x+Tank.WIDTH/2-Missile.BULLET_RADIUS/2,
				y+Tank.HEIGHT/2-Missile.BULLET_RADIUS/2,
				good,
				_dir,
				tc);
		tc.missiles.add(m);
		return m;
	}
	/**
	 * 发射超级导弹
	 */
	public void fireSuper(){
		Direction[] dirs=Direction.values();
		for(int i=0;i<dirs.length;i++){
			if(dirs[i]!=Direction.STOP)
				fireMissile(dirs[i]);
		}
	}
	/**
	 * 呆在原地
	 */
	public void stay(){
		x=oldX;
		y=oldY;
	}
	/**
	 * 吃血包
	 * @param hp
	 * @return
	 */
	public boolean eat(HealPack hp){
		if(isLive()&&good&&this.getRect().intersects(hp.getRec())){
			setLife(100);
			hp.live=false;
			return true;
		}
		return false;
	}
	/**
	 * 与其他坦克相撞
	 * @param tc
	 * @return
	 */
	public boolean collidesWithTank(TankClient tc){
		for(Tank tank:tc.tanks){
			if(this!=tank)
				if(this.isLive()&&tank.isLive())
					if(this.getRect().intersects(tank.getRect())){
						stay();
						return true;
					}

		}
		return false;
	}
	/**
	 * 与墙相撞
	 * @param wall
	 * @return
	 */
	public boolean collidesWithWall(Wall wall){
		if(this.isLive()&&this.getRect().intersects(wall.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	/**
	 * 内置类的血包
	 * @author 	AndersonKim
	 * @mail	pgytao@outlook.com
	 */
	private class HealthBar{
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.CYAN);
			g.drawRect(x, y-10, Tank.WIDTH, 10);
			g.setColor(Color.RED);
			int _width=WIDTH*life/100;
			g.fillRect(x, y-10, _width, 10);

		}
	}
}
