package anderson.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	static final float XSPEED = 5;
	static final float YSPEED = 5;
	private int x;
	private int y;
	private boolean bL=false;
	private boolean bR=false;
	private boolean bU=false;
	private boolean bD=false;
	private enum Direction{UL,U,UR,L,STOP,R,DL,D,DR};
	private Direction dir=Direction.STOP;


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
		public Tank(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.fillOval(x, y, 30, 30);
			g.setColor(c);
			move();
		}

		private void move(){
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
				x-=Math.sqrt(XSPEED);
				y-=Math.sqrt(YSPEED);
				break;
			case UR:
				x+=Math.sqrt(XSPEED);
				y-=Math.sqrt(YSPEED);
				break;
			case R:
				x+=XSPEED;
				break;
			case DL:
				x-=Math.sqrt(XSPEED);
				y+=Math.sqrt(YSPEED);
				break;
			case DR:
				x+=Math.sqrt(XSPEED);
				y+=Math.sqrt(YSPEED);
				break;			
			case STOP:
				break;
			}
		}

		public void currentPos(){
			System.out.println(System.currentTimeMillis()+" to: "+dir+" at:"+x+":"+y);
		}
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
			currentPos();
		}
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
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()){
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
}