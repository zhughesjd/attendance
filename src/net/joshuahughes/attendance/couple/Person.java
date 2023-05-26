package net.joshuahughes.attendance.couple;

import java.time.LocalDateTime;

public class Person
{
	private String first;
	private String last;
	private LocalDateTime birth;
	LocalDateTime lastAttended;
	private Couple couple;

	public Person(String first, String last, LocalDateTime birth, LocalDateTime lastAttended, Couple couple)
	{
		this.first = first;
		this.last = last;
		this.birth = birth;
		this.couple = couple;
		this.lastAttended  = lastAttended;
	}
	public String getFirst(){return first;}
	public String getLast(){return last;}
	public Couple getCouple(){return couple;}
	public LocalDateTime getBirth(){return birth;}
	public LocalDateTime lastAttended(){return lastAttended;}
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
}
