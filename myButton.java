import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class myButton extends JButton implements ActionListener{
	private int x;
	private int y;
	private int BOMB_VALUE = 99;
	public static boolean bomb_trip;

	public myButton(int coordx, int coordy, String label){
		super(label);
		addActionListener(this);
		x = coordx;
		y = coordy;
	}

	public int getCoordx(){
		return x;
	}

	public int getCoordy(){
		return y;
	}

	public void actionPerformed(ActionEvent event){
		System.out.println(bomb_trip);

		if(bomb_trip == false){
			System.out.println("Coords: " + getCoordx() + ", " + getCoordy());
			System.out.println("Minecheck: " + boardBuild.minefield[getCoordx()][getCoordy()]);
			int field_value = boardBuild.minefield[getCoordx()][getCoordy()];
			
			if(field_value >= BOMB_VALUE){
				JOptionPane.showMessageDialog(this, "There was a bomb!");
				bomb_trip = true;
				boardBuild.showBoard();
			}
			else{
				JOptionPane.showMessageDialog(this, "Number of bombs around: " + field_value);
				setBackground(Color.GRAY);
				if(field_value == 1)
					setForeground(Color.BLUE);
				else if(field_value == 2)
					setForeground(Color.GREEN);
				else if(field_value == 3)
					setForeground(Color.YELLOW);
				else if(field_value == 4)
					setForeground(Color.ORANGE);
				else if(field_value == 5)
					setForeground(Color.RED);

				this.setText(field_value + "");
			}
		}
	}	
}