import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class myButton extends JButton implements ActionListener{
	private int x;
	private int y;
	private int BOMB_VALUE = 99;
	public static boolean bomb_trip;
	private boolean toggled = false;
	private boolean right_click_lock = false;
	private MouseClickHandler mouseHandler = new MouseClickHandler();

	private int rightClickCount = 0;
	public myButton(int coordx, int coordy, String label){
		super(label);
		setBounds(20,20,300,100);
		addActionListener(this);
		this.addMouseListener(mouseHandler);
		x = coordx;
		y = coordy;
	}

	public int getCoordx(){
		return x;
	}

	public int getCoordy(){
		return y;
	}

	public void setToggle(boolean select){
		toggled = select;
	}

	public boolean getToggle(){
		return toggled;
	}

	public void resetRightClick(){
		rightClickCount = 0;
	}

	public void setrightClickLock(boolean select){
		right_click_lock = select;
	}

	private class MouseClickHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event){
			if(myBoard.time_init == false){
				myBoard.timer.start();
				myBoard.time_init = true;
			}
			if(right_click_lock == false){
				if(event.getButton() == 3){
					if(rightClickCount == 0){
						setText("M");
						toggled = true;
						myBoard.updateMineCount(myBoard.NUM_OF_MINES - 1);
						rightClickCount++;
					}
					else if(rightClickCount == 1){
						setText("?");
						myBoard.updateMineCount(myBoard.NUM_OF_MINES + 1);
						rightClickCount++;
					}
					else if(rightClickCount == 2){
						right_click_lock = false;
						setText("");
						rightClickCount = 0;
						toggled = false;
					}
				}
			else
				System.out.println("Something else");
			}
		}
			
	}
	public void actionPerformed(ActionEvent event){
		System.out.println(bomb_trip);
		System.out.println("Toggle: " + toggled);
		if(myBoard.time_init == false){
			myBoard.timer.start();
			myBoard.time_init = true;
		}
		
		if(bomb_trip == false){
			if(toggled == false){

				System.out.println("Coords: " + getCoordx() + ", " + getCoordy());
				System.out.println("Minecheck: " + myBoard.minefield[getCoordx()][getCoordy()]);
				int field_value = myBoard.minefield[getCoordx()][getCoordy()];
				
				if(field_value >= BOMB_VALUE){
					bomb_trip = true;
					setBackground(Color.GRAY);
					this.setText("B");
					myBoard.timer.stop();
					JOptionPane.showMessageDialog(this, "There was a bomb!");
					myBoard.showBoard();
				}
				else{
					//JOptionPane.showMessageDialog(this, "Number of bombs around: " + field_value);
					setBackground(Color.GRAY);
					if(field_value == 0){
						myBoard.cell_depthsearch(getCoordx(), getCoordy());
						return;
					}
					else if(field_value == 1)
						setForeground(Color.BLUE);
					else if(field_value == 2)
						setForeground(Color.GREEN);
					else if(field_value == 3)
						setForeground(Color.YELLOW);
					else if(field_value == 4)
						setForeground(Color.ORANGE);
					else if(field_value == 5)
						setForeground(Color.RED);
					else if(field_value == 6)
						setForeground(Color.CYAN);
					else if(field_value == 7)
						setForeground(Color.PINK);
					else if(field_value == 8)
						setForeground(Color.WHITE);

					this.setText(field_value + "");
					toggled = true;
					myBoard.emptyCellsDecr();
					right_click_lock = true;
				}
				
			}
		}
	}

}
