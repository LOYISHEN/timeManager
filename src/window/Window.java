package window;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import timeManager.TimeManager;

public class Window extends JFrame {
	public Window(String title, TimeManager timeManager) {
		this.timeManager = timeManager;
		this.planEditWindow = new PlanEditWindow("Edit plan");
		this.timeManager.readFromFile();
		ImageIcon icon = new ImageIcon("icon.png");
		Image image = icon.getImage();
		this.setIconImage(image);
		
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon trayIcon = new TrayIcon(image, "TimeManager");
			trayIcon.setImageAutoSize(true);
			PopupMenu menu = new PopupMenu();
			MenuItem exitMenuItem = new MenuItem("exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("exit");
					exit();
				}
			});
			menu.add(exitMenuItem);
			trayIcon.setPopupMenu(menu);
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			trayIcon.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						setVisible(true);
					}
					if (e.getButton() == MouseEvent.BUTTON2) {
						Runnable popupWindowRunnable = new PopupWindow("", 3000);
						new Thread(popupWindowRunnable).start();
					}
				}
			});
		}
		
		this.setTitle(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
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
			int row = (i / 7) + 1;
			int column = (i % 7) + 1;
			planMap.put(row * 8 + column, this.timeManager.getPlan(i));
		}
		
		// 画组件
		for (int i=0; i<8*25; i++) {
			JButton button = new JButton(planMap.get(i));
			button.setName(String.valueOf(i));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					planPress(button.getName(), button);
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
	
	private void planPress(String name, JButton button) {
		int number = Integer.valueOf(name);
		int row = number / 8 - 1;
		int column = number % 8 - 1;
		if (row < 0 || column < 0) {
			System.out.println("not plan table");
			return;
		}
		int time = row * 7 + column;
		this.planEditWindow.setPlan(this.timeManager.getPlan(time));
		
		this.planEditWindow.setVisible(true);
		
		// 写完计划后就获取计划
		String newPlan =  this.planEditWindow.getPlan();
		this.timeManager.setPlan(time, newPlan);
		button.setText(this.timeManager.getPlan(time));
		// 保存计划
		timeManager.saveToFile();
	}
	
	private TimeManager timeManager;
	private PlanEditWindow planEditWindow;
}
