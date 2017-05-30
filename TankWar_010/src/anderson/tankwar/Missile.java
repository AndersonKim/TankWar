package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * �����࣬��������̹��
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
public class Missile {
	//�����İ뾶
	static final int BULLET_RADIUS = 10;
	//������XY����ķ��ٶ�
	static final float XSPEED = 10;
	static final float YSPEED = 10;
	//������λ��
	private int x;
	private int y;
	//�����ĵ�������
	private boolean good;


	//������������
	private TankClient tc;
	//�Ƿ��Ǽ���ĵ���
	private boolean live = true;
	//�����ķ���
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
	 * ���Ƶ���
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
 * �����ƶ�
 */
	private void move() {
		//���з���ı���
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
		//�ƶ����ж�����Ϸ�����
		checkPos();
	}
/**
 * �鿴�����Ƿ�����Ϸ����
 */
	private void checkPos() {
		if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HIGHT) {
			setLive(false);
		}

	}


/**
 * ����̹��
 * @param tank
 * @return
 */
	public boolean hitTank(Tank tank) {
		boolean hit = false;
		//����ĵ����������뷢����������ͬ��Ӫ
		if (tank.isLive()&&this.good!=tank.isGood()) {
			//������ײ��̹��
			if (this.getRect().intersects(tank.getRect())) {
				//����̹�˿�Ѫ
				if(tank.isGood()){
					tank.setLife(tank.getLife()-20);
					if(tank.getLife()<=0){
						tank.setLive(false);
					}
				}else{
					//�з�̹��ֱ�ӻ�ɱ
					tank.setLive(false);
				}
				//���б��
				hit = true;
				//��������̬
				this.live = false;
				//������ը
				Explode e=new Explode(x, y, tc);
				//��ӵ���ը�б����ڻ���
				tc.explodes.add(e);
			}
		}

		return hit;
	}

/**
 * ����̹�˼���
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
 * ��ײǽ��
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
 * ��ײ���
 * @return
 */
	public Rectangle getRect(){
		return new Rectangle(x,y,BULLET_RADIUS,BULLET_RADIUS);
	}
}
