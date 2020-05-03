package timeManager;

import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;

import java.util.*;

// 计划表
public class Table {
	public Table() {
		this.planArrayList = new ArrayList();
	}
	
	// 写一个计划，假如计划表中已经有该时间的计划，则覆盖原有计划
	public void setPlan(PlanTime planTime, PlanContent planContent) {
		Iterator<Plan> iterator = this.planArrayList.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime().equals(planTime)) {
				p.setPlanContent(planTime, planContent);
				return;
			}
		}
		
		this.planArrayList.add(new Plan(planTime, planContent));
		return;
	}

	// 根据索引获取一个计划
	public Plan getPlanContent(int index) {
		if (index >= this.planArrayList.size() || index < 0) {
			return null;
		}
		return this.planArrayList.get(index);
	}

	// 获取一个计划
	public PlanContent getPlanContent(PlanTime planTime) {
		boolean hitPlan = false; // 是否击中计划
		PlanContent planContent = new PlanContent();

		// 尝试击中计划
		Iterator<Plan> iterator = this.planArrayList.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			if (p.getTime().equals(planTime)) {
				planContent = p.getPlanContent();
				hitPlan = true;
				break;
			}
		}

		// 根据击中情况返回数据或空
		if (hitPlan) {
			return planContent;
		} else {
			return null;
		}

	}
	
	// 获取所有的计划
	public ArrayList<Plan> getAllPlan() {
		return this.planArrayList;
	}
	
	// 批量加载计划
	public void setAllPlan(ArrayList<Plan> planArrayList) {
		// 这里根据时间排下序
		planArrayList.sort((o1, o2) -> o1.getTime().toString().compareTo(o2.getTime().toString()));

		// 添加计划
		for (int i = 0; i < planArrayList.size(); i++) {
			this.setPlan(planArrayList.get(i).getTime(), planArrayList.get(i).getPlanContent());
		}
	}
	
	// 输出计划列表，调试用
	public String toString() {
		return planArrayList.toString();
	}

	public int getPlanSize() {
		return this.planArrayList.size();
	}
	
	private ArrayList<Plan> planArrayList;
}
