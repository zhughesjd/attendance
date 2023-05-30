package net.joshuahughes.attendance.family;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Family implements Comparable<Family>
{
	public static String header = "husbandFirst	husbandLast	husbandBirth	wifeFirst	wifeLast	wifeBirth	anniversary	lastAttended";
	ArrayList<Person> people = new ArrayList<>();
	LocalDateTime anniversary;

	public Family(String husbandFirst,String husbandLast,LocalDateTime husbandBirth,LocalDateTime husbandLastAttended,String wifeFirst,String wifeLast,LocalDateTime wifeBirth,LocalDateTime wifeLastAttended,LocalDateTime anniversary)
	{
		people.add(new Person(husbandFirst,husbandLast,husbandBirth,husbandLastAttended));
		people.add(new Person(wifeFirst,wifeLast,wifeBirth,wifeLastAttended));
		people.get(0).setFamily(this);
		people.get(1).setFamily(this);
		this.anniversary = anniversary;
	}
	public LocalDateTime getAnniversary(){return anniversary;}
	public LocalDateTime getLastAttended()
	{
		return getPeople().stream().sorted(Person.lastAttendedDecending).findFirst().get().getLastAttended();
	}
	@Override
	public int compareTo(Family that)
	{
		return alphabetical.compare(this, that);
	}
	public String toString()
	{
		Person p0 = people.get(0);
		String p2f = people.size()>1 ? "/"+people.get(1) : "";
		return p0.getFirst()+p2f+" "+p0.getLast()+" "+getAnniversary().toString().split("T")[0];
	}
	public List<Person> getPeople()
	{
		return new ArrayList<>(this.people);
	}
	public static final Comparator<Family> alphabetical = new Comparator<Family>() {

		@Override
		public int compare(Family cA,Family cB)
		{
			int result = cA.getPeople().get(0).getLast().compareTo(cB.getPeople().get(0).getLast());
			if(result !=0 ) return result;
			for(int i=0;i<Math.min(cA.getPeople().size(), cB.getPeople().size());i++)
			{
				result = cA.getPeople().get(i).getFirst().compareTo(cB.getPeople().get(i).getFirst());
				if(result !=0 ) return result;
			}
			return cA.getAnniversary().compareTo(cB.getAnniversary());
		}
	};
	public static final Comparator<Family> lastAttended = new Comparator<Family>() 
	{
		@Override
		public int compare(Family ca,Family cb)
		{
			return ca.getLastAttended().compareTo(cb.getLastAttended());
		}
	};
	public static final Comparator<Family> anniversaryComparator = new Comparator<Family>() 
	{
		@Override
		public int compare(Family ca,Family cb)
		{
			return ca.getAnniversary().compareTo(cb.getAnniversary());
		}
	};
}
