import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class myBoard extends JFrame implements ActionListener{

	private static final int ROWS = 10;
	private static final int COLM = 10;


	public static myButton[][] cells = new myButton[ROWS][COLM];

	public static int[][] minefield = new int[ROWS][COLM];
	public static generateMines mine_gen;


	private static JButton resetButton = new JButton("Reset");

	public static Timer timer;
	public static int current_time = 0;

	public static boolean time_init = false;

	private static int NUM_EMPTY_CELLS = 90;
	public static int NUM_OF_MINES = 10;
	public static boolean bomb_trip = false;
	private static JLabel flagsOnBoard = new JLabel();
	private static JLabel timeCounter = new JLabel();

	public myBoard(){
		super("Minesweeper");

		//creating minefield gridlayout and panel 
		GridLayout grid1 = new GridLayout(10,10);
		JPanel cellfield = new JPanel();


		timer = new Timer(1000, this);

		menubar bar = new menubar();
		setJMenuBar(bar);


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


		mine_gen = new generateMines(minefield);

		flagsOnBoard.setText("" + NUM_OF_MINES);
		flagsOnBoard.setBorder(new EmptyBorder(10,10,10,10));

		timeCounter.setText("" + 0);
		timeCounter.setBorder(new EmptyBorder(10,10,10,10));

		JPanel infoField = new JPanel();
		infoField.setLayout(new BorderLayout());

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mine_gen = new generateMines(minefield);
				cellReset();
				current_time = 0;
				time_init = false;
				timer.stop();
				updateMineCount(10);
				timeCounter.setText("" + current_time);
			}
		});

		JPanel resetpanel = new JPanel(new FlowLayout());
		resetpanel.add(resetButton);

		infoField.add(flagsOnBoard, "West");
		infoField.add(resetpanel, "Center");
		infoField.add(timeCounter, "East");

		//setting up window content to border layout
		Container win_container = getContentPane();
		win_container.setLayout(new BorderLayout());


		win_container.add(cellfield, BorderLayout.CENTER);
		win_container.add(infoField, BorderLayout.NORTH);
		setSize(500,600);
		setVisible(true);
		setResizable(false);
		System.out.println("Button size: " + cells[0][0].getWidth() + "." + cells[0][0].getWidth());
	}

	public static void cell_depthsearch(int x, int y){

	if(x >= 0 && x <= 9 && y >= 0 && y <= 9 && cells[x][y].getToggle() == false){
		System.out.println("Current pos: " + x + "," + y);
		System.out.println("minecheck" + minefield[x][y]);
            if(minefield[x][y] == 0 ){
            	emptyCellsDecr();
                cells[x][y].setToggle(true);
                cells[x][y].setText(0 + "");
                cells[x][y].setBackground(Color.GRAY);
                cells[x][y].setrightClickLock(true);

            	cell_depthsearch(x-1, y);
            	cell_depthsearch(x+1, y);
            	cell_depthsearch(x, y-1);
            	cell_depthsearch(x, y+1);

            	cell_depthsearch(x+1, y+1);
            	cell_depthsearch(x-1, y-1);
            	cell_depthsearch(x+1, y-1);
            	cell_depthsearch(x-1, y+1);
            	return;
            }
            else{
            	for (int z = 1; z < 6; z++) {
	                if (minefield[x][y] == z) {
	                    if(z == 1)
							cells[x][y].setForeground(Color.BLUE);
						else if(z == 2)
							cells[x][y].setForeground(Color.GREEN);
						else if(z == 3)
							cells[x][y].setForeground(Color.YELLOW);
						else if(z == 4)
							cells[x][y].setForeground(Color.ORANGE);
						else if(z == 5)
							cells[x][y].setForeground(Color.RED);
						else if(z == 6)
							cells[x][y].setForeground(Color.CYAN);
						else if(z == 7)
							cells[x][y].setForeground(Color.PINK);
						else if(z == 8)
							cells[x][y].setForeground(Color.WHITE);
	                    
	                    emptyCellsDecr();
	                    cells[x][y].setToggle(true);
	                    cells[x][y].setText(z + "");
	                   	cells[x][y].setBackground(Color.GRAY);
	                   	cells[x][y].setrightClickLock(true);
	                   	return;
	                }
            	}
            		
            }
		}
		return;
	}
	public static void showBoard(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				if(minefield[i][j] >= 99){
					cells[i][j].setBackground(Color.GRAY);
					cells[i][j].setForeground(Color.RED);
					cells[i][j].setToggle(true);
					cells[i][j].setText("");
	                Icon mine = new ImageIcon("mine.png");
					//this.setText("B");
					cells[i][j].setIcon(mine);
				}
            }
		}
		
	}
	public void cellReset(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLM; j++){
				cells[i][j].setBackground(null);
				cells[i][j].setForeground(null);
				cells[i][j].setText("");
				cells[i][j].setToggle(false);
				cells[i][j].resetRightClick();
				cells[i][j].setrightClickLock(false);
				cells[i][j].setIcon(null);
			}
		}
		NUM_EMPTY_CELLS = 90;
	}
	
	public static void emptyCellsDecr(){
		NUM_EMPTY_CELLS--;
		System.out.println("Empty cells remaining: " + NUM_EMPTY_CELLS);
		if(NUM_EMPTY_CELLS == 0){
			timer.stop();
			score gameWon = new score(current_time);
		}

	}


	public static void updateMineCount(int x){
		NUM_OF_MINES = x;
		System.out.println("Number of bombs = " + NUM_OF_MINES);
		flagsOnBoard.setText("" + NUM_OF_MINES);
		return;
	}

	public static void resetClick(){
		resetButton.doClick();
	}

	//timer action
	public void actionPerformed(ActionEvent event){
		current_time++;
		System.out.println("This is the time: " + current_time);
		timeCounter.setText("" + current_time);
		timer.restart();
	}

	public static void main (String args[]){
		myBoard application = new myBoard();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

