import java.util.*;
public class generateMines{
	
	private static final int ROWS = 10;
	private static final int COLM = 10;

	public generateMines(int[][] minefield){
		int BOMB_VALUE = 99;
		int NUM_OF_MINES = 10;
		myButton.bomb_trip = false;

		System.out.println("Cell has been reset");
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
	}


}