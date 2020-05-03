package timeManager.Plan;

// 计划
public class Plan {
	private PlanTime planTime;
	private PlanContent planContent;

	public Plan() {
		this.planTime = new PlanTime();
		this.planContent = new PlanContent();
	}
	
	public Plan(PlanTime planTime, PlanContent planContent) {
		this.setPlanContent(planTime, planContent);
	}
	
	public void setPlanContent(PlanTime planTime, PlanContent planContent) {
		this.planTime = planTime;
		this.planContent = planContent;
	}
	
	public PlanTime getTime() {
		return this.planTime;
	}
	
	public PlanContent getPlanContent() {
		return this.planContent;
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return this.planTime.toString() + ":" + this.planContent;
	}
	

}
