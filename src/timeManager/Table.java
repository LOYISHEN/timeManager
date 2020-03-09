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
	public void setPlan(int time, String plan) {
		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime() == time) {
				p.setPlan(time, plan);
				return;
			}
		}
		
		this.plan.add(new Plan(time, plan));
		return;
	}
	
	// 获取一个计划
	public String getPlan(int time) {
		String plan = new String();
		
		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime() == time) {
				plan = p.getPlan();
				break;
			}
		}
		
		return plan;
	}
	
	// 获取所有的计划
	public Map<String, String> getAllPlan() {		
		Map<String, String> planMap = new HashMap<String, String>();

		Iterator<Plan> iterator = this.plan.iterator();
		while (iterator.hasNext()) {
			Plan plan = iterator.next();
			planMap.put(String.valueOf(plan.getTime()), plan.getPlan());
		}
		
		return planMap;
	}
	
	// 批量加载计划
	public void setAllPlan(Map<String, String> planMap) {
		for (Map.Entry<String, String> entry : planMap.entrySet()) {
			this.setPlan(Integer.valueOf(entry.getKey()), entry.getValue());
		}
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return plan.toString();
	}
	
	private ArrayList<Plan> plan;
}
