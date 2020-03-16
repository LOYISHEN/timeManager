package window;

import timeManager.PlanTime;
import timeManager.TimeManager;

public class PopupManager implements Runnable{
	public PopupManager(TimeManager timeManager) {
		this.timeManager = timeManager;
	}
	
	public void popup(String message, int delayTime) {
		Runnable popupWindowRunnable = new PopupWindow(message, delayTime);
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
				continue;
			}
			System.out.println(nowTime);
			lastTime = new PlanTime(nowTime);
			String plan = timeManager.getPlan(nowTime);
			if (plan.length() > 0) {
				popup(plan, 4000); // delayTime单位是毫秒
				System.out.println(nowTime.toString() + " popup window, plan is " + plan);
			}
		}
	}
	
	private TimeManager timeManager;
}
