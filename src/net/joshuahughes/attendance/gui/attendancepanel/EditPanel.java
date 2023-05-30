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

import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.model.Model;

public class EditPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2277598753023544842L;
	
	DefaultListModel<Person> enrolled  = new DefaultListModel<>();
	DefaultListModel<Person> retire  = new DefaultListModel<>();
	BasicArrowButton toRetired = new BasicArrowButton(BasicArrowButton.EAST);
	BasicArrowButton toEnrolled = new BasicArrowButton(BasicArrowButton.WEST);
	JList<Person> enrolledList = new JList<>(enrolled);
	JList<Person> retiredList = new JList<>(retire);
	public EditPanel() 
	{
		super("Family Analysis");
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
				retire.addElement(c);
				sort(retire,Person.LastAttended.reversed());
			});
			
		});
		toEnrolled.addActionListener(e->
		{
			retiredList.getSelectedValuesList().stream().forEach(c->
			{
				retire.removeElement(c);
				enrolled.addElement(c);
				sort(enrolled,Person.LastAttended);
			});
			
		});
	}
	@Override
	public void populate(Model model)
	{
		enrolled.clear();
		retire.clear();
		model.getPeople(Person.LastAttended).stream().forEach(c->enrolled.addElement(c));
		
	}
	public static <T> void sort(DefaultListModel<T> m,Comparator<T> c)
	{
		ArrayList<T> list = new ArrayList<>(IntStream.range(0, m.size()).mapToObj(i->m.get(i)).collect(Collectors.toList()));
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
                 if (value instanceof Person) 
                 {
                      Person person = (Person) value;
                	  setText(person.getFirst()+" "+person.getLast()+" "+person.getLastAttended());

                      int i = Math.min(255,(int) Duration.between(LocalDateTime.now(), person.getLastAttended()).abs().toDays());
                      setBackground(new Color(255,255-i,255-i));
                 }
                 return c;
            }

       };
	}
 }
