package net.joshuahughes.attendance.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.joshuahughes.attendance.family.Family;

public abstract class AbstractModel implements Model
{
	protected SortedSet<Family> enrolled = new TreeSet<>();
	ArrayList<Family> list = new ArrayList<>();
	@Override
	public List<Family> getFamilies()
	{
		ArrayList<Family> list = new ArrayList<>(enrolled);
		return list;
	}
	@Override
	public boolean add(Family family)
	{
		return list.add(family);
	}
	@Override
	public boolean remove(Family family)
	{
		return list.remove(family);
	}
}
