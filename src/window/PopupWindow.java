package window;

import java.awt.Color;

import javax.swing.JDialog;

// 右下角弹窗
public class PopupWindow extends JDialog implements Runnable {
	public PopupWindow(String message, int delayTime) {
		this.message = message;
		this.delayTime = delayTime;
		this.setUndecorated(true);
		this.setSize(200, 200);
		
		
		
		GUITools.rightButtom(this);
		this.setVisible(true);
	}
	
	public void run() {
		for (int i=0; i<1000; i++) {
			try {
				Thread.sleep(1);
				this.setBackground(new Color(255, 255, 255, i*255/1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(this.delayTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i=1000; i>0; i--) {
			try {
				Thread.sleep(1);
				this.setBackground(new Color(255, 255, 255, i*255/1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
	}
	
	private String message;
	private int delayTime;
}
