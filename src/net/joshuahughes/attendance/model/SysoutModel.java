package net.joshuahughes.attendance.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.joshuahughes.attendance.Utility;
import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;

public class SysoutModel extends AbstractModel
{
	public SysoutModel()
	{
		enrolled.addAll(read());
	}
	@Override
	public void finishCheckin(Person person)
	{
		super.finishCheckin(person);
		write();
	}
	private void write() 
	{
		write(enrolled,"member."+LocalDateTime.now().toString().split("T")[0]+".txt");
	 }
	@Override
	protected void arrivalTextAlert(Person person)
	{
		Utility.sendTexts(person);
	}
	@Override
	protected void printTag(Person person)
	{
		int anvDiff = person.getFamily().getAnniversary().getDayOfYear() - LocalDateTime.now().getDayOfYear();
		boolean isAnv = anvDiff <= 0 && anvDiff < 7;
		String append = isAnv ? "Happy Anniversary!!!" : "";
		System.out.println("printing tag for "+person.getFirst()+" "+person.getLast() +" "+ append);
	}


	public static Comparator<String> reverseString = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o2.compareTo(o1);
		}
	};

	private static final SortedSet<Family> read() 
	{
		TreeSet<Family> sorted = new TreeSet<>();
		File[] files = new File("./").listFiles(f->f.getName().startsWith("members.txt"));
		String filepath = Arrays.stream(files).map(f->f.getName()).sorted(reverseString).findFirst().get();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] array = line.split("\t");
				sorted.add(new Family
						(
								array[0], array[1], LocalDateTime.parse(array[2] + "T00:00:00"),LocalDateTime.parse(array[3] + "T00:00:00"),
								array[4], array[5], LocalDateTime.parse(array[6] + "T00:00:00"),LocalDateTime.parse(array[7] + "T00:00:00"),
								LocalDateTime.parse(array[8] + "T00:00:00")
						));
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sorted;
	}
	private void write(Collection<Family> couples,String filepath) 
	{
		try 
		{
			String delimiter = "\t";
			PrintStream ps = new PrintStream(new FileOutputStream(filepath));
			ps.println(Family.header);
			couples.forEach
			(
				c->ps.println(String.join(delimiter,
				toString(c.getHusband(),delimiter),
				toString(c.getWife(),delimiter),
				c.getAnniversary().toString().split("T")[0]
			)));
			ps.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private String toString(Person person,String delimiter)
	{
		return String.join(delimiter, person.getFirst(),person.getLast(),person.getBirth().toString().split("T")[0],person.lastAttended().toString().split("T")[0]);
	}
}
