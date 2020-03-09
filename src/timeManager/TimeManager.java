package timeManager;

public class TimeManager {
	// filename 为保存到的文件名
	public TimeManager(String filename) {
		this.table = new Table();
		fileSaver = new FileSaver(filename);
	}
	
	// 根据时间获取计划
	public String getPlan(int time) {
		String plan = new String();
		
		plan = this.table.getPlan(time);
		
		return plan;
	}
	
	// 设置计划
	public void setPlan(int time, String plan) {
		this.table.setPlan(time, plan);
	}
	
	// 保存计划到文件中
	public void saveToFile() {
		fileSaver.save(this.table);
	}
	
	// 从文件中读取计划
	public void readFromFile() {
		this.table.setAllPlan(fileSaver.read());
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return table.toString();
	}
	
	private Table table;
	private FileSaver fileSaver;
}
