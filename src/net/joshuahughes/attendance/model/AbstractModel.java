package net.joshuahughes.attendance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public abstract class AbstractModel implements Model
{
	protected SortedSet<Family> enrolled = new TreeSet<>();
	protected SortedSet<Family> retired = new TreeSet<>();
	LinkedHashMap<Status,SortedSet<Family>> map = new LinkedHashMap<>();
	public AbstractModel()
	{
		map.put(Status.enrolled, enrolled);
		map.put(Status.retired, retired);
	}
	@Override
	public List<Family> getCouples(Status status,Comparator<Family> cmp)
	{
		ArrayList<Family> list = new ArrayList<Family>(map.get(status));
		Collections.sort(list, cmp);
		return list;
	}
	@Override
	public boolean add(Family couple, Status status)
	{
		return map.get(status).add(couple);
	}
	@Override
	public boolean remove(Family couple, Status status)
	{
		return map.get(status).remove(couple);
	}
	@Override
	public void finishCheckin(Person person)
	{
		printTag(person);
		arrivalTextAlert(person);
	}
	protected abstract void arrivalTextAlert(Person person);
	protected abstract void printTag(Person person);
}
