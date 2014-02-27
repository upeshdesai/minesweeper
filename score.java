import java.util.*;
import java.util.Collections;
import java.util.List;
import java.io.*;
import javax.swing.*;

public class score{

	
	public score(int current_time){
		JOptionPane.showMessageDialog(null, 
					"You have won the game. Total time: " + current_time,
					"YOU WIN!", JOptionPane.PLAIN_MESSAGE);
		boolean highScore = false;

		List<Integer> list = new ArrayList<Integer>();
		List<String> names = new ArrayList<String>();

		File file = new File("topten.txt");
		BufferedReader reader = null;

		try{
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			int counter = 0;
			while((text = reader.readLine()) != null && counter < 10){
				list.add(Integer.parseInt(text));
				counter++;
			}

			names.add(text);
			while((text = reader.readLine()) != null){
				names.add(text);
			}
		} 
		catch (FileNotFoundException e){
		  	e.printStackTrace();
		}
		catch (IOException e){
		  	e.printStackTrace();
		} 
		finally{
		  	try{
		  		if(reader != null){
		  			reader.close();
		  		}
		  	} 
		  	catch(IOException e){

		  	}
		}

		int index = 0;
		for(int i = 0; i < list.size(); i++){
		  	if(list.get(i) > current_time){
		  		list.remove(i);
		  		list.add(i, current_time);
		  		highScore = true;
		  		index = i;
		  		break;
		  	}
		}

		/*for(int i = 0; i < list.size(); i++){
			System.out.println(names.get(i) + " " + list.get(i));
  		}*/
  		if(highScore){
  			String s = (String)JOptionPane.showInputDialog(null, "New High Score!\n"
  				+"Enter your name:\n", "High Score", JOptionPane.PLAIN_MESSAGE, null, null, "Bob");
  			if(s == null){
  				s = "null";
  			}

  			names.remove(index);
  			names.add(index, s);
  			try{
		  		PrintWriter writer = new PrintWriter(file);
		  		for(int i = 0; i < list.size(); i++){
		  			writer.println(list.get(i));
	 			}
	 			for(int i = 0; i < names.size(); i++){
	 				writer.println(names.get(i));
	 			}
		 		writer.close();
			}
			catch (FileNotFoundException e){
		  		e.printStackTrace();
			}
  		}
	}

	public static void showTopTen(){
		File file = new File("topten.txt");
		BufferedReader reader = null;
		List<String> nums = new ArrayList<String>();
		List<String> names = new ArrayList<String>();

		String temp = "";
		try{
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			int counter = 0;
			while((text = reader.readLine()) != null && counter < 10){
				nums.add(text);
				counter++;
			}
			names.add(text);

			while((text = reader.readLine()) != null ){
				names.add(text);
			}
		} 
		catch (FileNotFoundException e){
		  	e.printStackTrace();
		}
		catch (IOException e){
		  	e.printStackTrace();
		} 
		finally{
		  	try{
		  		if(reader != null){
		  			reader.close();
		  		}
		  	} 
		  	catch(IOException e){

		  	}
		}

		for(int i = 0; i < nums.size(); i++){
			temp = temp + names.get(i) + " " + nums.get(i) + "\n";
		}

		
		JOptionPane.showMessageDialog(null, 
					"High Scores:\n" + temp,
					"Top Ten", JOptionPane.PLAIN_MESSAGE);
	}

	public static void resetScores(){

		File file = new File("topten.txt");
		try{
			PrintWriter writer = new PrintWriter(file);
			for(int i = 0; i < 10; i++){
				writer.println("999");
			}
			for(int i = 0; i < 10; i++){
				writer.println("Bob");
			}

			writer.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
}

