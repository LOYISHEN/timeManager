package window;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JDialog;

// 右下角弹窗
public class PopupWindow extends JDialog implements Runnable {
	// delayTime单位是毫秒
	public PopupWindow(String message, int delayTime) {
		this.message = message;
		this.delayTime = delayTime;
		this.setUndecorated(true);
		this.setSize(200, 200);
		
		this.paintContent();
		
		GUITools.rightButtom(this);
		this.setVisible(true);
	}
	
	private void paintContent() {
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("宋体", Font.PLAIN, 40));
		textArea.setText(this.message);
		this.add(textArea);
		System.out.println(this.message);
	}
	
	public void run() {
		//这里要解决淡入淡出不能正确显示窗口内容的问题
		//GUITools.fadeIn(this, 500);
		
		// 开一个线程播放音效
		Runnable popupAlarmRunnable = new PopupAlarm("ding.wav");
		new Thread(popupAlarmRunnable).start();
		
		try {
			Thread.sleep(this.delayTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//GUITools.fadeOut(this, 500);
		
		this.dispose();
	}
	
	private String message;
	private int delayTime;
}
