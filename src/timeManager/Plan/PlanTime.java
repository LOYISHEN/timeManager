package timeManager.Plan;

import java.util.Date;
import java.util.Calendar;

public class PlanTime {
	public PlanTime() {
		this.day = 0;
		this.hour = 0;
		this.minute = 0;
	}
	
	public PlanTime(PlanTime planTime) {
		this.setDay(planTime.day);
		this.setHour(planTime.hour);
		this.setMinute(planTime.minute);
	}
	
	public PlanTime(int day, int hour, int minute) {
		this.setDay(day);
		this.setHour(hour);
		this.setMinute(minute);
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMinute() {
		return this.minute;
	}
	
	public void setDay(int day) {
		if (day < 0 || day > 6) {
			return;
		}
		this.day = day;
	}
	
	public void setHour(int hour) {
		if (hour < 0 || hour >23) {
			return;
		}
		this.hour = hour;
	}
	
	public void setMinute(int minute) {
		if (minute < 0 || minute > 59) {
			return;
		}
		this.minute = minute;
	}
	
	public String toString() {
		return String.valueOf(this.day) + "-" + String.valueOf(this.hour) + ":" +String.valueOf(this.minute);
	}
	
	public boolean equals(Object anObject) {
		if (anObject == null) {
			return false;
		}
		
		if (anObject == this) {
			return true;
		}
		
		if (anObject.getClass() != this.getClass()) {
			return false;
		}
		
		PlanTime tmp = (PlanTime)anObject;
		return ((tmp.day == this.day) && (tmp.hour == this.hour) && (tmp.minute == this.minute)); 
	}
	
	public static PlanTime valueOf(String planTimeString) {
		PlanTime planTime;
		
		String[] strings1 = planTimeString.split("-");
		if (strings1.length < 2) {
			return null;
		}
		int day = Integer.valueOf(strings1[0]);
		
		String[] strings2 = strings1[1].split(":");
		if (strings2.length < 2) {
			return null;
		}
		int hour = Integer.valueOf(strings2[0]);
		int minute = Integer.valueOf(strings2[1]);
		
		planTime = new PlanTime(day, hour, minute);
		return planTime;
	}
	
	// 获取当前的时间，并且转换为PlanTime类型
	public static PlanTime getNowFormatTime() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1; // calendar返回的值域为[1,7]，所以要减一
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR是12小时制，HOUR_OF_DAY是24小时制
		int minute = calendar.get(Calendar.MINUTE);
		
		return new PlanTime(day, hour, minute);
	}
	
	private int day; // 星期几 0为星期天，1为星期一，一直到星期六
	private int hour; // 24小时制
	private int minute;
}
