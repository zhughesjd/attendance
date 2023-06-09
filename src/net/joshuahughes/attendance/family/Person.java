package net.joshuahughes.attendance.family;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Person
{
	private String first;
	private String last;
	private LocalDateTime birth;
	private LocalDateTime lastAttended;
	private Family family;
	private boolean leader = false;
	public Person(String first, String last, LocalDateTime birth, LocalDateTime lastAttended)
	{
		this.first = first;
		this.last = last;
		this.birth = birth;
		this.lastAttended  = lastAttended;
		this.leader = false;
	}
	public String getFirst(){return first;}
	public String getLast(){return last;}
	public Family getFamily(){return family;}
	public void setFamily(Family family) {this.family = family;}
	public LocalDateTime getBirth(){return birth;}
	public LocalDateTime lastAttended(){return lastAttended;}
	public boolean leader(){return leader;}
    public void setLastAttended(LocalDateTime ldt){this.lastAttended = ldt;}

	public String toString()
	{
		String f  = first==null?"null":first;
		String l  = last==null?"null":last;
		String b  = birth==null?"null":birth.toString().split("T")[0];
		return String.join(" ",f,l,b);
	}
	public LocalDateTime getLastAttended()
	{
		return this.lastAttended;
	}
	public static final Comparator<Person> sundaysAbsent = new Comparator<Person>()
	{
		
		@Override
		public int compare(Person p1, Person p2)
		{
			return p1.getLastAttended().compareTo(p2.getLastAttended());
		}
		public String toString() { return "sundays absent";}
	};
	
	public static final Comparator<Person> lastNameFirst = new Comparator<Person>()
	{
		
		@Override
		public int compare(Person p1, Person p2)
		{
			return (p1.getLast()+" "+p1.getFirst()).compareTo(p2.getLast()+" "+p2.getFirst());
		}
		public String toString() { return "last name";}
	};
	
	
			
}
