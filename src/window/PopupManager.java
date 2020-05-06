package window;

import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;
import timeManager.TimeManager;

public class PopupManager implements Runnable{
	public PopupManager(TimeManager timeManager) {
		this.timeManager = timeManager;
	}
	
	public void popup(PlanContent planContent, int delayTime) {
		Runnable popupWindowRunnable = new PopupWindow(planContent, delayTime);
		new Thread(popupWindowRunnable).start();
	}

	public void run() {
		PlanTime nowTime;
		PlanTime lastTime;
		nowTime = PlanTime.getNowFormatTime();
		lastTime = null;
		while (true) {
			if (nowTime.equals(lastTime)) {
				nowTime = PlanTime.getNowFormatTime();
				// 隔一段时间再继续，希望能减少系统资源的占用
				//导致一个结果就是：不能准时到某一分钟的第0秒刷新数据
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			System.out.println(nowTime);

			lastTime = new PlanTime(nowTime);
			Plan plan = timeManager.getPlan(nowTime);
			// 判断该计划是否有内容
			if (null != plan) {
				popup(plan.getPlanContent(), 4000); // delayTime单位是毫秒
				System.out.println(nowTime.toString() + " popup window, plan is " + plan.toString());
			}
		}
	}
	
	private TimeManager timeManager;
}
