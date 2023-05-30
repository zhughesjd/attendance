package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.model.ListModel;
import net.joshuahughes.attendance.model.Model;

public class SublistPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String COUPLE_SELECTED = "COUPLE_SELECTED";
	int colCnt = 2;
	JPanel couplesPnl = new JPanel(new GridBagLayout());
	public SublistPanel() 
	{
		super("Select couple");
		cntrPnl.setLayout(new BorderLayout());
		cntrPnl.add(new JScrollPane(couplesPnl),BorderLayout.CENTER);
	}
	public void populate(Model model)
	{
		couplesPnl.removeAll();
		List<Family> list = model.getFamilies(Family.alphabetical);
		int rowCnt = list.size()/2;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		AtomicInteger ndx = new AtomicInteger();
		for(int y=0;y<rowCnt;y++)
		{
			for(int x=0;x<colCnt;x++)
			{
				if(ndx.get()>=list.size())continue;
				gbc.gridx = x;
				gbc.gridy = y;
				couplesPnl.add(createButton(list.get(ndx.getAndIncrement())),gbc);
			}
		}
	}
	private Component createButton(Family family) 
	{
		String first = family.getPeople().get(0).getFirst();
		String middle = family.getPeople().size()>=2?"/"+family.getPeople().get(1).getFirst():"";
		String last = family.getPeople().get(0).getLast();
		JButton btn = new JButton(first+middle+" "+last);
		btn.addActionListener(l->firePropertyChange(COUPLE_SELECTED, null, new ListModel(Collections.singletonList(family),Collections.emptyList())));
		return btn;
	}
}