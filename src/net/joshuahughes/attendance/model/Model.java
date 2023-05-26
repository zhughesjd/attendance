package net.joshuahughes.attendance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.joshuahughes.attendance.couple.Couple;
import net.joshuahughes.attendance.couple.Person;

public interface Model
{
	public enum Status{enrolled,retired}

	public List<Couple> getCouples(Status status,Comparator<Couple> cmp);
    public boolean add(Couple couple,Status status);
	public boolean remove(Couple couple,Status status);
	public void finishCheckin(Person person);

	public default List<Person> getPeople(Status status,Comparator<Couple> cmp)
	{
		ArrayList<Person> list = new  ArrayList<>();
		List<Couple> cs = getCouples(status, cmp);
		cs.stream().forEach(c->
		{
			list.add(c.getHusband());
			list.add(c.getWife());
		});
		return list ;
	}
	public default boolean enroll(Couple couple)
	{
		remove(couple, Status.retired);
		add(couple, Status.enrolled);
		return true;
	}
	public default boolean retire(Couple couple)
	{
		remove(couple, Status.enrolled);
		add(couple, Status.retired);
		return true;
	}
	public default void checkIn(Person person)
	{
		person.setLastAttended(LocalDateTime.now());
		finishCheckin(person);
	}
}
