package window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class PlanEditWindow extends JDialog{
	public PlanEditWindow(String title) {
		this.setTitle(title);
		this.setSize(200, 150);
		this.setModal(true);
		
		this.setLayout(new BorderLayout());
		
		this.paintContent();
		
		this.addWindowListener(new WindowAdapter() {
			// 设置窗口显示后就focus到textarea，然后选中所有文字
			public void windowActivated(WindowEvent e) {
				textArea.setFocusable(true);
				textArea.selectAll();
			}
		});
		
		GUITools.center(this);
		this.setVisible(false);
	}
	
	private void paintContent() {
		JButton submitButton = new JButton("确定");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		this.add(submitButton, BorderLayout.SOUTH);
		
		this.textArea = new JTextArea();
		this.add(textArea, BorderLayout.CENTER);
	}
	
	private void submit() {
		String plan = this.textArea.getText();
		System.out.println("submit");
		System.out.println(plan);
		this.plan = plan;
		this.setVisible(false);
	}
	
	public void setPlan(String plan) {
		this.textArea.setText(plan);
		this.plan = plan;
	}
	
	public String getPlan() {
		return this.plan;
	}
	
	private JTextArea textArea;
	private String plan;
}
