package net.joshuahughes.attendance;

import net.joshuahughes.attendance.gui.AttendanceDialog;
import net.joshuahughes.attendance.model.Model;
import net.joshuahughes.attendance.model.SysoutModel;

public class Attendance 
{

	public static void main(String[] args)
	{
		Model model = new SysoutModel();
		AttendanceDialog dlg = new AttendanceDialog(model);
		dlg.setSize(800,800);
		dlg.setVisible(true);
	}
}
