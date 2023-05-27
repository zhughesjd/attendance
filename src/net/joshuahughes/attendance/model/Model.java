package net.joshuahughes.attendance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public interface Model
{
	public enum Status{enrolled,retired}

	public List<Family> getCouples(Status status,Comparator<Family> cmp);
    public boolean add(Family couple,Status status);
	public boolean remove(Family couple,Status status);
	public void finishCheckin(Person person);

	public default List<Person> getPeople(Status status,Comparator<Family> cmp)
	{
		ArrayList<Person> list = new  ArrayList<>();
		List<Family> cs = getCouples(status, cmp);
		cs.stream().forEach(c->
		{
			list.add(c.getHusband());
			list.add(c.getWife());
		});
		return list ;
	}
	public default boolean enroll(Family couple)
	{
		remove(couple, Status.retired);
		add(couple, Status.enrolled);
		return true;
	}
	public default boolean retire(Family couple)
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
