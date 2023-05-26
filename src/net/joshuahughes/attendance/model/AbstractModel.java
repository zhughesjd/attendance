package net.joshuahughes.attendance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.joshuahughes.attendance.couple.Couple;
import net.joshuahughes.attendance.couple.Person;

public abstract class AbstractModel implements Model
{
	protected SortedSet<Couple> enrolled = new TreeSet<>();
	protected SortedSet<Couple> retired = new TreeSet<>();
	LinkedHashMap<Status,SortedSet<Couple>> map = new LinkedHashMap<>();
	public AbstractModel()
	{
		map.put(Status.enrolled, enrolled);
		map.put(Status.retired, retired);
	}
	@Override
	public List<Couple> getCouples(Status status,Comparator<Couple> cmp)
	{
		ArrayList<Couple> list = new ArrayList<Couple>(map.get(status));
		Collections.sort(list, cmp);
		return list;
	}
	@Override
	public boolean add(Couple couple, Status status)
	{
		return map.get(status).add(couple);
	}
	@Override
	public boolean remove(Couple couple, Status status)
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
