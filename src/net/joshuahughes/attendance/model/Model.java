package net.joshuahughes.attendance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public interface Model
{
	public enum Status{leader,member,assigned,visitor}
	public List<Family> getFamilies();
    public boolean add(Family couple,Status status);
	public boolean remove(Family couple,Status status);

	public default List<Family> getFamilies(Comparator<Family> cmp)
	{
		ArrayList<Family> list = new ArrayList<>(getFamilies());
		Collections.sort(list, cmp);
		return list;
	}
	public default List<Person> getPeople()
	{
		ArrayList<Person> people = new ArrayList<>();
		getFamilies().forEach(f->f.getPeople().stream().forEach(p->people.add(p)));
		return people;
	}
	public default List<Person> getPeople(Comparator<Person> cmp)
	{
		ArrayList<Person> list = new ArrayList<Person>(getPeople());
		Collections.sort(list, cmp);
		return list;
	}
	public default List<Person> getPeople(Status status,Comparator<Family> cmp)
	{
		ArrayList<Person> list = new  ArrayList<>();
		List<Family> fs = getFamilies(cmp);
		fs.stream().forEach(f->
		{
			f.getPeople().stream().forEach(p->list.add(p));
		});
		return list ;
	}
	public void process(Person person);
}
