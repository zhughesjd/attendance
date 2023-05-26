package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.joshuahughes.attendance.model.Model;

public abstract class AttendancePanel extends JPanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String BACK = "BACK";
	
	JLabel titleLbl = new JLabel("",JLabel.CENTER);
	JPanel cntrPnl = new JPanel();
	JButton backBtn = new JButton("<- Back");
	public AttendancePanel(String title)
	{
		super(new BorderLayout());
		titleLbl.setText(title);
		add(titleLbl,BorderLayout.NORTH);
		add(cntrPnl,BorderLayout.CENTER);
		add(backBtn,BorderLayout.SOUTH);
		backBtn.addActionListener(l->firePropertyChange(BACK, null, BACK));
	}
	public abstract void populate(Model model);
}
