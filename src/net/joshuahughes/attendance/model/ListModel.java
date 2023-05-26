package net.joshuahughes.attendance.model;

import java.util.List;

import net.joshuahughes.attendance.couple.Couple;
import net.joshuahughes.attendance.couple.Person;

public class ListModel extends AbstractModel{

	public ListModel(List<Couple> enrolled,List<Couple> retired)
	{
		this.enrolled.addAll(enrolled);
		this.retired.addAll(retired);
	}
	@Override
	protected void arrivalTextAlert(Person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void printTag(Person person) {
		// TODO Auto-generated method stub
		
	}

}
