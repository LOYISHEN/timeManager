package timeManager.Plan;

// 计划
public class Plan {
	private PlanTime planTimeFrom; // 开始时间
	private PlanTime planTimeTo; // 结束时间
	private PlanContent planContent;

	public Plan() {
		this.planTimeFrom = new PlanTime();
		this.planTimeTo = new PlanTime();
		this.planContent = new PlanContent();
	}
	
	public Plan(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		this.setPlanContent(planTimeFrom, planTimeTo, planContent);
	}
	
	public void setPlanContent(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		this.planTimeFrom = planTimeFrom;
		this.planTimeTo = planTimeTo;
		this.planContent = planContent;
	}
	
	public PlanTime getPlanTimeFrom() {
		return this.planTimeFrom;
	}

	public PlanTime getPlanTimeTo() {
		return this.planTimeTo;
	}
	
	public PlanContent getPlanContent() {
		return this.planContent;
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return " from " + this.planTimeFrom.toString() + " to " + this.planTimeTo.toString() + ":" + this.planContent;
	}
	
	public boolean equals(Plan plan) {
		if (plan == null) {
			return false;
		} else if (plan == this) {
			return true;
		} else {
			return plan.getPlanTimeFrom().toString().equals(this.getPlanTimeFrom().toString())
					&& plan.getPlanTimeTo().toString().equals(this.planTimeTo.toString())
					&& plan.getPlanContent().toString().equals(this.planContent.toString());
		}
	}
}
