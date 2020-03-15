package timeManager;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// 计划表
public class Table {
	public Table() {
		this.plan = new ArrayList<Plan>();
	}
	
	// 写一个计划，假如计划表中已经有该时间的计划，则覆盖原有计划
	public void setPlan(PlanTime planTime, String plan) {
		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime().equals(planTime)) {
				p.setPlan(planTime, plan);
				return;
			}
		}
		
		this.plan.add(new Plan(planTime, plan));
		return;
	}
	
	// 获取一个计划
	public String getPlan(PlanTime planTime) {
		String plan = new String();
		
		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime().equals(planTime)) {
				plan = p.getPlan();
				break;
			}
		}
		
		return plan;
	}
	
	// 获取所有的计划
	public Map<PlanTime, String> getAllPlan() {		
		Map<PlanTime, String> planMap = new HashMap<PlanTime, String>();

		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan plan = iterator.next();
			planMap.put(plan.getTime(), plan.getPlan());
		}
		
		return planMap;
	}
	
	// 批量加载计划
	public void setAllPlan(Map<PlanTime, String> planMap) {
		for (Map.Entry<PlanTime, String> entry : planMap.entrySet()) {
			this.setPlan(entry.getKey(), entry.getValue());
		}
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return plan.toString();
	}
	
	private ArrayList<Plan> plan;
}
