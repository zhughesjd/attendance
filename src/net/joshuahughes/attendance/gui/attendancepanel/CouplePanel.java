package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.model.Model;

public class CouplePanel extends AttendancePanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String PERSON_CHECKIN = "PERSON_CHECKIN";
	public static final String COMPLETED = "COMPLETED";
	int colCnt = 2;
	JButton checkIn = new JButton("Check In");
	LinkedHashMap<Person,JCheckBox> boxes = new LinkedHashMap<>();
	public CouplePanel() 
	{
		super("Select name");
		cntrPnl.setLayout(new GridBagLayout());
		checkIn.addActionListener(l->
		{
			boxes.entrySet().forEach(e->{if(e.getValue().isSelected()) firePropertyChange(PERSON_CHECKIN, null, e.getKey());});
			firePropertyChange(COMPLETED, null, null);
		});
	}
	public void populate(Model model)
	{
		boxes.clear();
		cntrPnl.removeAll();
		Family family = model.getFamilies(Family.alphabetical).get(0);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		family.getPeople().stream().forEach(p->
		{
			cntrPnl.add(createCheckBox(p),gbc);
			gbc.gridy++;		
		});
		cntrPnl.add(checkIn,gbc);
	}
	private JCheckBox createCheckBox(Person person)
	{
		JCheckBox box = new JCheckBox(person.getFirst()+" "+person.getLast(),true);
		boxes.put(person,box);
		return box;
	}
}