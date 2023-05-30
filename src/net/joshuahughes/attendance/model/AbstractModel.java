package net.joshuahughes.attendance.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public abstract class AbstractModel implements Model
{
	protected SortedSet<Family> enrolled = new TreeSet<>();
	LinkedHashMap<Status,SortedSet<Family>> map = new LinkedHashMap<>();
	public AbstractModel()
	{
		map.put(Status.member, enrolled);
	}
	@Override
	public List<Family> getFamilies()
	{
		ArrayList<Family> list = new ArrayList<>(enrolled);
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
