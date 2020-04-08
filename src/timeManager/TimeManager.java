package timeManager;

import java.util.ArrayList;
import java.util.Map;

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
		return this.table.getPlan(index);
	}

	// 根据时间获取计划
	public String getPlan(PlanTime planTime) {
		String plan = new String();
		
		plan = this.table.getPlan(planTime);
		
		return plan;
	}
	
	// 设置计划
	public void setPlan(PlanTime time, String plan) {
		this.table.setPlan(time, plan);
	}
	
	// 保存计划到文件中
	public void saveToFile() {
		if (!this.fileSaver.save(this.table)) {
			System.out.println(this.fileSaver.getErrorString());
		}
	}
	
	// 从文件中读取计划
	public void readFromFile() {
		ArrayList<Plan> planArrayList = this.fileSaver.read();

		if (0 == planArrayList.size()) { // 可能是第一次运行程序，这时写入一个空的计划表。以后要加上判断
			System.out.println(this.fileSaver.getErrorString());
			this.saveToFile();
			planArrayList = this.fileSaver.read();
		}

		this.table.setAllPlan(planArrayList);
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return table.toString();
	}
	
	private Table table;
	private FileSaver fileSaver;
}
