package timeManager;

import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;

import java.util.ArrayList;

public class TimeManager {
	// filename 为保存到的文件名
	public TimeManager(String filename) {
		this.table = new Table();
		fileSaver = new FileSaver(filename);
	}

	public int getPlanSize() {
		return this.table.getPlanSize();
	}

	// 根据索引获取某一个计划
	public Plan getPlan(int index) {
		return this.table.getPlanContent(index);
	}

	// 根据时间获取计划
	public PlanContent getPlan(PlanTime planTime) {
		PlanContent planContent = new PlanContent();

		planContent = this.table.getPlanContent(planTime);
		
		return planContent;
	}
	
	// 设置计划
	public void setPlan(PlanTime time, PlanContent planContent) {
		this.table.setPlan(time, planContent);
	}
	
	// 保存计划到文件中
	public void saveToFile() {
		if (!this.fileSaver.save(this.table)) {
			System.out.println(this.fileSaver.getErrorString());
		}
	}
	
	// 从文件中读取计划
	public boolean readFromFile() {
		ArrayList<Plan> planArrayList = this.fileSaver.read();

		// 判断读取配置文件是否有误
		if (null == planArrayList) {
			System.out.println(this.fileSaver.getErrorString());

			// 出现错误时，保存一下数据再读进来
			this.saveToFile();
			planArrayList = this.fileSaver.read();

			// 假如读入还是失败，就返回false
			if (null == planArrayList) {
				return false;
			}
		}

		this.table.setAllPlan(planArrayList);
		return true;
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return table.toString();
	}
	
	private Table table;
	private FileSaver fileSaver;
}
