package window;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Toolkit;

public class GUITools {
	static Toolkit kToolkit = Toolkit.getDefaultToolkit();
	
	// 把组件位置定位屏幕中间
	public static void center(Component component) {
		int x = (kToolkit.getScreenSize().width - component.getWidth()) / 2;
		int y = (kToolkit.getScreenSize().height - component.getHeight()) / 2;
		component.setLocation(x, y);
	}
	
	// 把组件位置定位在屏幕的右下角
	public static void rightButtom(Component component) {
		Insets screenInsets = kToolkit.getScreenInsets(component.getGraphicsConfiguration());
		int taskBarHeight = screenInsets.bottom; //任务栏高度
		int x = kToolkit.getScreenSize().width - component.getWidth();
		int y = kToolkit.getScreenSize().height - component.getHeight() - taskBarHeight;
		component.setLocation(x, y);
	}
	
	// 透明渐入
	public static void fadeIn(Component component, int milisecond) {
		Color color = component.getBackground();
		if (milisecond < 0) {
			return;
		}
		for (int i=0; i<milisecond; i++) {
			try {
				Thread.sleep(1);
				component.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), i*255/milisecond));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 透明渐出
	public static void fadeOut(Component component, int milisecond) {
		Color color = component.getBackground();
		if (milisecond < 0) {
			return;
		}
		for (int i=milisecond; i>0; i--) {
			try {
				Thread.sleep(1);
				component.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), i*255/milisecond));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
