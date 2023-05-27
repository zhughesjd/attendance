package net.joshuahughes.attendance;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.joshuahughes.attendance.family.Family;
import net.joshuahughes.attendance.family.Person;
import net.joshuahughes.attendance.gui.attendancepanel.TextPanel.Leader;
import net.joshuahughes.attendance.model.Model;
import net.joshuahughes.attendance.model.Model.Status;

public class Utility
{
	public static String leadershipFilepath = "leadership.txt";
	
	
	public static void printRoster(Model model)
	{
		ArrayList<Family> list = new ArrayList<>(model.getCouples(Status.enrolled,Family.alphabetical));
		while(list.size()>0)
		{
			print(removeThenCreate(list));
		}
	}
	public static Vector<Leader> read() 
	{
		Vector<Leader> vector = new Vector<>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(leadershipFilepath ));
			String line = null;
			while((line = br.readLine())!=null)
			{
				AtomicInteger ndx = new AtomicInteger();
				String[] array = line.split("\t");
				Leader leader = new Leader();
				leader.name = array[ndx.getAndIncrement()];
				leader.number = array[ndx.getAndIncrement()];
				leader.members = new ArrayList<>(IntStream.range(ndx.get(),array.length).mapToObj(i->array[i]).collect(Collectors.toList()));
				vector.add(leader);
			}
			br.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return vector;
	}
	public static void write(Collection<Leader> leaders)
	{
		try
		{
			PrintStream ps = new PrintStream(new FileOutputStream(new File(leadershipFilepath)));
			leaders.stream().forEach(ldr->
			{
				String ms = String.join("\t",ldr.members.toArray(new String[] {}));
				ps.println(String.join("\t",ldr.name,ldr.number,ms));
			});
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendTexts(Person person) 
	{
		read().stream().forEach(ldr->
		{
			String name = person.getFirst()+" "+person.getLast();
			if(ldr.members.contains(name))
				sendText(ldr.number,ldr.name+" ... "+name+" has signed in to Joint Venture");
		});
	}
	public static void sendText(String cellNumber, String message)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("apikey.txt"));
			String apiKey = br.readLine().trim();
			br.close();

			final NameValuePair[] data = {
			    new BasicNameValuePair("phone", cellNumber),
			    new BasicNameValuePair("message", message),
			    new BasicNameValuePair("key", apiKey)
			};
			HttpClient httpClient = HttpClients.createMinimal();
			HttpPost httpPost = new HttpPost("https://textbelt.com/text");
			httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(data)));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			String responseString = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(responseString);
		}
		catch(Exception e)
		{
			System.out.println("error on sending text");
			e.printStackTrace();
		}
	}

	private static BufferedImage removeThenCreate(List<Family> list)
	{
		int coupleCnt = 20;
		BufferedImage img = new BufferedImage(850, 1000, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = img.createGraphics();
		g2d.setClip(0, 0, img.getWidth(), img.getHeight());
		g2d.setBackground(Color.white);
		g2d.setColor(Color.white);
	    g2d.clearRect(0, 0, img.getWidth(), img.getHeight());
		g2d.setBackground(Color.white);
		g2d.setColor(Color.black);
	    g2d.drawLine(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight());

	    List<Family> sublist = new ArrayList<>();
	    IntStream.range(0, Math.min(coupleCnt, list.size())).forEach(i->sublist.add(list.remove(0)));
	    int xOffset = 10;
	    draw(g2d,sublist,xOffset);

	    sublist.clear();
	    IntStream.range(0, Math.min(coupleCnt, list.size())).forEach(i->sublist.add(list.remove(0)));
	    xOffset += img.getWidth()/2;
	    draw(g2d,sublist,xOffset);
	
	    return img;
	}

	private static void draw(Graphics2D g2d,List<Family> roster,int xOffset)
	{
		String today = LocalDateTime.now().toString().split("T")[0];
		g2d.setColor(Color.black);
		int rectDim = 15;
		int offset = 10;
		AtomicInteger yNdx = new AtomicInteger(10);
		IntStream.range(0, roster.size()).forEach(i->
		{
			Person[] ps = new Person[] {roster.get(i).getHusband(),roster.get(i).getWife()};
			IntStream.range(0, ps.length).forEach(pNdx->
			{
				Person p = ps[pNdx];
				String la = p.lastAttended().toString().split("T")[0];
				if(today.equals(la))
					g2d.fillRect(xOffset, yNdx.get(), 10, 10);
				else
					g2d.drawRect(xOffset, yNdx.get(), 10, 10);
				g2d.drawString( p.getFirst()+" "+p.getLast(), xOffset+rectDim,yNdx.get()+offset);
				yNdx.getAndAdd(15);
			});
		});
	}

	public static void print(BufferedImage image)
	{
		JOptionPane.showInputDialog(new ImageIcon(image));
		int x = 3; if(x == 3) return;
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(new Printable() {
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex != 0) {
					return NO_SUCH_PAGE;
				}
				graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
				return PAGE_EXISTS;
			}
		});     
		try {
			printJob.print();
		} catch (PrinterException e1) {             
			e1.printStackTrace();
		}}

}
