import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menubar extends JMenuBar{

	public menubar(){
		super();

		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.setMnemonic('A');

		helpMenu.add(aboutItem);

		aboutItem.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent event){
				JOptionPane.showMessageDialog(null, 
					"This minesweeper program was made by\nAdrian and Upesh for CS342.\n",
					"About", JOptionPane.PLAIN_MESSAGE);
				//System.out.println("ARGGH!!");
			}
		});

		add(helpMenu);
	}
}