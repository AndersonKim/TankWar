/**
 * 
 */
package anderson.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import anderson.tankwar.Tank.Direction;
/**
 * ��Ϸ������
 * @author 	AndersonKim
 * @mail	pgytao@outlook.com
 */
@SuppressWarnings("serial")
public class TankClient extends Frame {
	//���ڵĿ����߶�
	public static final int WIDTH = 500;
	public static final int HIGHT = 500;
	//����̹��
	public Tank myTank = new Tank(50, 50, true, this);
	//���������б�
	public List<Missile> missiles = new ArrayList<Missile>();
	//��ը�����б�
	public List<Explode> explodes = new ArrayList<Explode>();
	//̹�˻����б�
	public List<Tank> tanks = new ArrayList<Tank>();
	//ǽ��
	Wall wall=new Wall(400,400,10,100,this);
	//Ѫ��
	HealPack hp=new HealPack(this);
	//����ͼƬ
	Image offScreenImage = null;

	/**
	 * �������
	 */
	@Override
	public void paint(Graphics g) {
		/**
		 * ָ����Ϸ����Ҫ����
		 * ��������
		 * ̹������
		 * ��ը����
		 * ����ֵ
		 * ̹��λ��
		 */
		Color c=g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("missiles count : " + missiles.size(), 10, 50);
		g.drawString("explodes count : " + explodes.size(), 10, 70);
		g.drawString("enemy tanks count : " + tanks.size(), 10, 90);
		g.drawString("life count : " + myTank.getLife(), 10, 110);
		g.drawString("tank pos : " + myTank.getX() + ":" + myTank.getY(), 10, 130);
		g.setColor(c);
		//���Ƶ���
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			missile.hitTanks(tanks);
			missile.hitTank(myTank);
			missile.draw(g);
			missile.hitWall(wall);
		}
		//���Ʊ�ը
		for (int i = 0; i < explodes.size(); i++) {
			Explode e=explodes.get(i);
			e.draw(g);
		}
		//����̹��ѭ��
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			//̹�˻���
			t.draw(g);
			//̹��֮�����ײ
			t.collidesWithTank(this);
			//̹����ǽ�ڵ���ײ
			t.collidesWithWall(wall);
		}
		//����̹�˵Ļ���
		myTank.draw(g);
		//����̹�˵�ǽ����ײ
		myTank.collidesWithWall(wall);
		//����̹�˳�Ѫ��
		myTank.eat(hp);
		//Ѫ���Ļ���
		hp.draw(g);
		//ǽ�ڵĻ���
		wall.draw(g);

	}
	/**
	 * ���������ֹ���涶��
	 */
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(WIDTH, HIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, WIDTH, HIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * @param args ���в���
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc = new TankClient();
		tc.launchFrame();

	}
	/**
	 * ��ӵ���̹��
	 */
	public void addEnemyTank(){
		for(int i=0;i<10;i++){
			tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
		}
	}
	/**
	 * ��ʼ���������Լ�����
	 */
	private void launchFrame() {

		for(int i=0;i<10;i++){
			tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
		}


		this.setTitle("Tank War--Client");
		this.setLocation(WIDTH / 2, HIGHT / 2);
		this.setSize(WIDTH, HIGHT);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}

		});
		this.setResizable(false);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();

	}
	/**
	 * �����߳� 50����һˢ�� ������Ϸ�ٶ�
	 * @author 	AndersonKim
	 * @mail	pgytao@outlook.com
	 */
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					repaint();
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * ����������
	 * @author 	AndersonKim
	 * @mail	pgytao@outlook.com
	 */
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent arg0) {
			myTank.keyReleased(arg0);
			switch(arg0.getKeyCode()){
			case KeyEvent.VK_F1:
				addEnemyTank();
				break;

			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			myTank.keyPressed(arg0);
		}

	}

}
