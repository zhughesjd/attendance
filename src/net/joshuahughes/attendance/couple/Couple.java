package net.joshuahughes.attendance.couple;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Couple implements Comparable<Couple>
{
	public static String header = "husbandFirst	husbandLast	husbandBirth	wifeFirst	wifeLast	wifeBirth	anniversary	lastAttended";
	Person husband;
	Person wife;
	LocalDateTime anniversary;

	public Couple(String husbandFirst,String husbandLast,LocalDateTime husbandBirth,LocalDateTime husbandLastAttended,String wifeFirst,String wifeLast,LocalDateTime wifeBirth,LocalDateTime wifeLastAttended,LocalDateTime anniversary)
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
	public int compareTo(Couple that)
	{
		return alphabetical.compare(this, that);
	}
	public String toString()
	{
		return husband.getFirst()+"/"+wife.getFirst()+" "+husband.getLast()+" "+getLastAttended().toString().split("T")[0];
	}
	public static final Comparator<Couple> alphabetical = new Comparator<Couple>() {

		@Override
		public int compare(Couple cA,Couple cB)
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
	public static final Comparator<Couple> lastAttended = new Comparator<Couple>() 
	{
		@Override
		public int compare(Couple ca,Couple cb)
		{
			return ca.getLastAttended().compareTo(cb.getLastAttended());
		}
	};
	public static final Comparator<Couple> anniversaryComparator = new Comparator<Couple>() 
	{
		@Override
		public int compare(Couple ca,Couple cb)
		{
			return ca.getAnniversary().compareTo(cb.getAnniversary());
		}
	};
}
