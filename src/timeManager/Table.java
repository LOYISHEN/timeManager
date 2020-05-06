package timeManager;

import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;

import java.util.*;

// 计划表
public class Table {
	private ArrayList<Plan> planArrayList;

	public Table() {
		this.planArrayList = new ArrayList();
	}
	
	// 写一个计划，假如计划表中已经有该时间的计划，则覆盖原有计划
	public void setPlan(PlanTime planTimeFrom, PlanTime planTimeTo, PlanContent planContent) {
		Plan plan = new Plan(planTimeFrom, planTimeTo, planContent);

		for (int i=0; i<this.planArrayList.size(); i++) {
			Plan p = this.planArrayList.get(i);
			if (p.getPlanTimeFrom().toString().equals(planTimeFrom.toString())
					&& p.getPlanTimeTo().toString().equals(planTimeTo.toString())) {
				this.planArrayList.set(i, plan);
				return;
			}
		}
		// 假如不在队列中就添加一个
		this.planArrayList.add(plan);
		return;
	}

	// 根据索引获取一个计划
	public Plan getPlan(int index) {
		if (index >= this.planArrayList.size() || index < 0) {
			return null;
		}
		return this.planArrayList.get(index);
	}

	// 获取一个计划
	public Plan getPlan(PlanTime planTimeFrom) {
		boolean hitPlan = false; // 是否击中计划
		Plan plan = new Plan();

		// 尝试击中计划
		Iterator<Plan> iterator = this.planArrayList.iterator();
		while (iterator.hasNext()) {
			Plan p = iterator.next();
			// 因为计划时间不可重叠，所以只需要判断起始时间是否相同即可
			if (p.getPlanTimeFrom().equals(planTimeFrom)) {
				plan = p;
				hitPlan = true;
				break;
			}
		}

		// 根据击中情况返回数据或空
		if (hitPlan) {
			return plan;
		} else {
			return null;
		}

	}
	
	// 获取所有的计划
	public ArrayList<Plan> getAllPlan() {
		return this.planArrayList;
	}

	// 对所有计划按照时间排序
	public void sortAllPlan() {
		this.planArrayList.sort((o1, o2) ->
				o1.getPlanTimeFrom().getDay() == o2.getPlanTimeFrom().getDay() ?
						o1.getPlanTimeFrom().getHour() == o2.getPlanTimeFrom().getHour() ? o1.getPlanTimeFrom().getMinute() - o2.getPlanTimeFrom().getMinute()
							: o1.getPlanTimeFrom().getHour() - o2.getPlanTimeFrom().getHour()
						: o1.getPlanTimeFrom().getDay() - o2.getPlanTimeFrom().getDay());
	}

	// 批量加载计划
	public void setAllPlan(ArrayList<Plan> planArrayList) {
		// 这里根据时间排下序
		this.sortAllPlan();

		// 添加计划
		for (int i = 0; i < planArrayList.size(); i++) {
			this.setPlan(planArrayList.get(i).getPlanTimeFrom(), planArrayList.get(i).getPlanTimeTo(), planArrayList.get(i).getPlanContent());
		}
	}

	public void deletePlan(Plan plan) {
		for (int i=0; i<this.planArrayList.size(); i++) {
			if (planArrayList.get(i).equals(plan)) {
				planArrayList.remove(i);
				return;
			}
		}
	}

	// 输出计划列表，调试用
	public String toString() {
		return planArrayList.toString();
	}

	// 返回计划队列的长度
	public int getPlanListLength() {
		return this.planArrayList.size();
	}
}
