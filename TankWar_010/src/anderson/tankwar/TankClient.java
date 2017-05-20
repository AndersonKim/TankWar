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

@SuppressWarnings("serial")
public class TankClient extends Frame {

	public static final int WIDTH = 800;
	public static final int HIGHT = 600;
	int x = 50, y = 50;
	Image offScreenImage = null;

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
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
		// TODO 自动生成的方法存根
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
public void currentPos(){
	System.out.println(x+":"+y);
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
	 * @author lenovo
	 *
	 */
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			switch (arg0.getKeyCode()){
				case 38:
					y-=5;
					break;
				case 40:
					y+=5;
					break;
				case 37:
					x-=5;
					break;
				case 39:
					x+=5;
					break;
			}
			currentPos();
		}
		
	}

}
