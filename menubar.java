import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menubar extends JMenuBar{

	public menubar(){
		super();

		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help");

		gameMenu.setMnemonic('G');

		JMenuItem resetItem = new JMenuItem("Reset");
		JMenuItem topTenItem = new JMenuItem("Top Ten");
		JMenuItem resetTopTen = new JMenuItem("Reset Top Ten");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem aboutItem = new JMenuItem("About...");
		JMenuItem helpItem = new JMenuItem("Help");

		resetItem.setMnemonic('R');
		topTenItem.setMnemonic('T');
		exitItem.setMnemonic('E');
		helpItem.setMnemonic('H');
		aboutItem.setMnemonic('A');

		gameMenu.add(resetItem);
		gameMenu.add(topTenItem);
		gameMenu.add(resetTopTen);
		gameMenu.add(exitItem);
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);

		resetItem.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent event){
				myBoard.resetClick();
			}
		});

		resetTopTen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				score.resetScores();
			}
		});

		topTenItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				score.showTopTen();
			}
		});

		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent event){
				System.exit(0);
			}
		});

		aboutItem.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent event){
				JOptionPane.showMessageDialog(null, 
					"This minesweeper program was made by\nAdrian and Upesh for CS342.\n",
					"About", JOptionPane.PLAIN_MESSAGE);
				//System.out.println("ARGGH!!");
			}
		});

		helpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JOptionPane.showMessageDialog(null,
					"To play the game, try to find all the places that do not have mines.\n" +
					"Right click on the buttons to indiciate there is a mine in that space.\n" +
					"Click on a mine and you lose. Clear the board, with no mine hits and you win.\n",
					"Help", JOptionPane.PLAIN_MESSAGE);
			}
		});

		add(gameMenu);
		add(helpMenu);
	}

	public void openTopTen(){

	}
}