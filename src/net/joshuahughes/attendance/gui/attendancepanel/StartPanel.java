package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import net.joshuahughes.attendance.model.Model;

public class StartPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String CHECK_IN = "Check in";
	public static final String PRINT_ROSTER = "Print Roster";
	public static final String EDIT_ROSTER = "Edit Roster";
	public static final String ALERT_LEADER = "Alert Leader";
	private Model model;
	public StartPanel()
	{
		super("New Ventrue Attendance Tool");
		backBtn.setText(" ");
		backBtn.setOpaque(true);
		backBtn.setBackground(this.getBackground());
		backBtn.setContentAreaFilled(false);
		backBtn.setBorder(BorderFactory.createLineBorder(this.getBackground()));;

		cntrPnl.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		cntrPnl.add(createButton(CHECK_IN),gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		cntrPnl.add(createButton(PRINT_ROSTER),gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		cntrPnl.add(createButton(EDIT_ROSTER),gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		cntrPnl.add(createButton(ALERT_LEADER),gbc);
	}
	public void setModel(Model model)
	{
		this.model = model;
	}
	private JButton createButton(String text)
	{
		JButton btn = new JButton(text);
		btn.addActionListener(l->firePropertyChange(text, null, model));
		return btn;
	}
	@Override
	public void populate(Model model){}
}
