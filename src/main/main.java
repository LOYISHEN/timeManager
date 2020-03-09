package main;
import timeManager.*;
import window.Window;

public class main {

	public static void main(String[] args) {
//		TimeManager timeManager = new TimeManager("a.json");
//	
//		timeManager.setPlan(0, "Python");
//		timeManager.setPlan(1, "Java");
//		timeManager.setPlan(5, "c");
//		System.out.println(timeManager.getPlan(0));
//		System.out.println(timeManager.getPlan(1));
//		System.out.println(timeManager.getPlan(5));
//		timeManager.setPlan(0, "shell");
//		System.out.println(timeManager.getPlan(0));
//		//timeManager.saveToFile();
//		timeManager.readFromFile();
//		System.out.println(timeManager);
//		timeManager.setPlan(10, "javascript");
//		System.out.println(timeManager);
//		timeManager.saveToFile();
//		System.out.println(timeManager.getPlan(10));
		
		new Window("时间管理器 - By PenG", new TimeManager("a.json"));
		
		System.out.println("Done!");
	}

}
