import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class boardBuild extends JFrame{

	private static final int ROWS = 10;
	private static final int COLM = 10;


	public myButton[][] cells = new myButton[ROWS][COLM];
	public static int[][] minefield = new int[ROWS][COLM];
	private JButton resetButton = new JButton("Reset");


	private static int NUM_EMPTY_CELLS = 90;
	public static boolean bomb_trip = false;

	public boardBuild(){
		super("Minesweeper");


		//creating minefield gridlayout and panel 
		GridLayout grid1 = new GridLayout(10,10);
		JPanel cellfield = new JPanel();
		
		//setting layout to minefield panel
		cellfield.setLayout(grid1);

		try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

		//ButtonHandler handler = new ButtonHandler();

		//adding all the cells into the panel
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				cells[i][j] = new myButton(i,j, "");
				cellfield.add(cells[i][j]);
			}
		}
		generateMines();


		JPanel infoField = new JPanel();
		infoField.setLayout(new FlowLayout());

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMines();
			}
		});

		infoField.add(resetButton);

		//setting up window content to border layout
		Container win_container = getContentPane();
		win_container.setLayout(new BorderLayout());


		win_container.add(cellfield, BorderLayout.CENTER);
		win_container.add(infoField, BorderLayout.NORTH);
		setSize(450, 500);
		setVisible(true);
		setResizable(false);
	}

	public void generateMines(){
		int BOMB_VALUE = 99;
		int NUM_OF_MINES = 10;
		myButton.bomb_trip = false;
		cellReset();
		//fills minefield with all 0s
		for(int [] row: minefield)
			Arrays.fill(row, 0);

		Random generator = new Random();
		int num = generator.nextInt(100);

		for(int i = 0; i < NUM_OF_MINES; i++){
			minefield[num / 10][num % 10] = BOMB_VALUE;
			num = generator.nextInt(100);
		}

		//marks the minefield to tell the user how
		//many bombs surround this cell

		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				if(minefield[i][j] >= BOMB_VALUE){
					if(j != 9) // East
						minefield[i][j + 1] += 1;
					if(j != 0) //West
						minefield[i][j - 1] += 1;
					if(i != 0) //North
						minefield[i - 1][j] += 1;
					if(i != 9) //South
						minefield[i + 1][j] += 1;
					if(i != 0 && j != 0 ) //North west
						minefield[i - 1][j - 1] += 1;
					if(i != 0 && j != 9) // North east
						minefield[i - 1][j + 1] += 1;
					if(i != 9 && j != 0) // South West
						minefield[i + 1][j - 1] += 1;
					if(i != 9 && j != 9)// South east
						minefield[i + 1][j + 1] += 1;

				}
			}
		}
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				System.out.println(i + "," + j + " " + minefield[i][j]);
			}
		}
	}
	public void cellReset(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				cells[i][j].setBackground(null);
				cells[i][j].setForeground(null);
				cells[i][j].setText("");
				cells[i][j].setToggle();
			}
		}
		NUM_EMPTY_CELLS = 90;
	}
	
	public static void emptyCellsDecr(){
		NUM_EMPTY_CELLS--;
		System.out.println("Empty cells remaining: " + NUM_EMPTY_CELLS);
	}
	public static void main (String args[]){
		boardBuild application = new boardBuild();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
