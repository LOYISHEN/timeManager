package main;
import timeManager.*;
import window.Window;

public class main {

	// 程序入口
	public static void main(String[] args) {
		new Window("TimeManager - By PenG", new TimeManager("timeplan.json"));
	}

}
