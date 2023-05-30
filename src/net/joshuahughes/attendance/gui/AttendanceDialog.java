package net.joshuahughes.attendance.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.joshuahughes.attendance.Utility;
import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.gui.attendancepanel.AttendancePanel;
import net.joshuahughes.attendance.gui.attendancepanel.CouplePanel;
import net.joshuahughes.attendance.gui.attendancepanel.EditPanel;
import net.joshuahughes.attendance.gui.attendancepanel.LetterPanel;
import net.joshuahughes.attendance.gui.attendancepanel.StartPanel;
import net.joshuahughes.attendance.gui.attendancepanel.SublistPanel;
import net.joshuahughes.attendance.gui.attendancepanel.TextPanel;
import net.joshuahughes.attendance.model.Model;

public class AttendanceDialog extends JDialog
{
	private static final long serialVersionUID = -7903816866478815929L;

	TextPanel textPnl = new TextPanel();
	EditPanel editPnl = new EditPanel();
	StartPanel startPnl = new StartPanel();
	LetterPanel letterPnl = new LetterPanel();
	SublistPanel sublistPnl = new SublistPanel();
	CouplePanel couplePnl = new CouplePanel();
	JPanel contentPne = new JPanel(new BorderLayout());

	public AttendanceDialog(Model model)
	{
		startPnl.setModel(model);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(contentPne);
		contentPne.add(startPnl,BorderLayout.CENTER);

		startPnl.addPropertyChangeListener(StartPanel.CHECK_IN, e->
		{
			letterPnl.populate(model);
			setContent(letterPnl);
		});
		startPnl.addPropertyChangeListener(StartPanel.PRINT_ROSTER, e->
		{
			Utility.printRoster((Model) e.getNewValue());
		});
		startPnl.addPropertyChangeListener(StartPanel.EDIT_ROSTER, e->
		{
			editPnl.populate(model);
			setContent(editPnl);
		});
		startPnl.addPropertyChangeListener(StartPanel.NOTIFY_LEADER, e->
		{
			textPnl.populate(model);
			setContent(textPnl);
		});
		

		/***************************************/
		
		
		letterPnl.addPropertyChangeListener(LetterPanel.SUBLIST_SELECTED, e->
		{
			sublistPnl.populate((Model) e.getNewValue());
			setContent(sublistPnl);
		});
		sublistPnl.addPropertyChangeListener(SublistPanel.COUPLE_SELECTED, e->
		{
			couplePnl.populate((Model) e.getNewValue());
			setContent(couplePnl);
		});
		couplePnl.addPropertyChangeListener(CouplePanel.PERSON_CHECKIN, e->
		{
			model.checkIn((Person) e.getNewValue());
			setContent(couplePnl);
		});
		couplePnl.addPropertyChangeListener(CouplePanel.COMPLETED, e->
		{
			setContent(letterPnl);
		});
		
		/***************************************/
		
		textPnl.addPropertyChangeListener(AttendancePanel.BACK, e->
		{
			setContent(startPnl);
		});
		editPnl.addPropertyChangeListener(AttendancePanel.BACK, e->
		{
			setContent(startPnl);
		});
		letterPnl.addPropertyChangeListener(AttendancePanel.BACK, e->
		{
			if(validAdmin()) setContent(startPnl);
		});
		sublistPnl.addPropertyChangeListener(AttendancePanel.BACK, e->
		{
			setContent(letterPnl);
		});
		couplePnl.addPropertyChangeListener(AttendancePanel.BACK, e->
		{
			setContent(sublistPnl);
		});
	}
	private boolean validAdmin()
	{
		PinPanel pinPnl = new PinPanel();
		int option = JOptionPane.showConfirmDialog(null, pinPnl, "admin pin", JOptionPane.OK_CANCEL_OPTION);
		boolean valid = pinPnl.getText().trim().equals("490");
		if(!valid)
		{
			JOptionPane.showMessageDialog(null,new JLabel("Incorrect Pin"), " hint: maximum pardon count", JOptionPane.OK_OPTION);
		}
		return option == JOptionPane.OK_OPTION && valid;
	}
	private void setContent(Component c)
	{
		contentPne.removeAll();
		contentPne.add(c,BorderLayout.CENTER);
		contentPne.updateUI();
	}
}
