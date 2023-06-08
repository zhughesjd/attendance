package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
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
		char firstLetter = 'A';
		int groupSize = rowCnt*colCnt;
		List<Family> list = model.getFamilies(Family.alphabetical);
		
		TreeMap<String,List<Family>> map = new TreeMap<>();
		while(firstLetter<='Z')
		{
			char first = firstLetter;
			char last = (char) Math.min ('Z',first+rowCnt-1);
			List<Family> subList = list.stream().filter(f->first<= f.getPeople().get(0).getLast().charAt(0) && f.getPeople().get(0).getLast().charAt(0) <= last).collect(Collectors.toList());
			map.put(first+"-"+last, subList);
			firstLetter+=rowCnt;
		}
		cntrPnl.removeAll();
		cntrPnl.setLayout(new GridBagLayout());
		AtomicInteger ndx = new AtomicInteger(0);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		Iterator<Entry<String, List<Family>>> it = map.entrySet().iterator();
		for(int y=0;y<colCnt;y++)
		{
			for(int x=0;x<rowCnt;x++)
			{
				if(!it.hasNext()) continue;
				Entry<String, List<Family>> e = it.next();
				System.out.println();
				gbc.gridx = x;
				gbc.gridy = y;
				char secondLetter = list.get(groupSize*ndx.getAndIncrement()).getPeople().get(0).getLast().charAt(0);
				if(ndx.get() == rowCnt*colCnt) secondLetter = 'Z';
				JButton btn = addButton(e.getKey(),e.getValue());
				cntrPnl.add(btn,gbc);
				firstLetter = (char) (secondLetter+1);
			}
		}
	}
	private JButton addButton(String text,List<Family> list)
	{
		JButton btn = new JButton(text);
		btn.addActionListener(l->firePropertyChange(SUBLIST_SELECTED, null, new ListModel(list, Collections.emptyList())));
		return btn;
	}
}
