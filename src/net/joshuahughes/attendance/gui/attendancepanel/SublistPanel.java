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
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		AtomicInteger x = new AtomicInteger();
		AtomicInteger y = new AtomicInteger();
		list.forEach(f->
		{
			gbc.gridx = x.getAndIncrement();
			gbc.gridy = y.get();
			couplesPnl.add(createButton(f),gbc);
			if(x.get() >= 2)
			{
				y.incrementAndGet();
				x.set(0);
			}
		});
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