package net.joshuahughes.attendance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

public class PinPanel extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 4611643848461885366L;
	private JTextField textFld = new JTextField();
	BasicArrowButton bkSpc = new BasicArrowButton(BasicArrowButton.WEST);
	JPanel numberPnl = new JPanel(new GridLayout(3, 3));

    public PinPanel()
    {
    	super(new BorderLayout());
    	JPanel northPnl = new JPanel(new BorderLayout());
    	textFld.setPreferredSize(new Dimension(100, 30));
    	bkSpc.setPreferredSize(new Dimension(50, 30));
    	northPnl.add(textFld, BorderLayout.CENTER);
    	northPnl.add(bkSpc, BorderLayout.EAST);
    	add(northPnl,BorderLayout.NORTH);
    	add(numberPnl,BorderLayout.CENTER);
        for(int i = 1; i <= 9; i++) createButton(Integer.toString(i));
        createButton("0");
        bkSpc.addActionListener(e->
        {
        	String txt = textFld.getText();
        	if(txt.isEmpty()) return;
        	txt = txt.substring(0, txt.length()-1);
        	textFld.setText(txt);        	
        });
    }

    private void createButton(String label)
    {
        JButton btn = new JButton(label);
        btn.addActionListener(this);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(50, 50));
        Font font = btn.getFont();
        float size = font.getSize() + 15.0f;
        btn.setFont(font.deriveFont(size));
        if(label.equals("0"))
        	add(btn,BorderLayout.SOUTH);
        else
        	numberPnl.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        textFld.setText(textFld.getText() + actionCommand);
    }

	public String getText() 
	{
		return textFld.getText();
	}
}