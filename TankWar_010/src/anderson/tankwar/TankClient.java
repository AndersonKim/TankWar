/**
 * 
 */
package anderson.tankwar;

import javax.swing.JFrame;
/**
 * 
 * @author lenovo
 *
 */
@SuppressWarnings("serial")
public class TankClient extends JFrame{
   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc=new TankClient();
		tc.launchFrame();
	}

	private void launchFrame() {
		// TODO �Զ����ɵķ������
		this.setTitle("Tank War--Client");
		this.setLocation(400,300);
		this.setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
	}

}
