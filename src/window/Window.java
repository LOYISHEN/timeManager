package window;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import timeManager.TimeManager;

public class Window extends JFrame {
	public Window(String title, TimeManager timeManager) {
		this.timeManager = timeManager;
		this.timeManager.readFromFile();
		
		this.setTitle(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.setMinimumSize(new Dimension(1350, 750));
		
		this.paintContent();
		
		GUITools.center(this);
		
		this.setVisible(true);
	}
	
	private void paintContent() {
		this.setIconImage(new ImageIcon("image/icon.png").getImage());
		
		this.setLayout(new GridLayout(25, 8, 5, 5));
		
		Map<Integer, String> planMap = new HashMap<Integer, String>();
		// 星期几
		planMap.put(0, "");
		planMap.put(1, "星期天");
		planMap.put(2, "星期一");
		planMap.put(3, "星期二");
		planMap.put(4, "星期三");
		planMap.put(5, "星期四");
		planMap.put(6, "星期五");
		planMap.put(7, "星期六");
		
		// 时间
		for (int i=0; i<24; i++) {
			planMap.put((i+1)*8, String.valueOf(i));
		}
		
		// 计划
		for (int i=0; i<7*24; i++) {
			planMap.put(i * 8 + 9 + (i/8), this.timeManager.getPlan(i));
		}
		
		// 画组件
		for (int i=0; i<8*25; i++) {
			JButton button = new JButton(planMap.get(i));
			button.setName(String.valueOf(i));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(button.getName());
				}
			});
			this.add(button);
		}
	}
	
	private void exit() {
		this.timeManager.saveToFile();
		this.dispose();
		System.exit(0);
	}
	
	private TimeManager timeManager;
}
