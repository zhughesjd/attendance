package net.joshuahughes.attendance.family;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Family implements Comparable<Family>
{
	public static String header = "husbandFirst	husbandLast	husbandBirth	wifeFirst	wifeLast	wifeBirth	anniversary	lastAttended";
	Person husband;
	Person wife;
	LocalDateTime anniversary;

	public Family(String husbandFirst,String husbandLast,LocalDateTime husbandBirth,LocalDateTime husbandLastAttended,String wifeFirst,String wifeLast,LocalDateTime wifeBirth,LocalDateTime wifeLastAttended,LocalDateTime anniversary)
	{
		this.husband = new Person(husbandFirst,husbandLast,husbandBirth,husbandLastAttended,this);
		this.wife = new Person(wifeFirst,wifeLast,wifeBirth,wifeLastAttended,this);
		this.anniversary = anniversary;
	}
	public Person getHusband(){return husband;}
	public Person getWife(){return wife;}
	public LocalDateTime getAnniversary(){return anniversary;}
	public LocalDateTime getLastAttended()
	{
		LocalDateTime lastAttended = getHusband().getLastAttended();
		if(getWife().getLastAttended().isAfter(lastAttended))
			lastAttended = getWife().getLastAttended();
		return lastAttended;
	}
	@Override
	public int compareTo(Family that)
	{
		return alphabetical.compare(this, that);
	}
	public String toString()
	{
		return husband.getFirst()+"/"+wife.getFirst()+" "+husband.getLast()+" "+getLastAttended().toString().split("T")[0];
	}
	public static final Comparator<Family> alphabetical = new Comparator<Family>() {

		@Override
		public int compare(Family cA,Family cB)
		{
			int result = cA.getHusband().getLast().compareTo(cB.getHusband().getLast());
			if(result !=0 ) return result;
			result = cA.getHusband().getFirst().compareTo(cB.getHusband().getFirst());
			if(result !=0 ) return result;
			result = cA.getWife().getLast().compareTo(cB.getWife().getLast());
			if(result !=0 ) return result;
			result = cA.getWife().getFirst().compareTo(cB.getWife().getFirst());
			if(result !=0 ) return result;
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
