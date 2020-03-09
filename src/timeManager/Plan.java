package timeManager;

// 计划
public class Plan {
	public Plan() {
		this.time = -1;
		this.planString = new String();
	}
	
	public Plan(int time, String plan) {
		this.setPlan(time, plan);
	}
	
	public void setPlan(int time, String plan) {
		this.time = time;
		this.planString = plan;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public String getPlan() {
		return this.planString;
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return String.valueOf(this.time) + ":" + this.planString;
	}
	
	private int time;
	private String planString;
}
