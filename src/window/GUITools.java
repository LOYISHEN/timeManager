package window;
import java.awt.Component;
import java.awt.Toolkit;

public class GUITools {
	static Toolkit kToolkit = Toolkit.getDefaultToolkit();
	
	// 把组件位置定位屏幕中间
	public static void center(Component component) {
		int x = (kToolkit.getScreenSize().width - component.getWidth()) / 2;
		int y = (kToolkit.getScreenSize().height - component.getHeight()) / 2;
		component.setLocation(x, y);
	}
}
