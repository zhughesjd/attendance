package net.joshuahughes.attendance.model;

import java.util.List;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public class ListModel extends AbstractModel{

	public ListModel(List<Family> enrolled,List<Family> retired)
	{
		this.enrolled.addAll(enrolled);
	}

	@Override
	public void process(Person person){}
}
