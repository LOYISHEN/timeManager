package window;

import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class PlanEditWindow extends JDialog{
	private JTextArea textAreaContent;
	private JTextArea textAreaPlanTimeFrom;
	private JTextArea textAreaPlanTimeto;
	private PlanContent planContent;
	private PlanTime planTimeFrom;
	private PlanTime planTimeTo;

	public PlanEditWindow(String title) {
		this.setTitle(title);
		this.setSize(200, 150);
		this.setModal(true);
		
		this.setLayout(new BorderLayout());
		
		this.paintContent();
		
		this.addWindowListener(new WindowAdapter() {
			// 设置窗口显示后就focus到textarea，然后选中所有文字
			public void windowActivated(WindowEvent e) {
				textAreaContent.setFocusable(true);
				textAreaContent.selectAll();
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

		JPanel contentPanel = new JPanel(new GridLayout(3,1));
		this.textAreaPlanTimeFrom = new JTextArea();
		this.textAreaPlanTimeto = new JTextArea();
		this.textAreaContent = new JTextArea();
		contentPanel.add(this.textAreaPlanTimeFrom);
		contentPanel.add(this.textAreaPlanTimeto);
		contentPanel.add(this.textAreaContent);

		this.add(contentPanel, BorderLayout.CENTER);
	}
	
	private void submit() {
		this.planContent = new PlanContent(this.textAreaContent.getText());
		this.planTimeFrom = PlanTime.valueOf(this.textAreaPlanTimeFrom.getText());
		this.planTimeTo = PlanTime.valueOf(this.textAreaPlanTimeto.getText());
		System.out.println("submit");
		System.out.println(planContent);
		this.setVisible(false);
	}
	
	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
		this.textAreaContent.setText(planContent.toString());
	}
	
	public PlanContent getPlanContent() {
		return this.planContent;
	}

	public void setPlanTimeFrom(PlanTime time) {
		this.planTimeFrom = time;
		this.textAreaPlanTimeFrom.setText(planTimeFrom.toString());
	}

	public PlanTime getPlanTimeForm() {
		return this.planTimeFrom;
	}

	public void setPlanTimeTo(PlanTime time) {
		this.planTimeTo = time;
		this.textAreaPlanTimeto.setText(planTimeTo.toString());
	}

	public PlanTime getPlanTimeTo() {
		return this.planTimeTo;
	}
}
