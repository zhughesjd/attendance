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
	protected void arrivalTextAlert(Person person) {
		
	}

	@Override
	protected void printTag(Person person) {
		// TODO Auto-generated method stub
		
	}

}
