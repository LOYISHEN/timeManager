package timeManager;

// 计划
public class Plan {
	public Plan() {
		this.planTime = new PlanTime();
		this.planString = new String();
	}
	
	public Plan(PlanTime planTime, String plan) {
		this.setPlan(planTime, plan);
	}
	
	public void setPlan(PlanTime planTime, String plan) {
		this.planTime = planTime;
		this.planString = plan;
	}
	
	public PlanTime getTime() {
		return this.planTime;
	}
	
	public String getPlan() {
		return this.planString;
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return this.planTime.toString() + ":" + this.planString;
	}
	
	private PlanTime planTime;
	private String planString;
}
