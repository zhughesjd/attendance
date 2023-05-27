package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.model.Model;
import net.joshuahughes.attendance.model.Model.Status;

public class CouplePanel extends AttendancePanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String PERSON_CHECKIN = "PERSON_CHECKIN";
	public static final String COMPLETED = "COMPLETED";
	int colCnt = 2;
	JButton checkIn = new JButton("Check In");
	Person husband;
	JCheckBox husbandBtn;
	Person wife;
	JCheckBox wifeBtn;
	public CouplePanel() 
	{
		super("Select name");
		cntrPnl.setLayout(new GridBagLayout());
		checkIn.addActionListener(l->
		{
			if(husbandBtn != null && husbandBtn.isSelected())
				firePropertyChange(PERSON_CHECKIN, null, husband);
			if(wifeBtn != null && wifeBtn.isSelected())
				firePropertyChange(PERSON_CHECKIN, null, wife);
			firePropertyChange(COMPLETED, null, null);
		});
	}
	public void populate(Model model)
	{
		cntrPnl.removeAll();
		Family couple = model.getCouples(Status.enrolled, Family.alphabetical).get(0);
		husband = couple.getHusband();
		wife = couple.getWife();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		if(valid(husband))
		{
			cntrPnl.add(husbandBtn = createCheckBox(couple.getHusband()),gbc);
			gbc.gridy++;
		}
		if(valid(wife))
		{
			cntrPnl.add(wifeBtn = createCheckBox(couple.getWife()),gbc);
			gbc.gridy++;
		}
		cntrPnl.add(checkIn,gbc);
	}
	private boolean valid(Person person)
	{
		return person!=null && person.getFirst()!=null && person.getLast()!=null;
	}
	private JCheckBox createCheckBox(Person person)
	{
		return new JCheckBox(person.getFirst()+" "+person.getLast(),true);
	}
}