package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.model.ListModel;
import net.joshuahughes.attendance.model.Model;

public class LetterPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2746586552461380870L;
	public static final String SUBLIST_SELECTED = "sublist selected";
	int rowCnt = 3;
	int colCnt = 3;
	public LetterPanel()
	{
		super("Select the first letter of your last name");
		backBtn.setText(" ");
		backBtn.setOpaque(true);
		backBtn.setBackground(this.getBackground());
		backBtn.setContentAreaFilled(false);
		backBtn.setBorder(BorderFactory.createLineBorder(this.getBackground()));;
	}
	public void populate(Model model)
	{
		
		cntrPnl.removeAll();
		cntrPnl.setLayout(new GridBagLayout());
		char firstLetter = 'A';
		AtomicInteger ndx = new AtomicInteger(0);
		List<Family> list = model.getFamilies(Family.alphabetical);
		int charGroupSize = list.size()/(rowCnt*colCnt);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		
		for(int y=0;y<colCnt;y++)
		{
				for(int x=0;x<rowCnt;x++)
			{
				gbc.gridx = x;
				gbc.gridy = y;
				char secondLetter = list.get(charGroupSize*ndx.getAndIncrement()).getPeople().get(0).getLast().charAt(0);
				if(ndx.get() == rowCnt*colCnt) secondLetter = 'Z';
				JButton btn = addButton(firstLetter,secondLetter,list);
				cntrPnl.add(btn,gbc);
				firstLetter = (char) (secondLetter+1);
			}
		}
	}
	private JButton addButton(char fLtr, char sLtr,List<Family> list)
	{
		JButton btn = new JButton(fLtr + (fLtr==sLtr?"":"-"+sLtr));
		btn.addActionListener(l->firePropertyChange(SUBLIST_SELECTED, null, model(fLtr,sLtr,list)));
		return btn;
	}
	private Model model(char c0,char c1,List<Family> list)
	{
	   List<Family> l = list.stream().filter(c -> c0<=c.getPeople().get(0).getLast().charAt(0) && c.getPeople().get(0).getLast().charAt(0)<=c1).collect(Collectors.toList());
	   return new ListModel(l, Collections.emptyList());
	}
}
