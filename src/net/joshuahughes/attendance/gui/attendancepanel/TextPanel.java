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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicArrowButton;

import net.joshuahughes.attendance.Utility;
import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.model.Model;
import net.joshuahughes.attendance.model.Model.Status;

public class TextPanel extends AttendancePanel
{
	private static final long serialVersionUID = -2277598753023544842L;
	
	
	JComboBox<Leader> leaderBox = new JComboBox<>(Utility.read());
	DefaultListModel<Person> enrolled  = new DefaultListModel<>();
	DefaultListModel<String> text  = new DefaultListModel<>();
	BasicArrowButton toText = new BasicArrowButton(BasicArrowButton.EAST);
	BasicArrowButton toEnrolled = new BasicArrowButton(BasicArrowButton.WEST);
	JList<Person> enrolledList = new JList<>(enrolled);
	JList<String> textList = new JList<>(text);
	Model model;


	public TextPanel()
	{
		super("Text leadership upon member arrival");
		cntrPnl.setLayout(new BorderLayout());
		JPanel btnPnl = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=gbc.gridy = 0;
		btnPnl.add(toText,gbc);
		gbc.gridy++;
		btnPnl.add(toEnrolled,gbc);
		gbc.gridy++;
		btnPnl.add(leaderBox,gbc);

		cntrPnl.add(new JScrollPane(enrolledList),BorderLayout.WEST);
		cntrPnl.add(btnPnl,BorderLayout.CENTER);
		cntrPnl.add(new JScrollPane(textList),BorderLayout.EAST);
				
		
		toText.addActionListener(e->
		{
			enrolledList.getSelectedValuesList().stream().forEach(c->
			{
				enrolled.removeElement(c);
				String name = c.getFirst()+" "+c.getLast();
				text.addElement(name);
				Leader leader = (Leader) leaderBox.getSelectedItem();
				leader.members.add(name);
				write();
			});
			
		});
		toEnrolled.addActionListener(e->
		{
			LinkedHashSet<Person> people = new LinkedHashSet<>(); 
			model.getFamilies(Family.alphabetical).stream().forEach(c->
			{
				people.add(c.getHusband());
				people.add(c.getWife());
			});
			textList.getSelectedValuesList().stream().forEach(name->
			{
				Optional<Person> o = people.stream().filter(p->name.equals(p.getFirst()+" "+p.getLast())).findAny();
				if(o.isPresent())
					enrolled.addElement(o.get());
				text.removeElement(name);
				Leader leader = (Leader) leaderBox.getSelectedItem();
				leader.members.remove(name);
				write();
			});
			sort(enrolled);
		});
		leaderBox.addActionListener(e->
		{
			text.clear();
			enrolled.clear();
			Leader leader = (Leader) leaderBox.getSelectedItem();
			if(leader == null) return;
			List<Person> people = model.getPeople(Status.member, Family.alphabetical);
			leader.members.stream().forEach(s->
			{
				text.addElement(s);
				Optional<Person> o = people.stream().filter(p->s.equals(p.getFirst()+" "+p.getLast())).findAny();
				if(!o.isPresent()) return;
				people.remove(o.get());
			});
			people.stream().forEach(p->enrolled.addElement(p));
		});
		leaderBox.getModel().getElementAt(0).members.stream().forEach(m->text.addElement(m));
	}
	private void write()
	{
		Utility.write(IntStream.range(0, leaderBox.getItemCount()).mapToObj(i->leaderBox.getItemAt(i)).collect(Collectors.toList()));
	}
	@Override
	public void populate(Model model)
	{
		this.model = model;
		enrolled.clear();
		leaderBox.setSelectedItem(null);;
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
                 if (value instanceof Family) {
                      Family couple = (Family) value;
                      int i = Math.min(255,(int) Duration.between(LocalDateTime.now(), couple.getLastAttended()).abs().toDays());
                      setBackground(new Color(255,255-i,255-i));
                 }
                 return c;
            }

       };
	}

	
	
	public static void sort(DefaultListModel<Person> m)
	{
		ArrayList<Person> list = new ArrayList<>(IntStream.range(0, m.size()).mapToObj(i->m.get(i)).collect(Collectors.toList()));
		Collections.sort(list,new Comparator<Person>() 
		{
			@Override
			public int compare(Person p1, Person p2)
			{
				return (p1.getLast()+" "+p1.getFirst()).compareTo(p2.getLast()+" "+p2.getFirst());
			}
		});
		m.removeAllElements();
		list.stream().forEach(e->m.addElement(e));
	}

	
	
	
	public static class Leader
	{
		public String name;
		public String number;
		public ArrayList<String> members;
		public String toString() {return name;}
	}
 }
