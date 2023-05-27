package net.joshuahughes.attendance.family;

import java.time.LocalDateTime;

public class Person
{
	private String first;
	private String last;
	private LocalDateTime birth;
	private LocalDateTime lastAttended;
	private Family family;
	private boolean leader = false;

	public Person(String first, String last, LocalDateTime birth, LocalDateTime lastAttended, Family couple)
	{
		this.first = first;
		this.last = last;
		this.birth = birth;
		this.family = couple;
		this.lastAttended  = lastAttended;
		this.leader = false;
	}
	public String getFirst(){return first;}
	public String getLast(){return last;}
	public Family getFamily(){return family;}
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
}
