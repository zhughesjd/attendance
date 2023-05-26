package net.joshuahughes.attendance.gui.attendancepanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicArrowButton;

import net.joshuahughes.attendance.couple.Couple;
import net.joshuahughes.attendance.model.Model;
import net.joshuahughes.attendance.model.Model.Status;

public class EditPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2277598753023544842L;
	
	DefaultListModel<Couple> enrolled  = new DefaultListModel<>();
	DefaultListModel<Couple> retired  = new DefaultListModel<>();
	BasicArrowButton toRetired = new BasicArrowButton(BasicArrowButton.EAST);
	BasicArrowButton toEnrolled = new BasicArrowButton(BasicArrowButton.WEST);
	JList<Couple> enrolledList = new JList<>(enrolled);
	JList<Couple> retiredList = new JList<>(retired);
	Model model;
	public EditPanel() 
	{
		super("Enroll/Rertire Couples");
		enrolledList.setCellRenderer(create());
		retiredList.setCellRenderer(create());
		cntrPnl.setLayout(new BorderLayout());
		JPanel btnPnl = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=gbc.gridy = 0;
		btnPnl.add(toRetired,gbc);
		gbc.gridy++;
		btnPnl.add(toEnrolled,gbc);

		cntrPnl.add(new JScrollPane(enrolledList),BorderLayout.WEST);
		cntrPnl.add(btnPnl,BorderLayout.CENTER);
		cntrPnl.add(new JScrollPane(retiredList),BorderLayout.EAST);
		
		toRetired.addActionListener(e->
		{
			enrolledList.getSelectedValuesList().stream().forEach(c->
			{
				enrolled.removeElement(c);
				retired.addElement(c);
				sort(retired,Couple.lastAttended.reversed());
				model.retire(c);
			});
			
		});
		toEnrolled.addActionListener(e->
		{
			retiredList.getSelectedValuesList().stream().forEach(c->
			{
				retired.removeElement(c);
				enrolled.addElement(c);
				sort(enrolled,Couple.lastAttended);
				model.enroll(c);
			});
			
		});
	}
	@Override
	public void populate(Model model)
	{
		this.model = model;
		enrolled.clear();
		retired.clear();
		model.getCouples(Status.enrolled, Couple.lastAttended).stream().forEach(c->enrolled.addElement(c));
		model.getCouples(Status.retired, Couple.lastAttended.reversed()).stream().forEach(c->retired.addElement(c));
		
	}
	public static void sort(DefaultListModel<Couple> m,Comparator<Couple> c)
	{
		ArrayList<Couple> list = new ArrayList<>(IntStream.range(0, m.size()).mapToObj(i->m.get(i)).collect(Collectors.toList()));
		Collections.sort(list,c);
		m.removeAllElements();
		list.stream().forEach(e->m.addElement(e));
	}

	public static DefaultListCellRenderer create()
	{
		return new DefaultListCellRenderer() {

            private static final long serialVersionUID = 4708140757930731741L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) 
			{
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 if (value instanceof Couple) {
                      Couple couple = (Couple) value;
                      int i = Math.min(255,(int) Duration.between(LocalDateTime.now(), couple.getLastAttended()).abs().toDays());
                      setBackground(new Color(255,255-i,255-i));
                 }
                 return c;
            }

       };
	}
 }
