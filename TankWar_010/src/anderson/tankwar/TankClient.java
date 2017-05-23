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

@SuppressWarnings("serial")
public class TankClient extends Frame {

	public static final int WIDTH = 500;
	public static final int HIGHT = 500;
	public Tank myTank = new Tank(50, 50, true, this);
	public List<Missile> missiles = new ArrayList<Missile>();
	public List<Explode> explodes = new ArrayList<Explode>();
	public List<Tank> tanks = new ArrayList<Tank>();
	Image offScreenImage = null;

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("missiles count : " + missiles.size(), 10, 50);
		g.drawString("explodes count : " + explodes.size(), 10, 70);
		g.drawString("tanks count : " + tanks.size(), 10, 90);
		g.drawString("tank pos : " + myTank.getX() + ":" + myTank.getY(), 10, 110);
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			missile.hitTanks(tanks);
			missile.hitTank(myTank);
			missile.draw(g);
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			Explode e=explodes.get(i);
			e.draw(g);
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			t.draw(g);
		}
		
		myTank.draw(g);
		

	}

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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();

	}

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

	private class PaintThread implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					repaint();
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}

	/**
	 * listen key press action and tank move
	 * 
	 * @author lenovo
	 *
	 */
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent arg0) {
			myTank.keyReleased(arg0);
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			myTank.keyPressed(arg0);
		}

	}

}
