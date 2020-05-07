package window;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import com.sun.javaws.Main;
import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;
import timeManager.TimeManager;

public class Window extends JFrame {
	private TimeManager timeManager;
	private PlanEditWindow planEditWindow;

	private JPanel timePanel = null; // 时间panel
	private JPanel planPanel = null; // 计划panel

	// 右键选中计划时弹出来的菜单
	private JPopupMenu planOperationPopupMenu = new JPopupMenu();

	// 被右键选中的计划
	private Plan selectedPlan = null;

	// 窗口主容器
	private JPanel containerPanel = new JPanel(new GridBagLayout());

	// 最大的计划时间和最小的计划时间，用来确定区域大小
	private PlanTime maxPlanTime = new PlanTime(0, 0, 0);
	private PlanTime minPlanTime = new PlanTime(0, 23, 59);


	public Window(String title, TimeManager timeManager) {
		this.timeManager = timeManager;
		this.planEditWindow = new PlanEditWindow("Edit plan");
		this.timeManager.readFromFile();
		ImageIcon icon = new ImageIcon("icon.png");
		Image image = icon.getImage();
		this.setIconImage(icon.getImage());
		
		this.addSystemTray(image); // 系统托盘

		this.addMenu(); // 菜单
		
		this.setTitle(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		this.setMinimumSize(new Dimension(1024, 768));
		this.setResizable(false);
		
		this.paintContent();
		
		GUITools.center(this);
		
		this.setVisible(true);

		// =============== 下面的语句仅供测试 =================
//		this.timeManager.setPlan(new PlanTime(0, 8, 0), new PlanTime(0, 9, 40), new PlanContent());
//		this.timeManager.setPlan(new PlanTime(0, 10, 0), new PlanTime(0, 11, 40), new PlanContent());
//		this.timeManager.setPlan(new PlanTime(0, 14, 30), new PlanTime(0, 16, 10), new PlanContent());
//		this.timeManager.setPlan(new PlanTime(0, 16, 20), new PlanTime(0, 17, 50), new PlanContent());
		// =============== 上面的语句仅供测试 =================

		// 启动弹窗管理器
		Runnable popupManagerRunnable = new PopupManager(this.timeManager);
		new Thread(popupManagerRunnable).start();
	}

	// 添加系统托盘
	private void addSystemTray(Image image) {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon trayIcon = new TrayIcon(image, "TimeManager");
			trayIcon.setImageAutoSize(true);

			// 添加一个右键菜单
			PopupMenu menu = new PopupMenu();
			MenuItem exitMenuItem = new MenuItem("Exit");
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
				e1.printStackTrace();
			}

			// 左键单击显示窗口
			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						setVisible(true);
					}
					// 下面这段用来测试的而已
//					if (e.getButton() == MouseEvent.BUTTON2) {
//
//					}
				}
			});
		}
	}

	// 增加计划
	private void planAdd(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		this.planUpdate(planTimeFrom, planTimeTo, planContent);
	}

	// 更新计划
	private void planUpdate(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		planEditWindow.setPlanTimeFrom(planTimeFrom);
		planEditWindow.setPlanTimeTo(planTimeTo);
		planEditWindow.setPlanContent(planContent);

		planEditWindow.unableSubmit(); // 必须先unable才能显示
		planEditWindow.setVisible(true);

		// 写完计划后就获取计划
		PlanContent newPlanContent =  planEditWindow.getPlanContent();
		PlanTime newPlanTimeFrom = planEditWindow.getPlanTimeForm();
		PlanTime newPlanTimeTo = planEditWindow.getPlanTimeTo();

		if (planEditWindow.Submitted() && newPlanTimeFrom != null && newPlanTimeTo != null && !newPlanTimeFrom.equals(newPlanTimeTo)) {
			timeManager.setPlan(newPlanTimeFrom, newPlanTimeTo, newPlanContent);
			// 保存计划
			timeManager.saveToFile();

			this.paintContent();
		}
	}

	// 删除计划
	private void planDelete(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		this.timeManager.deletePlan(new Plan(planTimeFrom, planTimeTo, planContent));

		this.paintContent();
	}

	// 增加菜单
	private void addMenu() {
		// ================ 窗口菜单 ================
		JMenuBar menuBar = new JMenuBar();

		JMenu menuOperation = new JMenu("操作");

		JMenuItem menuNewPlan = new JMenuItem("新建计划");
		menuNewPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				planAdd(new PlanTime(), new PlanTime(), new PlanContent());
			}
		});

		JMenuItem menuExit = new JMenuItem("退出");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

		menuOperation.add(menuNewPlan);
		menuOperation.addSeparator();
		menuOperation.add(menuExit);

		menuBar.add(menuOperation);

		this.setJMenuBar(menuBar);


		// ================ 弹出式菜单 ================
		this.planOperationPopupMenu.setVisible(false);
		JMenuItem popupUpdatePlanMenu = new JMenuItem("更新");
		popupUpdatePlanMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				planUpdate(selectedPlan.getPlanTimeFrom(), selectedPlan.getPlanTimeTo(), selectedPlan.getPlanContent());
			}
		});
		JMenuItem popupDeletePlanMenu = new JMenuItem("删除");
		popupDeletePlanMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				planDelete(selectedPlan.getPlanTimeFrom(), selectedPlan.getPlanTimeTo(), selectedPlan.getPlanContent());
			}
		});
		this.planOperationPopupMenu.add(popupUpdatePlanMenu);
		this.planOperationPopupMenu.addSeparator();
		this.planOperationPopupMenu.add(popupDeletePlanMenu);
	}

	// 画主要内容
	private void paintContent() {

		// 设置程序图标
		this.setIconImage(new ImageIcon("image/icon.png").getImage());

		// 容器panel
		this.containerPanel.removeAll();
		GridBagConstraints containerConstraints = new GridBagConstraints();
		// 时间panel
		this.timePanel = new JPanel(null);
		this.timePanel.add(new JLabel("timePanel"));
		// 星期几panel
		JPanel dayPanel = new JPanel(new GridLayout(1, 7));
		//dayPanel.add(new JButton("dayPanel"));
		// 计划panel
		this.planPanel = new JPanel(new GridLayout(1, 7));
		//planPanel.add(new JButton("PlanPanel"));

		// 设置布局
		containerConstraints.fill = GridBagConstraints.BOTH;
		// 星期几panel
		containerConstraints.gridx = 1;
		containerConstraints.gridy = 0;
		containerConstraints.gridwidth = GridBagConstraints.REMAINDER;
		containerConstraints.weightx = 15;
		containerConstraints.weighty = 1;
		containerPanel.add(dayPanel, containerConstraints);
		// 时间panel
		containerConstraints.gridx = 0;
		containerConstraints.gridy = 1;
		containerConstraints.gridwidth = 1;
		containerConstraints.weightx = 1;
		containerConstraints.weighty = 15;
		containerPanel.add(this.timePanel, containerConstraints);
		// 计划panel
		containerConstraints.gridx = 1;
		containerConstraints.gridy = 1;
		containerConstraints.gridwidth = GridBagConstraints.REMAINDER;
		containerConstraints.weightx = 15;
		containerConstraints.weighty = 15;
		containerPanel.add(this.planPanel, containerConstraints);

		// 窗口添加容器panel
		this.add(containerPanel);

		// 添加星期信息
		String day[] = {"日", "一", "二", "三", "四", "五", "六"};
		for (int i=0; i<7; i++) {
			JLabel dayLabel = new JLabel("星期" + day[i]);
			dayLabel.setHorizontalAlignment(JLabel.CENTER);
			dayPanel.add(dayLabel);
		}

		this.updatePlanPanelShow();
		this.updateTimePanelShow();
	}

	// 更新时间panel的显示
	private void updateTimePanelShow() {
		// 先清除所有组件
		timePanel.removeAll();

		// 获取时间节点
		ArrayList<PlanTime> planTimes = new ArrayList<PlanTime>();
		PlanTime planTime = new PlanTime();
		for (int i=0; i<this.timeManager.getPlanListLength(); i++) {
			planTime = new PlanTime(this.timeManager.getPlan(i).getPlanTimeFrom());
			planTime.setDay(0);
			if (!planTimes.contains(planTime)) {
				planTimes.add(planTime);
			}
			planTime = new PlanTime(this.timeManager.getPlan(i).getPlanTimeTo());
			planTime.setDay(0);
			if (!planTimes.contains(planTime)) {
				planTimes.add(planTime);
			}
		}

		// 排序
		planTimes.sort((o1, o2) -> o1.getDay() == o2.getDay() ?
				o1.getHour() == o2.getHour() ? o1.getMinute() - o2.getMinute()
						: o1.getHour() - o2.getHour()
				: o1.getDay() - o2.getDay());

		// 重新计算容器大小
		//this.pack();

		int minuteFromMinToMax = PlanTime.getMinuteBetween(minPlanTime, maxPlanTime);
		minuteFromMinToMax = minuteFromMinToMax==0 ? 10 : minuteFromMinToMax;

		int containerWidth = this.timePanel.getWidth();
		int containerHeight = this.timePanel.getHeight();

		for (int i=0; i<planTimes.size(); i++) {
			int height = 20;
			int y;
			if (0 == i) {
				y = 0;
			} else if (planTimes.size()-1 == i) {
				y = containerHeight - 20;
			} else {
				y = (int) ((float)containerHeight / (float) minuteFromMinToMax * PlanTime.getMinuteBetween(minPlanTime, planTimes.get(i))) - 10;
			}

			int hour = planTimes.get(i).getHour();
			int minute = planTimes.get(i).getMinute();
			String sHour = hour > 9 ? String.valueOf(hour) : "0" + String.valueOf(hour);
			String sMinute = minute > 9 ? String.valueOf(minute) : "0" + String.valueOf(minute);
			JLabel label = new JLabel(sHour + ":" + sMinute + " ->");
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setSize(containerWidth, height);
			label.setLocation(0, y);
			this.timePanel.add(label);
		}
	}

	// 更新所有的计划的显示
	private void updatePlanPanelShow() {
		// 先清除所有组件
		planPanel.removeAll();

		// 7天的planPanel
		JPanel[] dayPlanPanel = new JPanel[7];
		for (int i=0; i<7; i++) {
			dayPlanPanel[i] = new JPanel(null);
			planPanel.add(dayPlanPanel[i]);
		}

		// 排序
		this.timeManager.sortAllPlan();

		// 二维数组，分星期几存储计划
		ArrayList<ArrayList<Plan>> dayPlan = new ArrayList<ArrayList<Plan>>();

		// 计算位置
		int t_index = 0; // 暂时用来索引技术
		Plan t_plan = null; // 暂时用来记录Plan
		PlanTime t_planTimeFrom = null; // 暂时用来记录开始PlanTime
		PlanTime t_planTimeTo = null; // 暂时用来记录结束PlanTime
		this.maxPlanTime = new PlanTime(0, 0, 0);
		this.minPlanTime = new PlanTime(0, 23, 59);
		for (int i=0; i<7; i++) {
			dayPlan.add(new ArrayList<Plan>());
			while ((t_plan = this.timeManager.getPlan(t_index)) != null && t_plan.getPlanTimeFrom().getDay() == i) {

				t_planTimeFrom = t_plan.getPlanTimeFrom();
				t_planTimeTo = t_plan.getPlanTimeTo();

				// 更新最大最小时间
				if (t_planTimeFrom.getHour() < minPlanTime.getHour()
						|| t_planTimeFrom.getHour() == t_planTimeFrom.getHour() && t_planTimeFrom.getMinute() < minPlanTime.getMinute()) {
					minPlanTime.setHour(t_planTimeFrom.getHour());
					minPlanTime.setMinute(t_planTimeFrom.getMinute());
				}
				if (t_planTimeTo.getHour() > maxPlanTime.getHour()
						|| t_planTimeTo.getHour() == maxPlanTime.getHour() && t_planTimeTo.getMinute() > maxPlanTime.getMinute()) {
					maxPlanTime.setHour(t_planTimeTo.getHour());
					maxPlanTime.setMinute(t_planTimeTo.getMinute());
				}

				// 添加记录到对应arraylist
				dayPlan.get(i).add(t_plan);

				t_index++;
			}
		}

		// 最大时间与最小时间之间相差的分钟数
		int minuteFromMinToMax = PlanTime.getMinuteBetween(minPlanTime, maxPlanTime);
		minuteFromMinToMax = minuteFromMinToMax==0 ? 10 : minuteFromMinToMax;

		// 让窗口计算出前面的容器的大小
		this.pack();

		// 显示计划内容
		for (int day=0; day<7; day++) {
			for (Iterator<Plan> iterator = dayPlan.get(day).iterator(); iterator.hasNext(); ) {
				Plan plan = iterator.next();
				PlanTime planTimeFrom = plan.getPlanTimeFrom();
				PlanTime planTimeTo = plan.getPlanTimeTo();

				int containerWith = dayPlanPanel[planTimeFrom.getDay()].getWidth();
				int containerHeight = dayPlanPanel[planTimeFrom.getDay()].getHeight();
				int height = (int) ((float)containerHeight / (float)minuteFromMinToMax * PlanTime.getMinuteBetween(planTimeFrom, planTimeTo));
				int y = (int) ((float)containerHeight / (float)minuteFromMinToMax * PlanTime.getMinuteBetween(minPlanTime, planTimeFrom));

				JLabel label = new JLabel(plan.getPlanContent().getContent());
				label.setSize(containerWith - 2, height - 2); // 为了添加一个缝隙
				label.setLocation(1, y + 1); // 为了添加一个缝隙
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setOpaque(true);
				label.setBackground(new Color(128, 187, 198));

				label.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						switch (e.getClickCount()) {
							case 1:
								// 右键单击
								if (e.getButton() == MouseEvent.BUTTON3) {
									selectedPlan = plan;
									planOperationPopupMenu.show(label, e.getX(), e.getY());
								}
								break;

							case 2:
								// 左键双击
								if (e.getButton() == MouseEvent.BUTTON1) {
									planUpdate(plan.getPlanTimeFrom(), plan.getPlanTimeTo(), plan.getPlanContent());
								}
								break;

							default:

								break;
						}
					}

					public void mouseEntered(MouseEvent e) {
						// 增大字体
						label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize() + 5));
					}

					public void mouseExited(MouseEvent e) {
						// 减小字体
						label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, label.getFont().getSize() - 5));
					}
				});

				dayPlanPanel[planTimeFrom.getDay()].add(label);
			}
		}
	}

	private void exit() {
		this.timeManager.saveToFile();
		this.dispose();
		System.exit(0);
	}
}
